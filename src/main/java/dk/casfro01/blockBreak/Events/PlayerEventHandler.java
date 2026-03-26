package dk.casfro01.blockBreak.Events;

import dk.casfro01.blockBreak.Service.Service;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEventHandler implements Listener {
    private final Service service;
    public PlayerEventHandler(Service service){
        this.service = service;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        service.addPlayer(player.getUniqueId().toString());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        service.savePlayerDataAndRemoveCache(player.getUniqueId().toString());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        service.incrementBlock(player.getUniqueId().toString());
    }
}
