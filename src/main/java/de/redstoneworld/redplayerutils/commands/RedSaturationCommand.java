package de.redstoneworld.redplayerutils.commands;

import de.redstoneworld.redplayerutils.AbstractValueCommand;
import de.redstoneworld.redplayerutils.RedPlayerUtils;
import org.bukkit.entity.Player;

public class RedSaturationCommand extends AbstractValueCommand {
    private final int MAX_LEVEL = 20;
    
    public RedSaturationCommand(RedPlayerUtils plugin, String name) {
        super(plugin, name);
    }
    
    @Override
    protected String getValue(Player player) {
        return String.valueOf(player.getSaturation());
    }
    
    @Override
    protected boolean applyValue(Player target, String input) {
        float saturationLevel = Float.parseFloat(input);

        if (saturationLevel > MAX_LEVEL) {
            return false;
        }

        // Apply saturation level
        target.setSaturation(saturationLevel);
        return true;
    }

}
