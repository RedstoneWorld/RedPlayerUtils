package de.redstoneworld.redplayerutils.commands;

import de.redstoneworld.redplayerutils.AbstractValueCommand;
import de.redstoneworld.redplayerutils.RedPlayerUtils;
import org.bukkit.entity.Player;

public class RedXpCommand extends AbstractValueCommand {
    
    private final long MAX_XP = Integer.MAX_VALUE;
    
    public RedXpCommand(RedPlayerUtils plugin, String name) {
        super(plugin, name);
    }
    
    @Override
    protected String getValue(Player player) {
        return String.valueOf(RedPlayerUtils.getTotalXp(player));
    }
    
    @Override
    protected boolean applyValue(Player target, String input) {
        if (input.length() > String.valueOf(MAX_XP).length()) {
            return false;
        }
        
        long xp = Long.parseLong(input);
        if (xp < 0 || xp > MAX_XP) {
            return false;
        }
        
        // Apply XP
        RedPlayerUtils.setXp(target, xp);
        target.setTotalExperience(xp < Integer.MAX_VALUE ? (int) xp : Integer.MAX_VALUE);
        return true;
    }
}
