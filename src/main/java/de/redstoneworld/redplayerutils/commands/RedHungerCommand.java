package de.redstoneworld.redplayerutils.commands;

import de.redstoneworld.redplayerutils.AbstractValueCommand;
import de.redstoneworld.redplayerutils.RedPlayerUtils;
import org.bukkit.entity.Player;

public class RedHungerCommand extends AbstractValueCommand {

    private final int MAX_HUNGER = 20;
    
    public RedHungerCommand(RedPlayerUtils plugin, String name) {
        super(plugin, name);
    }
    
    @Override
    protected String getValue(Player player) {
        return String.valueOf(player.getFoodLevel());
    }
    
    @Override
    protected boolean applyValue(Player target, String input) {
        int foodLevel = Integer.parseInt(input);

        if (foodLevel < 0 || foodLevel > MAX_HUNGER) {
            return false;
        }

        // Apply food
        target.setFoodLevel(foodLevel);
        return true;
    }
}
