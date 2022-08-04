package fr.ft.vnaddons.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp())
            event.setJoinMessage(event.getJoinMessage().replace(
                    player.getName(),
                    ChatColor.DARK_RED + player.getName() + ChatColor.WHITE
            ));
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.isOp())
            event.setQuitMessage(event.getQuitMessage().replace(
                    player.getName(),
                    ChatColor.DARK_RED + player.getName() + ChatColor.WHITE
            ));
    }
}
