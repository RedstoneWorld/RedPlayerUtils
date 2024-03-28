package de.redstoneworld.redplayerutils.commands;

import de.redstoneworld.redplayerutils.AbstractValueCommand;
import de.redstoneworld.redplayerutils.RedPlayerUtils;
import org.bukkit.entity.Player;

public class RedLevelCommand extends AbstractValueCommand {
    
    private final long MAX_LEVEL = Integer.MAX_VALUE;
    
    public RedLevelCommand(RedPlayerUtils plugin, String name) {
        super(plugin, name);
    }
    
    @Override
    protected String getValue(Player player) {
        return String.valueOf(player.getLevel());
    }
    
    @Override
    protected boolean applyValue(Player target, String input) {
        if (input.length() > String.valueOf(MAX_LEVEL).length()) {
            return false;
        }
        
        long level = Long.parseLong(input);
        if (level < 0 || level > MAX_LEVEL) {
            return false;
        }

        // Apply level
        target.setLevel((int) level);
        target.setExp(0);
        return true;
    }

}
