package de.redstoneworld.redhunger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RedHungerCommand implements CommandExecutor {

    private final RedPlayerUtils plugin;
    private final int MAX_LEVEL = 20;

    public RedHungerCommand(RedPlayerUtils plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rwm.redhunger.use")) {
            sender.sendMessage(plugin.getLang("no-permission", "perm", "rwm.redhunger.use"));
            return true;
        }

        // Get target player
        Player target;
        if (args.length > 1) {
            if (!sender.getName().equalsIgnoreCase(args[1]) && !sender.hasPermission("rwm.redhunger.use.others")) {
                sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.redhunger.use.others"));
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
        int foodLevel = MAX_LEVEL;
        if (args.length > 0) {
            if (!sender.hasPermission("rwm.redhunger.setlevel")) {
                sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.redhunger.setlevel"));
                return true;
            }
            try {
                foodLevel = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(plugin.getLang("error.wrong-foodlevel", "input", args[0]));
                return true;
            }
            if (foodLevel > MAX_LEVEL) {
                sender.sendMessage(plugin.getLang("error.foodlevel-above-max", "input", args[0]));
                return true;
            }
        }

        // Apply food level
        target.setFoodLevel(foodLevel);
        sender.sendMessage(plugin.getLang("success." + (sender == target ? "own" : "other"),
                "name", target.getName(),
                "value", String.valueOf(foodLevel)
        ));
        return true;
    }
}
