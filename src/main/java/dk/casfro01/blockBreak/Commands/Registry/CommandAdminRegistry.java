package dk.casfro01.blockBreak.Commands.Registry;


import dk.casfro01.blockBreak.Commands.Abstractions.ICommand;

import java.util.HashMap;
import java.util.Map;

public class CommandAdminRegistry implements IRegistry<ICommand> {
    private static CommandAdminRegistry instance;

    private CommandAdminRegistry(){}

    public static synchronized CommandAdminRegistry getInstance(){
        if (instance == null){
            instance = new CommandAdminRegistry();
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
