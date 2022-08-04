package fr.ft.vnaddons.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
}
