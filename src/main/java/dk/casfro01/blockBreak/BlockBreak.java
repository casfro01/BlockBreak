package dk.casfro01.blockBreak;

import dk.casfro01.blockBreak.Commands.Abstractions.ICommand;
import dk.casfro01.blockBreak.Commands.Admin.*;
import dk.casfro01.blockBreak.Commands.CommandFactory;
import dk.casfro01.blockBreak.Commands.Registry.CommandAdminRegistry;
import dk.casfro01.blockBreak.Commands.Registry.CommandRegistry;
import dk.casfro01.blockBreak.Commands.Registry.IRegistry;
import dk.casfro01.blockBreak.Commands.User.InfoBlocksCommand;
import dk.casfro01.blockBreak.Commands.User.TopBlocksCommand;
import dk.casfro01.blockBreak.Commands.User.UserCommandExecutor;
import dk.casfro01.blockBreak.Configs.StyleConfig;
import dk.casfro01.blockBreak.Configs.Config;
import dk.casfro01.blockBreak.Events.PlayerEventHandler;
import dk.casfro01.blockBreak.Options.ConfigOptions;
import dk.casfro01.blockBreak.Options.StyleOptions;
import dk.casfro01.blockBreak.Service.Service;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class BlockBreak extends JavaPlugin {
    private Service service;

    @Override
    public void onEnable() {

        // load configs
        saveResource("styleConfig.yml", false);
        saveResource("config.yml", false);

        StyleConfig style = new StyleConfig(this, "styleConfig.yml");
        Config conf = new Config(this, "config.yml");

        // options
        StyleOptions sOptions = new StyleOptions(style);
        ConfigOptions options = new ConfigOptions(conf);

        String dbpath = "/blocks.db";

        // services
        Service s = new Service(this, dbpath, options);;

        // setup
        CommandFactory.setStyleOptions(sOptions);
        CommandFactory.setService(s);
        CommandFactory.setConfigOptions(options);

        // register commands
        IRegistry<ICommand> Commands = CommandRegistry.getInstance();
        IRegistry<ICommand> adminCommands = CommandAdminRegistry.getInstance();

        Commands.registerCommand(new InfoBlocksCommand());
        Commands.registerCommand(new TopBlocksCommand());

        adminCommands.registerCommand(new AdminHelpCommand());

        // base commands
        getCommand("blocks").setExecutor(new UserCommandExecutor());
        getCommand("blockAdmin").setExecutor(new AdminCommandExecutor());

        // events
        getServer().getPluginManager().registerEvents(new PlayerEventHandler(s), this);

        // load online players into memory -> if server is reloaded
        s.loadCurrentPlayers(new ArrayList<>(getServer().getOnlinePlayers()));

        service = s;
    }

    @Override
    public void onDisable() {
        service.saveAllCachedPlayerData();
        // Plugin shutdown logic
        System.out.println("[BlockBreak] Shutting down, good bye.");
    }
}
