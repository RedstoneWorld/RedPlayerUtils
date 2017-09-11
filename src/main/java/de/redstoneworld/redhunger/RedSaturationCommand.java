package de.redstoneworld.redhunger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RedSaturationCommand implements CommandExecutor {

    private final RedPlayerUtils plugin;
    private final int MAX_LEVEL = 20;

    public RedSaturationCommand(RedPlayerUtils plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rwm.redsaturation.use")) {
            sender.sendMessage(plugin.getLang("no-permission", "perm", "rwm.redsaturation.use"));
            return true;
        }

        if (args.length == 0 && sender instanceof Player) {
            sender.sendMessage(plugin.getLang("current-level.saturation", "value", String.valueOf(((Player) sender).getSaturation())));
            return true;
        } else if (args.length == 1) {
            Player player = plugin.getServer().getPlayer(args[0]);
            if (player != null) {
                if (sender != player && !sender.hasPermission("rwm.redsaturation.use.others")) {
                    sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.redsaturation.use.others"));
                    return true;
                }
                sender.sendMessage(plugin.getLang("current-level-other.saturation",
                        "player", player.getName(),
                        "value", String.valueOf(player.getSaturation())));
                return true;
            }
        }

        // Get target player
        Player target;
        if (args.length > 1) {
            if (!sender.getName().equalsIgnoreCase(args[1]) && !sender.hasPermission("rwm.redsaturation.use.others")) {
                sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.redsaturation.use.others"));
                return true;
            }
            target = plugin.getServer().getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(plugin.getLang("error.player-not-found", "name", args[1]));
                return true;
            }
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getLang("usage-saturation"));
            return true;
        } else {
            target = (Player) sender;
        }

        if (!sender.hasPermission("rwm.redsaturation.setlevel")) {
            sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.redsaturation.setlevel"));
            return true;
        }

        try {
            float saturationLevel = Float.parseFloat(args[0]);

            if (saturationLevel > MAX_LEVEL) {
                sender.sendMessage(plugin.getLang("error.saturation-above-max", "input", args[0]));
                return true;
            }

            // Apply saturation level
            target.setSaturation(saturationLevel);
            sender.sendMessage(plugin.getLang("success-saturation." + (sender == target ? "own" : "other"),
                    "name", target.getName(),
                    "value", String.valueOf(saturationLevel)
            ));
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getLang("error.wrong-saturation", "input", args[0]));
            return true;
        }

        return true;
    }

}
