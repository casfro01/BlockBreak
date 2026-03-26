package dk.casfro01.blockBreak.Commands;

import dk.casfro01.blockBreak.Commands.Abstractions.AbstractCommand;
import org.bukkit.command.CommandSender;

public class CommandBuilder {
    private CommandSender sender;
    private AbstractCommand command;

    public CommandBuilder() {}

    public AbstractCommand getCommand(){
        command.setSender(sender);
        return command;
    }

    public CommandBuilder setCommand(String command, CommandFactory.CommandType type){
        this.command = CommandFactory.newCommand(command, type);
        return this;
    }

    /**
     * Sætter argumenterne til en kommando.
     */
    public CommandBuilder setArgs(String args){
        return setArgs(args.split(" "));
    }

    /**
     * Sætter argumenterne til den kommando.
     */
    public CommandBuilder setArgs(String[] args){
        command.setArgs(args);
        return this;
    }

    /**
     * Sætter den som udfører kommandoen. Hvis denne ikke bliver sat, så kan nogle kommandoer fejle, især hvis den skriver beskeder.
     */
    public CommandBuilder setPlayer(CommandSender sender){
        this.sender = sender;
        return this;
    }
}
