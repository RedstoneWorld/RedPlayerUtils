package de.redstoneworld.redhunger;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RedHunger extends JavaPlugin {

    public void onEnable() {
        saveDefaultConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rwm.redhunger.use")) {
            sender.sendMessage(getLang("no-permission", "perm", "rwm.redhunger.use"));
            return true;
        }

        // Reload comand /hunger --reload|-r
        if (args.length > 0 && ("-r".equals(args[0]) || "--reload".equalsIgnoreCase(args[0]))) {
            if (!sender.hasPermission("rwm.redhunger.reload")) {
                sender.sendMessage(getLang("error.no-permission", "perm", "rwm.redhunger.reload"));
                return true;
            }
            reloadConfig();
            sender.sendMessage(ChatColor.RED + "[RedHunger]" + ChatColor.YELLOW + " Config reloaded!");
            return true;
        }

        // Get target player and food level
        int foodLevel = 0;
        Player target = null;
        if (args.length > 0) {
            if (!sender.getName().equalsIgnoreCase(args[0]) && !sender.hasPermission("rwm.redhunger.use.others")) {
                sender.sendMessage(getLang("error.no-permission", "perm", "rwm.redhunger.use.others"));
                return true;
            }
            target = getServer().getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(getLang("error.player-not-found", "name", args[0]));
                return true;
            }
            if (args.length > 1) {
                if (!sender.hasPermission("rwm.redhunger.setlevel")) {
                    sender.sendMessage(getLang("error.no-permission", "perm", "rwm.redhunger.setlevel"));
                    return true;
                }
                try {
                    foodLevel = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(getLang("error.wrong-foodlevel", "input", args[1]));
                    return true;
                }
            }
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(getLang("usage"));
            return true;
        } else {
            target = (Player) sender;
        }

        // Apply food level
        target.setFoodLevel(foodLevel);
        if (sender == target) {
            sender.sendMessage(getLang("success.own", "name", target.getName()));
        } else {
            sender.sendMessage(getLang("success.other", "name", target.getName()));
        }
        return true;
    }

    private String getLang(String key, String... args) {
        String lang = getConfig().getString("lang." + key, "&cUnknown language key &6" + key);
        for (int i = 0; i + 1 < args.length; i+=2) {
            lang = lang.replace("%" + args[i] + "%", args[i + 1]);
        }
        return ChatColor.translateAlternateColorCodes('&', lang);
    }


}
