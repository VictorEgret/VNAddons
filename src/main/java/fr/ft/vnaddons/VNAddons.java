package fr.ft.vnaddons;

import fr.ft.vnaddons.commands.Commands;
import fr.ft.vnaddons.listeners.PlayerListener;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class VNAddons extends JavaPlugin {

    public static final HashMap<UUID, Location> spectators = new HashMap<>();

    public static void removeSpectator(Player player) {
        player.teleport(VNAddons.spectators.get(player.getUniqueId()));
        VNAddons.spectators.remove(player.getUniqueId());
        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(ChatColor.GREEN + "Spectate mode disabled");
    }

    @Override
    public void onEnable() {
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands() {
        String[] commands = {"spectate"};

        Arrays.stream(commands).forEach(command -> Objects.requireNonNull(getCommand(command)).setExecutor(new Commands()));
    }

    private void registerListeners() {
        Listener[] listeners = {new PlayerListener()};

        Arrays.stream(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }
}
