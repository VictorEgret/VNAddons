package fr.ft.vnaddons;

import fr.ft.vnaddons.commands.Commands;
import fr.ft.vnaddons.listeners.PlayerListener;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
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
        String[] commands = {"spectate", "ping", "sendmycoords"};

        Arrays.stream(commands).forEach(command -> Objects.requireNonNull(getCommand(command)).setExecutor(new Commands()));
    }

    private void registerListeners() {
        Listener[] listeners = {new PlayerListener()};

        Arrays.stream(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private static Object minecraftServer;
    private static Field recentTps;

    private static double[] getRecentTpsRefl() throws Throwable {
        if (minecraftServer == null) {
            Server server = Bukkit.getServer();
            Field consoleField = server.getClass().getDeclaredField("console");
            consoleField.setAccessible(true);
            minecraftServer = consoleField.get(server);
        }
        if (recentTps == null) {
            recentTps = minecraftServer.getClass().getSuperclass().getDeclaredField("recentTps");
            recentTps.setAccessible(true);
        }
        return (double[]) recentTps.get(minecraftServer);
    }

    public static double[] getRecentTps() {
        try {
            return getRecentTpsRefl();
        } catch (Throwable throwable) {
            return new double[]{20, 20, 20};
        }
    }
}
