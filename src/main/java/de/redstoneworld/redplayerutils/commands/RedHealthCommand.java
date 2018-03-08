package de.redstoneworld.redplayerutils.commands;

import de.redstoneworld.redplayerutils.AbstractValueCommand;
import de.redstoneworld.redplayerutils.RedPlayerUtils;
import org.bukkit.entity.Player;

public class RedHealthCommand extends AbstractValueCommand {
    
    public RedHealthCommand(RedPlayerUtils plugin, String name) {
        super(plugin, name);
    }
    
    @Override
    protected String getValue(Player player) {
        return String.valueOf(player.getHealth());
    }
    
    @Override
    protected boolean applyValue(Player target, String input) {
        double healthLevel = Double.parseDouble(input);
        if (healthLevel > 20) {
            return false;
        }

        // Apply health level
        target.setHealth(healthLevel);
        return true;
    }

}
