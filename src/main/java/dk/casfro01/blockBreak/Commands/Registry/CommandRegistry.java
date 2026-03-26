package dk.casfro01.blockBreak.Commands.Registry;


import dk.casfro01.blockBreak.Commands.Abstractions.ICommand;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry implements IRegistry<ICommand> {
    private static CommandRegistry instance;

    private CommandRegistry(){}

    public static synchronized CommandRegistry getInstance(){
        if (instance == null){
            instance = new CommandRegistry();
        }
        return instance;
    }


    private final Map<String, Class<? extends ICommand>> commands = new HashMap<>();
    @Override
    public void registerCommand(ICommand command) {
        commands.put(command.getName(), command.getClass());
    }

    @Override
    public Map<String, Class<? extends ICommand>> getCommands() {
        return commands;
    }
}
