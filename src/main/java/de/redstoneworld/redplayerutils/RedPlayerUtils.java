package de.redstoneworld.redplayerutils;

import de.redstoneworld.redplayerutils.commands.RedHealthCommand;
import de.redstoneworld.redplayerutils.commands.RedHungerCommand;
import de.redstoneworld.redplayerutils.commands.RedLevelCommand;
import de.redstoneworld.redplayerutils.commands.RedSaturationCommand;
import de.redstoneworld.redplayerutils.commands.RedXpCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RedPlayerUtils extends JavaPlugin {

    public void onEnable() {
        saveDefaultConfig();
        registerCommand(new RedHungerCommand(this, "hunger"));
        registerCommand(new RedSaturationCommand(this, "saturation"));
        registerCommand(new RedHealthCommand(this, "health"));
        registerCommand(new RedXpCommand(this, "xp"));
        registerCommand(new RedLevelCommand(this, "level"));
    }
    
    private void registerCommand(AbstractValueCommand command) {
        getCommand(command.getName()).setExecutor(command);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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
    
    
    public static void setXp(Player target, int xp) {
        int level = getLevel(xp);
        target.setLevel(level);
        target.setExp((xp - getTotalXp(level)) / (float) getXpToNextLevelLevel(level));
    }
    
    public static int getLevel(int xp) {
        int level ;
        if (xp >= getTotalXp(32)) {
            level = ((int) Math.sqrt(72 * xp - 54215) + 325) / 18;
        } else if (xp >= getTotalXp(17)) {
            level = ((int) Math.sqrt(40 * xp - 7839) - 81) / 10;
        } else {
            level = (int) Math.sqrt(xp + 9) - 3;
        }
        return level > 0 ? level : 0;
    }
    
    public static int getXpToNextLevelLevel(Player player) {
        return getXpToNextLevelLevel(player.getLevel());
    }
    
    public static int getXpToNextLevelLevel(int level) {
        if (level > 30) {
            return (9 * level) - 158;
        } else if (level > 15) {
            return (5 * level) - 38;
        } else {
            return (2 * level) + 7;
        }
    }
    
    public static int getTotalXp(Player player) {
        return Math.round(getXpToNextLevelLevel(player) * player.getExp()) + getTotalXp(player.getLevel());
    }
    
    public static int getTotalXp(int level) {
        if (level > 31) {
            return (int) (4.5 * level * level - 162.5 * level + 2220);
        } else if (level > 16) {
            return (int) (2.5 * level * level - 40.5 * level + 360);
        } else {
            return level * level + 6 * level;
        }
    }
}
