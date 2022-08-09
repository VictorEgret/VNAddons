package fr.ft.vnaddons.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

public class ServerListener implements Listener {

    private final ArrayList<CachedServerIcon> icons = new ArrayList<>();
    private int icon = 0;

    public ServerListener() {
        loadServerIcons();
    }

    private void loadServerIcons() {
        File iconsFolder = new File(Bukkit.getWorldContainer().getAbsolutePath() + "/icons");

        iconsFolder.mkdirs();

        if (iconsFolder.isDirectory()) {
            for (File icon : iconsFolder.listFiles()) {
                try {
                    icons.add(Bukkit.loadServerIcon(icon));
                } catch (Exception e) {
                    Bukkit.getLogger().log(Level.SEVERE, "§cFailed to cache " + icon.getName() + " in server icons");
                }
            }
        }
    }

    private CachedServerIcon getServerIcon() {
        CachedServerIcon currentIcon = icons.get(icon);
        icon++;
        if (icon == icons.size())
            icon = 0;
        return currentIcon;
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        if (!icons.isEmpty())
            event.setServerIcon(getServerIcon());
    }
}