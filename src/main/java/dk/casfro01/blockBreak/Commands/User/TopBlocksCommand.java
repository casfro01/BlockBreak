package dk.casfro01.blockBreak.Commands.User;

import dk.casfro01.blockBreak.BlockBreak;
import dk.casfro01.blockBreak.Commands.Abstractions.AbstractCommand;
import dk.casfro01.blockBreak.Commands.Abstractions.CommandWithService;
import dk.casfro01.blockBreak.Models.TopPlayerBlockData;
import dk.casfro01.blockBreak.Service.Service;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TopBlocksCommand extends AbstractCommand implements CommandWithService<Service> {
    Service service;
    @Override
    public void execute() {
        String[] args = getArgs();
        int page = 1;
        if (args.length != 0 && !args[0].isEmpty()) {
            try{
                page = Integer.parseInt(args[0]);
            }
            catch (Exception ignored){

            }
        }
        OfflinePlayer p = (OfflinePlayer) getSender();
        if (!service.canExecute(p.getUniqueId().toString())){
            sendPlayerMessage(getStyle().getErrorColor() + "Du kan ikke udføre denne kommando lige nu.");
            return;
        }
        service.addExecuting(p.getUniqueId().toString());
        int finalPage = page;
        BlockBreak bb = BlockBreak.getPlugin(BlockBreak.class);
        Bukkit.getScheduler().runTaskAsynchronously(bb, () -> {
            List<TopPlayerBlockData> lst = service.getTopBlocks(finalPage);
            Bukkit.getScheduler().runTask(bb, () ->{
                if (lst.isEmpty()){
                    sendPlayerMessage(getStyle().getPrimaryColor() + "Ingen data her.");
                }
                for (TopPlayerBlockData tp : lst) {
                    sendPlayerMessage(getStyle().getPrimaryColor() + "#" + tp.getPlace() + getStyle().getSecondaryColor() + " " + Bukkit.getOfflinePlayer(UUID.fromString(tp.getUuid())).getName() + " Blocks: " + tp.getBlocks());
                }
                service.removeExecuting(p.getUniqueId().toString());
            });

        });
    }


    @Override
    public String getName() {
        return "top";
    }

    @Override
    public String getDescription() {
        return getStyle().getPrimaryColor() + "/blocks top <side?> " + getStyle().getSecondaryColor() + " Henter blocks du har minet";
    }

    @Override
    public String getPermission() {
        return "block.top";
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
