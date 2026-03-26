package dk.casfro01.blockBreak.DataAccess;

import dk.casfro01.blockBreak.BlockBreak;
import dk.casfro01.blockBreak.Models.PlayerBlockData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataAccess implements IBlockAccess<PlayerBlockData, String> {
    private DBConnector connector;

    // dbpath -> relative
    public DataAccess(BlockBreak plugin, String dbPath) throws Exception {
        this.connector = new DBConnector("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + dbPath);
        runDBScript();
    }

    public boolean saveAll(List<PlayerBlockData> data) throws Exception{
        String sql_update = "UPDATE Blocks SET Blocks = ? WHERE UUID = ?;";
        try(Connection conn = connector.getConnection();
            PreparedStatement stmt_update = conn.prepareStatement(sql_update)) {

            for (PlayerBlockData pData : data){
                if (!pData.isDirty()) continue;
                stmt_update.setInt(1, pData.getBlocks());
                stmt_update.setString(2, pData.getUuid());
                stmt_update.addBatch();
            }

            stmt_update.executeBatch();
        }
        return true;
    }

    public boolean save(PlayerBlockData data) throws Exception{
        if (!data.isDirty()) return true;
        String sql_playerCID = "SELECT ID FROM Blocks WHERE UUID = ?;";
        String sql_update = "UPDATE Blocks SET Blocks = ? WHERE ID = ?;";
        try(Connection conn = connector.getConnection();
        PreparedStatement stmt_CID = conn.prepareStatement(sql_playerCID);
        PreparedStatement stmt_update = conn.prepareStatement(sql_update)){

            stmt_CID.setString(1, data.getUuid());
            ResultSet rs = stmt_CID.executeQuery();
            int cid = 0;
            if (!rs.next()) cid = registerPlayer(data.getUuid(), conn);
            if (cid == -1) return false;
            cid = rs.getInt("ID");

            stmt_update.setInt(1, data.getBlocks());
            stmt_update.setInt(2, cid);

            stmt_update.executeUpdate();
        }
        return true;
    }

    private int registerPlayer(String uuid, Connection conn) throws Exception{
        String sql = "INSERT INTO Blocks (UUID) VALUES (?)";
        try(PreparedStatement addP = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            addP.setString(1, uuid);
            addP.executeUpdate();
            ResultSet rs = addP.getGeneratedKeys();
            if (!rs.next()) return -1;

            return rs.getInt(1);
        }
    }

    public PlayerBlockData get(String uuid) throws Exception{
        String sql_blocks = "SELECT Blocks FROM Blocks WHERE UUID = ?;";

        try(Connection conn = connector.getConnection();
        PreparedStatement stmt_blocks = conn.prepareStatement(sql_blocks)){

            stmt_blocks.setString(1, uuid);
            ResultSet rs = stmt_blocks.executeQuery();
            if (!rs.next()){
                registerPlayer(uuid, conn);
                return new PlayerBlockData(uuid, 0);
            }
            return new PlayerBlockData(uuid, rs.getInt(1));
        }
    }

    @Override
    public List<PlayerBlockData> getAll(List<String> param) throws Exception {
        List<PlayerBlockData> data = new ArrayList<>();

        String placeholders = String.join(",", Collections.nCopies(param.size(), "?"));
        String sql = "SELECT Blocks, UUID FROM Blocks WHERE UUID IN (" + placeholders + ")";

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int i = 1;
            for (String s : param) {
                stmt.setString(i++, s);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    data.add(new PlayerBlockData(rs.getString("UUID"), rs.getInt("Blocks")));
                }
            }
        }
        return data;
    }


    /**
     * Bygger databasen med den sql fil som man laver
     */
    public void runDBScript() throws Exception{
        executeSQL_File();
    }

    private void executeSQL_File() throws Exception {
        try (
                InputStream is = getClass().getClassLoader().getResourceAsStream("SQL.sql");
                Connection conn = connector.getConnection();
                Statement stmt = conn.createStatement()) {

            if (is == null) throw new NullPointerException("SQL File is not found!");
            String sql = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            // Split statements by semicolon, yk like a normal person
            String[] commands = sql.split("(?<=;)");
            for (String command : commands) {
                command = command.trim();
                if (!command.isEmpty()) {
                    stmt.execute(command);
                }
            }

        }
    }
}
