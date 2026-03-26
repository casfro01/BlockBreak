package dk.casfro01.blockBreak.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector{
    private String prop_path;
    public DBConnector(String prop_path) throws SQLException {
        this.prop_path = prop_path;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(prop_path);
    }
}
