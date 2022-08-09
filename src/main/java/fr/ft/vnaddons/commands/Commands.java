package fr.ft.vnaddons.commands;

import fr.ft.vnaddons.VNAddons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                    p.sendMessage("Your ping: " + p.getPing() + "ms (TPS: " + VNAddons.getRecentTps() + ")");
                    return true;
                }
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null || !target.isOnline()) {
                        p.sendMessage(ChatColor.RED + args[0] + " is not online");
                        return true;
                    }
                    p.sendMessage(args[0] + "'s ping: " + target.getPing() + "ms (TPS: " + VNAddons.getRecentTps() + ")");
                    return true;
                }
                return false;
            }
            if (cmd.getName().equalsIgnoreCase("sendmycoords")) {
                if (args.length == 0) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(
                                p.getName() + "'s coords: " +
                                        p.getLocation().getBlockX() + " " +
                                        p.getLocation().getBlockY() + " " +
                                        p.getLocation().getBlockZ() + " " +
                                        "(" + p.getWorld().getEnvironment().name() + "§r)" +
                                        formatDistance(p, player)
                        );
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private String formatDistance(Player p, Player player) {
        StringBuilder result = new StringBuilder();
        if (p.getWorld().getEnvironment() == player.getWorld().getEnvironment() && !p.getUniqueId().equals(player.getUniqueId())) {
            result.append(" (");
            result.append(Math.round(p.getLocation().distance(player.getLocation())));
            result.append(" blocks away)");
        }
        return result.toString();
    }
}
