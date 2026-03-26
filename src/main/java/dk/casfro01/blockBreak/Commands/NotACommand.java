package dk.casfro01.blockBreak.Commands;

import dk.casfro01.blockBreak.Commands.Abstractions.AbstractCommand;

public class NotACommand extends AbstractCommand {

    @Override
    public void execute() {
        sendPlayerMessage(getStyle().getErrorColor() + "Ikke en valid kommando, brug" + getStyle().getPrimaryColor() + " /template help" + getStyle().getErrorColor() + " for mere hjælp.");

    }

    @Override
    public String getName() {
        return "notacommand";
    }

    @Override
    public String getDescription() {
        return getStyle().getPrimaryColor() + "This is a description of a command.";
    }

    @Override
    public String getPermission() {
        return "permission";
    }

}
