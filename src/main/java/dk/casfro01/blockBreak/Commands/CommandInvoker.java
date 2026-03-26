package dk.casfro01.blockBreak.Commands;

import dk.casfro01.blockBreak.Commands.Abstractions.ICommand;

public class CommandInvoker {

    /**
     * Eksekvér en kommando.
     * @param command Den {@link ICommand} som skal eksekveres
     */
    public void executeCommand(ICommand command){
        // execute
        command.execute();
    }
}
