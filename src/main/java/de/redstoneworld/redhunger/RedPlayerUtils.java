package de.redstoneworld.redhunger;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class RedPlayerUtils extends JavaPlugin {

    public void onEnable() {
        saveDefaultConfig();
        getCommand("hunger").setExecutor(new RedHungerCommand(this));
        getCommand("saturation").setExecutor(new RedHungerCommand(this));
        getCommand("health").setExecutor(new RedHungerCommand(this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Reload comand /saturation --reload|-r
        if (args.length > 0 && ("reload".equalsIgnoreCase(args[0]))) {
            if (!sender.hasPermission("rwm.redplayerutils.reload")) {
                sender.sendMessage(getLang("error.no-permission", "perm", "rwm.redplayerutils.reload"));
                return true;
            }
            reloadConfig();
            sender.sendMessage(org.bukkit.ChatColor.RED + "[RedPlayerUtils]" + org.bukkit.ChatColor.YELLOW + " Config reloaded!");
            return true;
        }
        return false;
    }

    String getLang(String key, String... args) {
        String lang = getConfig().getString("lang." + key, "&cUnknown language key &6" + key);
        for (int i = 0; i + 1 < args.length; i+=2) {
            lang = lang.replace("%" + args[i] + "%", args[i + 1]);
        }
        return ChatColor.translateAlternateColorCodes('&', lang);
    }
}
