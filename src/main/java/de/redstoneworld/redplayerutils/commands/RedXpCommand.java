package de.redstoneworld.redplayerutils.commands;

import de.redstoneworld.redplayerutils.AbstractValueCommand;
import de.redstoneworld.redplayerutils.RedPlayerUtils;
import org.bukkit.entity.Player;

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
        int xp = Integer.parseInt(input);

        // Apply xp level
        RedPlayerUtils.setXp(target, xp);
        target.setTotalExperience(xp);
        return true;
    }
}
