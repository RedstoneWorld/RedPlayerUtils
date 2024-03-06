package de.redstoneworld.redplayerutils.commands;

import de.redstoneworld.redplayerutils.AbstractValueCommand;
import de.redstoneworld.redplayerutils.RedPlayerUtils;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class RedXpCommand extends AbstractValueCommand {
    
    public RedXpCommand(RedPlayerUtils plugin, String name) {
        super(plugin, name);
    }
    
    @Override
    protected String getValue(Player player) {
        return String.valueOf(RedPlayerUtils.getTotalXp(player));
    }
    
    @Override
    protected boolean applyValue(Player target, String input) {
        if (input.length() >= String.valueOf(Long.MAX_VALUE).length()) {
            return false;
        }
        long xp = Long.parseLong(input);
        if (xp < 0 || xp > Integer.MAX_VALUE) {
            return false;
        }
        // Apply xp level
        RedPlayerUtils.setXp(target, xp);
        target.setTotalExperience(xp < Integer.MAX_VALUE ? (int) xp : Integer.MAX_VALUE);
        return true;
    }
}
