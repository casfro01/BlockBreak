package dk.casfro01.blockBreak.Commands.Admin;

import dk.casfro01.blockBreak.Commands.Abstractions.AbstractCommand;
import dk.casfro01.blockBreak.Commands.Abstractions.CommandWithService;
import dk.casfro01.blockBreak.Options.StyleOptions;
import dk.casfro01.blockBreak.Service.Service;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class AdminAddBlocksCommand extends AbstractCommand implements CommandWithService<Service> {
    private Service service;
    @Override
    public void execute() {
        if (!checkPermission(true)) return;
        String[] args = getArgs();

        if(args.length < 2 || args[0].isEmpty() || args[1].isEmpty()){
            sendAdminPlayerMessage(getDescription());
            return;
        }

        OfflinePlayer off = Bukkit.getOfflinePlayer(args[0]);
        if (off == null) {
            sendAdminPlayerMessage(getStyle().getErrorColor() + "Kunne ikke finde spilleren");
            return;
        }
        int amount = 0;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException ignore) {
        }
        service.addBlocks(off.getUniqueId().toString(), amount);
        sendAdminPlayerMessage(getStyle().getPrimaryColor() + "Tilføjet " + getStyle().getSecondaryColor() + amount + getStyle().getPrimaryColor() + " til " + getStyle().getSecondaryColor() + off.getName());

    }

    @Override
    public String getName() {
        return "addblocks";
    }

    @Override
    public String getDescription() {
        return getStyle().getPrimaryColor() + "/blocksAdmin addBlocks <player> <amount> " + getStyle().getSecondaryColor() + "Tilføjer blocks til kontoen.";
    }

    @Override
    public String getPermission() {
        return "blocks.admin.add";
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public Class<Service> getServiceType() {
        return Service.class;
    }
}
