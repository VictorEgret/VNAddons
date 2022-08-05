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
                        sender.sendMessage(ChatColor.RED + args[0] + " is not online");
                    } else {
                        if (VNAddons.spectators.containsKey(p.getUniqueId())) {
                            VNAddons.removeSpectator(p);
                        } else {
                            VNAddons.spectators.put(p.getUniqueId(), p.getLocation());
                            p.setGameMode(GameMode.SPECTATOR);
                            p.setSpectatorTarget(target);
                            p.sendMessage(ChatColor.GREEN + "Spectate mode enabled");
                        }
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}