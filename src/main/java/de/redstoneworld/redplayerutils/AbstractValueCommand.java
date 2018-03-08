package de.redstoneworld.redplayerutils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractValueCommand implements CommandExecutor {
    protected final RedPlayerUtils plugin;
    private final String name;
    
    public AbstractValueCommand(RedPlayerUtils plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rwm.red" + name + ".use")) {
            sender.sendMessage(plugin.getLang("no-permission", "perm", "rwm.red" + name + ".use"));
            return true;
        }
        
        if (args.length == 0 && sender instanceof Player) {
            sender.sendMessage(plugin.getLang("current-level." + name + "", "value", getValue((Player) sender)));
            return true;
        } else if (args.length == 1) {
            Player player = plugin.getServer().getPlayer(args[0]);
            if (player != null) {
                if (sender != player && !sender.hasPermission("rwm.red" + name + ".use.others")) {
                    sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.red" + name + ".use.others"));
                    return true;
                }
                sender.sendMessage(plugin.getLang("current-level-other." + name + "",
                        "player", player.getName(),
                        "value", getValue(player)));
                return true;
            }
        }
        
        // Get target player
        Player target;
        if (args.length > 1) {
            if (!sender.getName().equalsIgnoreCase(args[1]) && !sender.hasPermission("rwm.red" + name + ".use.others")) {
                sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.red" + name + ".use.others"));
                return true;
            }
            target = plugin.getServer().getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(plugin.getLang("error.player-not-found", "name", args[1]));
                return true;
            }
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getLang("usage-" + name + ""));
            return true;
        } else {
            target = (Player) sender;
        }
        
        if (!sender.hasPermission("rwm.red" + name + ".setlevel")) {
            sender.sendMessage(plugin.getLang("error.no-permission", "perm", "rwm.red" + name + ".setlevel"));
            return true;
        }
        
        try {
            if (applyValue(target, args[0])) {
                sender.sendMessage(plugin.getLang("success-" + name + "." + (sender == target ? "own" : "other"),
                        "name", target.getName(),
                        "value", args[0]
                ));
            } else {
                sender.sendMessage(plugin.getLang("error." + name + "-above-max", "input", args[0]));
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getLang("error.wrong-" + name + "", "input", args[0]));
        }
        
        return true;
    }
    
    protected abstract String getValue(Player player);
    
    protected abstract boolean applyValue(Player target, String input) throws NumberFormatException;
    
}
