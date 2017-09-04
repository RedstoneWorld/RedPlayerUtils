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
            sender.sendMessage(plugin.getLang("usage"));
            return true;
        } else {
            target = (Player) sender;
        }

        // Get food level
        float saturationLevel = MAX_LEVEL;
        if (args.length > 0) {
            if (!sender.hasPermission("rwm.redsaturation.setlevel")) {
                sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.redsaturation.setlevel"));
                return true;
            }
            try {
                saturationLevel = Float.parseFloat(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(plugin.getLang("error.wrong-saturationlevel", "input", args[0]));
                return true;
            }
            if (saturationLevel > MAX_LEVEL) {
                sender.sendMessage(plugin.getLang("error.saturationlevel-above-max", "input", args[0]));
                return true;
            }
        }

        // Apply food level
        target.setSaturation(saturationLevel);
        sender.sendMessage(plugin.getLang("success-saturation." + (sender == target ? "own" : "other"),
                "name", target.getName(),
                "value", String.valueOf(saturationLevel)
        ));
        return true;
    }

}
