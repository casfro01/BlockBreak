package dk.casfro01.blockBreak.Service;

import dk.casfro01.blockBreak.DataAccess.DataAccess;
import dk.casfro01.blockBreak.BlockBreak;
import dk.casfro01.blockBreak.DataAccess.IBlockAccess;
import dk.casfro01.blockBreak.DataAccess.ITop;
import dk.casfro01.blockBreak.Models.PlayerBlockData;
import dk.casfro01.blockBreak.Models.TopPlayerBlockData;
import dk.casfro01.blockBreak.Options.ConfigOptions;
import dk.casfro01.blockBreak.Util.Logger;
import dk.casfro01.blockBreak.Util.PlayerDataCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    private IBlockAccess<PlayerBlockData, String> dataAccess;
    private ConfigOptions config;
    private PlayerDataCache cache;
    private Logger logger;
    private final BlockBreak plugin;
    private Map<String, Boolean> commandCache = new HashMap<>();

    public Service(BlockBreak plugin, String dbpath, ConfigOptions config){
        this.cache = new PlayerDataCache();
        this.config = config;
        this.logger = new Logger(plugin);
        this.plugin = plugin;
        try {
            this.dataAccess = new DataAccess(plugin, dbpath, !new File(plugin.getDataFolder().getAbsolutePath() + dbpath).exists());

        } catch (Exception e) {
            logger.printToLog(e.getMessage() + " [DEBUG: service constructor]");
        }


        // start regelmæssige saves af spillerdata
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::saveAllCachedPlayerData, 20L * 300, 20L * 300);
    }

    public PlayerBlockData getData(String uuid){
        return cache.get(uuid);
    }

    public void setConfig(ConfigOptions configOptions) {
        config = configOptions;
    }

    public void addPlayer(String uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PlayerBlockData pData = dataAccess.get(uuid);
                Bukkit.getScheduler().runTask(plugin, () -> cache.addPlayer(uuid, pData));
            } catch (Exception e) {
                logger.printToLog(e.getMessage() + " UUID : " + uuid + " [DEBUG: addPlayer]");
            }
        });
    }

    public void savePlayerDataAndRemoveCache(String uuid){
        PlayerBlockData playerData = cache.get(uuid);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                boolean pData = dataAccess.save(playerData);
                if (pData) Bukkit.getScheduler().runTask(plugin, () -> cache.remove(uuid));
            } catch (Exception e) {
                logger.printToLog(e.getMessage() + " UUID : " + uuid + " [DEBUG: savePlayerDataAndRemoveCache]");
            }
        });
    }

    public void saveAllCachedPlayerData(){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                dataAccess.saveAll(cache.getList());
            } catch (Exception e) {
                logger.printToLog("FAILED TO SAVE ALLE CACHED DATA BECAUSE: " + e.getMessage() + " [DEBUG: saveAllCachedPlayerData]");
            }
        });
    }

    public void incrementBlock(String uuid){
        cache.get(uuid).incrementBlocks();
    }

    public void loadCurrentPlayers(List<Player> list){
        List<String> stringList = new ArrayList<>();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                List<PlayerBlockData> pData = dataAccess.getAll(stringList);
                Bukkit.getScheduler().runTask(plugin, () -> {for (PlayerBlockData d : pData) cache.addPlayer(d.getUuid(), d);});
            } catch (Exception e) {
                logger.printToLog("FAILED TO LOAD PLAYERS BECAUSE : " + e.getMessage() + " [DEBUG: loadCurrentPlayers]");
            }
        });
    }

    public List<TopPlayerBlockData> getTopBlocks(int page){
        try {
            if (dataAccess instanceof DataAccess d){
                return d.getTopTen(page);
            }
        } catch (Exception e) {
            logger.printToLog("FAILED TO FETCH TOP BECAUSE : " + e.getMessage() + " -> DEBUG: getTopBlocks::" + page);
        }
        return null;
    }

    public void addExecuting(String string) {
        commandCache.put(string, false);
    }
    public boolean canExecute(String string){
        return commandCache.getOrDefault(string, true);
    }
    public void removeExecuting(String string){
        commandCache.remove(string);
    }
}
