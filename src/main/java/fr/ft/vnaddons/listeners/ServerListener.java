package fr.ft.vnaddons.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

public class ServerListener implements Listener {

    private final ArrayList<CachedServerIcon> icons = new ArrayList<>();
    private final ArrayList<String> iconsNames = new ArrayList<>();
    private final Random rand = new Random();

    public ServerListener() {
        loadServerIcons();
    }

    private void loadServerIcons() {
        File iconsFolder = new File(Bukkit.getWorldContainer().getAbsolutePath() + "/icons");

        iconsFolder.mkdirs();
        icons.clear();
        if (iconsFolder.isDirectory()) {
            for (File icon : iconsFolder.listFiles()) {
                try {
                    icons.add(Bukkit.loadServerIcon(icon));
                    iconsNames.add(icon.getName());
                } catch (Exception e) {
                    Bukkit.getLogger().log(Level.SEVERE, "Failed to cache " + icon.getName() + " in server icons");
                }
            }
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        loadServerIcons();
        if (!icons.isEmpty()) {
            try {
                int current = rand.nextInt(icons.size());
                event.setServerIcon(icons.get(current));
                event.setMotd(event.getMotd() + " (" + iconsNames.get(current) + ")");
            } catch (Exception exception) {

            }
        }
    }
}
