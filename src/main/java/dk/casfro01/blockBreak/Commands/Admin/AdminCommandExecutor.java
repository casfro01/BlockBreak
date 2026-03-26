package dk.casfro01.blockBreak.Commands.Admin;

import dk.casfro01.blockBreak.Commands.CommandBuilder;
import dk.casfro01.blockBreak.Commands.CommandFactory;
import dk.casfro01.blockBreak.Commands.CommandInvoker;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class AdminCommandExecutor implements org.bukkit.command.CommandExecutor{
    private final CommandInvoker invoker = new CommandInvoker();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("template.admin")){
            commandSender.sendMessage(ChatColor.RED + "Du har ikke adgang til admin systemet.");
            return false;
        }

        if (strings.length < 1){invoker.executeCommand(new CommandBuilder().setCommand("help", CommandFactory.CommandType.ADMIN).setPlayer(commandSender).getCommand()); return true;}
        // Find argumenterne, hvis der er nogen. Hvis der ikke er, så er den tom... og kommandoerne kan derfra håndtere resten.
        String[] args = (strings.length > 1) ? Arrays.copyOfRange(strings, 1, strings.length) : new String[0];
        String baseCommand = strings[0].toLowerCase();


        // byg kommando
        CommandBuilder com = new CommandBuilder()
                .setCommand(baseCommand, CommandFactory.CommandType.ADMIN)
                .setPlayer(commandSender)
                .setArgs(args);
        // udfør kommando
        invoker.executeCommand(com.getCommand());
        return true;
    }
}
