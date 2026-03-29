package dk.casfro01.blockBreak.Commands.Admin;

import dk.casfro01.blockBreak.Commands.Abstractions.AbstractCommand;
import dk.casfro01.blockBreak.Options.StyleOptions;

public class AdminHelpCommand extends AbstractCommand {
    @Override
    public void execute() {

        // send dem her - dette er hardcoded for at man selv kan styre hvordan de vises
        StyleOptions options = getStyle();
        sendAdminPlayerMessage(options.getPrimaryColor() + "Admin Kommandoer:");
        sendPlayerMessage(getDescription(), false);
        sendAdminPlayerMessage("Denne kommand er ikke lavet endnu");
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return getStyle().getPrimaryColor() + "/blocksAdmin help " + getStyle().getSecondaryColor() + "Viser dig en liste over tilgængelige kommandoer.";
    }

    @Override
    public String getPermission() {
        return "";
    }
}
