package dk.casfro01.blockBreak.Commands.User;

import dk.casfro01.blockBreak.Commands.Abstractions.AbstractCommand;
import dk.casfro01.blockBreak.Commands.Abstractions.CommandWithService;
import dk.casfro01.blockBreak.Service.Service;
import org.bukkit.OfflinePlayer;

public class InfoBlocksCommand extends AbstractCommand implements CommandWithService<Service> {
    Service service;
    @Override
    public void execute() {
        //String[] args = getArgs();
        OfflinePlayer p = (OfflinePlayer) getSender();
        sendPlayerMessage(getStyle().getPrimaryColor() + "Blocks: " + getStyle().getSecondaryColor() + service.getData(p.getUniqueId().toString()).getBlocks());
    }


    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return getStyle().getPrimaryColor() + "/blocks info " + getStyle().getSecondaryColor() + " Henter blocks du har minet";
    }

    @Override
    public String getPermission() {
        return "block.blocks";
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
