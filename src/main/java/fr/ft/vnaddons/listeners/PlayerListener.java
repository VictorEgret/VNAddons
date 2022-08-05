package fr.ft.vnaddons.listeners;

import fr.ft.vnaddons.VNAddons;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.isOp())
            event.setQuitMessage(event.getQuitMessage().replace(
                    player.getName(),
                    ChatColor.DARK_RED + player.getName() + ChatColor.WHITE
            ));
        if (VNAddons.spectators.containsKey(player.getUniqueId())) {
            VNAddons.removeSpectator(player);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        event.setFormat("%s§r: %s");
    }
}
