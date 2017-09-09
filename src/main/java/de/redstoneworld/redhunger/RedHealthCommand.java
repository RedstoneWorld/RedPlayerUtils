package de.redstoneworld.redhunger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RedHealthCommand implements CommandExecutor {

    private final RedPlayerUtils plugin;

    public RedHealthCommand(RedPlayerUtils plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rwm.redhealth.use")) {
            sender.sendMessage(plugin.getLang("no-permission", "perm", "rwm.redhealth.use"));
            return true;
        }

        if (args.length == 0 && sender instanceof Player) {
            sender.sendMessage(plugin.getLang("current-level.health", "value", String.valueOf(((Player) sender).getHealth())));
            return true;
        } else if (args.length == 1) {
            Player player = plugin.getServer().getPlayer(args[0]);
            if (player != null) {
                if (!sender.getName().equalsIgnoreCase(args[1]) && !sender.hasPermission("rwm.redhealth.use.others")) {
                    sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.redhealth.use.others"));
                    return true;
                }
                sender.sendMessage(plugin.getLang("current-level-other.health",
                        "player", player.getName(),
                        "value", String.valueOf(player.getHealth())));
                return true;
            }
        }

        // Get target player
        Player target;
        if (args.length > 1) {
            if (!sender.getName().equalsIgnoreCase(args[1]) && !sender.hasPermission("rwm.redhealth.use.others")) {
                sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.redhealth.use.others"));
                return true;
            }
            target = plugin.getServer().getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(plugin.getLang("error.player-not-found", "name", args[1]));
                return true;
            }
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getLang("usage"));
            return true;
        } else {
            target = (Player) sender;
        }

        if (!sender.hasPermission("rwm.redhealth.setlevel")) {
            sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.redhealth.setlevel"));
            return true;
        }

        try {
            double healthLevel = Double.parseDouble(args[0]);
            if (healthLevel > 20) {
                sender.sendMessage(plugin.getLang("error.healthlevel-above-max", "input", args[0]));
                return true;
            }

            // Apply health level
            target.setHealth(healthLevel);
            sender.sendMessage(plugin.getLang("success-health." + (sender == target ? "own" : "other"),
                    "name", target.getName(),
                    "value", String.valueOf(healthLevel)
            ));
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getLang("error.wrong-healthlevel", "input", args[0]));
            return true;
        }
        return true;
    }

}
