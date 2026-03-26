package dk.casfro01.blockBreak.Commands.Abstractions;

import dk.casfro01.blockBreak.Options.StyleOptions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements ICommand {
    // constructor perhaps
    private CommandSender sender;
    private String[] args = new String[0];
    private StyleOptions style = null;

    /**
     * Sætter kommandosenderen i kommandoen. Dette kan være konsollen eller en spiller.
     * @param sender af typen {@link CommandSender}
     */
    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    /**
     * Sætter De argumenter som blev brugt under kommandoudførslen
     * @param args Argumenterne i en tekst streng
     */
    public void setArgs(String[] args) {
        this.args = args;
    }

    protected CommandSender getSender(){
        return sender;
    }

    protected String[] getArgs(){
        return args;
    }

    /**
     * Tjekker om spilleren der sender kommandoen har adgang til den.
     * @param allowConsole Må konsollen sende denne kommando?
     * @return Hvis spilleren har adgang til kommandoen så returneres der true.
     * - Hvis de ikke har adgang så returneres der false og en besked sendes til spilleren.
     */
    protected boolean checkPermission(boolean allowConsole){
        if (sender.hasPermission(getPermission()) || (allowConsole && !(sender instanceof Player))) return true;
        else{
            sendAdminPlayerMessage(style.getPermissionMessage());
            return false;
        }
    }

    public void setStyle(StyleOptions options){
        style = options;
    }

    /**
     * Sender en besked til playeren med Adminprefix og med farve kode &.
     */
    protected void sendAdminPlayerMessage(String message){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', style.getAdminPrefix() + message));
    }

    /**
     * Sender en besked til playeren med Adminprefix og med farve kode &.
     */
    protected void sendPlayerMessage(String message){
        sendPlayerMessage(message, true);
    }

    /**
     * Sender en besked til playeren med prefix og med farve kode &.
     * Hvis man ikke ønsker prefix, så sæt prefix til false.
     */
    protected void sendPlayerMessage(String message, boolean prefix){
        if (!prefix)
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        else
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', style.getPrefix() + message));
    }

    protected StyleOptions getStyle(){
        return style;
    }
}
