package dk.casfro01.blockBreak.Util;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final File logFile;
    private final DateTimeFormatter formatter;

    public Logger(JavaPlugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        this.logFile = new File(plugin.getDataFolder(), "log.txt");

        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public void printToLog(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String line = "[" + timestamp + "] " + message + "\n";

        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(line);
        }
        // imagine, hvis man havde en logger til sin logger :skull:
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
