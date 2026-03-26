package dk.casfro01.blockBreak.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class StyleConfig implements IConfig {
    private File file;
    private FileConfiguration config;



    public StyleConfig(Plugin plugin, String path) {
        setup(plugin, path);
    }

    private void setup(Plugin plugin, String path) {
        file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        init();
    }

    public StyleConfig(String path){

        this.file = new File(path);
        boolean doesExist = file.exists();
        this.config = YamlConfiguration.loadConfiguration(this.file);

        // Kun lave den indbyggede kasse, hvis der ikke er nogen fil, ellers bliver vi nødt til at lave den.
        // Desuden gider man ikke at have, at den komme tilbage hver gang man sletter den
        if (!doesExist) {
            init();
        }
    }

    public boolean save(){
        try{
            this.config.save(this.file);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean init(){
        return this.save();
    }

    public File getFile(){
        return this.file;
    }

    public FileConfiguration getConfig(){
        return this.config;
    }

}
