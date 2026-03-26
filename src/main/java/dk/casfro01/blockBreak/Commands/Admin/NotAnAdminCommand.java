package dk.casfro01.blockBreak.Commands.Admin;

import dk.casfro01.blockBreak.Commands.Abstractions.AbstractCommand;

public class NotAnAdminCommand extends AbstractCommand {

    @Override
    public void execute() {
        sendPlayerMessage(getStyle().getErrorColor() + "Ikke en valid kommando, brug" + getStyle().getPrimaryColor() + " /command2 help" + getStyle().getErrorColor() + " for mere hjælp.");

    }

    @Override
    public String getName() {
        return "notanadmincommand";
    }

    @Override
    public String getDescription() {
        return getStyle().getPrimaryColor() + "This is a description of a command.";
    }

    @Override
    public String getPermission() {
        return "permission.admin";
    }

}
