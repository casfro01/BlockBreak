package dk.casfro01.blockBreak.Configs;

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfig {
    boolean save();
    FileConfiguration getConfig();
}
