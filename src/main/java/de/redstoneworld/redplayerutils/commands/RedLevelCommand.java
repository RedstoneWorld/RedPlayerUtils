package de.redstoneworld.redplayerutils.commands;

import de.redstoneworld.redplayerutils.AbstractValueCommand;
import de.redstoneworld.redplayerutils.RedPlayerUtils;
import org.bukkit.entity.Player;

public class RedLevelCommand extends AbstractValueCommand {
    
    public RedLevelCommand(RedPlayerUtils plugin, String name) {
        super(plugin, name);
    }
    
    @Override
    protected String getValue(Player player) {
        return String.valueOf(player.getLevel());
    }
    
    @Override
    protected boolean applyValue(Player target, String input) {
        int level = Integer.parseInt(input);

        // Apply level level
        target.setLevel(level);
        target.setExp(0);
        return true;
    }

}
