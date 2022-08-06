package fr.ft.vnaddons.commands;

import fr.ft.vnaddons.VNAddons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("spectate")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null || !target.isOnline()) {
                        p.sendMessage(ChatColor.RED + args[0] + " is not online");
                        return true;
                    }
                    if (target.getUniqueId() == p.getUniqueId()) {
                        p.sendMessage(ChatColor.RED + "You can't spectate yourself");
                        return true;
                    }
                    if (target.getGameMode() == GameMode.SURVIVAL) {
                        if (VNAddons.spectators.containsKey(p.getUniqueId())) {
                            VNAddons.removeSpectator(p);
                        } else {
                            VNAddons.spectators.put(p.getUniqueId(), p.getLocation());
                            p.setGameMode(GameMode.SPECTATOR);
                            p.setSpectatorTarget(target);
                            p.sendMessage(ChatColor.GREEN + "Spectate mode enabled");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + args[0] + " is not in survival");
                    }
                    return true;
                }
                return false;
            }
            if (cmd.getName().equalsIgnoreCase("ping")) {
                if (args.length == 0) {
                    p.sendMessage("Your ping: " + p.getPing() + "ms (TPS: " + Arrays.toString(VNAddons.getRecentTps()) + ")");
                    return true;
                }
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null || !target.isOnline()) {
                        p.sendMessage(ChatColor.RED + args[0] + " is not online");
                        return true;
                    }
                    p.sendMessage(args[0] + "'s ping: " + target.getPing() + "ms (TPS: " + Arrays.toString(VNAddons.getRecentTps()) + ")");
                    return true;
                }
                return false;
            }
            if (cmd.getName().equalsIgnoreCase("sendmycoords")) {
                if (args.length == 0) {
                    Bukkit.broadcastMessage(
                            p.getName() + "'s coords: " +
                                    p.getLocation().getBlockX() + " " +
                                    p.getLocation().getBlockY() + " " +
                                    p.getLocation().getBlockZ() + " " +
                                    " (" + p.getWorld().getEnvironment().name() + "§r)"
                    );
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
