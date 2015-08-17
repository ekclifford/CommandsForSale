package com.Tecno_Wizard.CommandsForSale.config;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Ethan Zeigler on 8/6/2015 for CommandsForSale.
 */
public class ListCommandGroup extends CommandGroup {
    private List<String> containedCommands;

    public ListCommandGroup(double permBuyPrice, double oneTimeBuyPrice, String neededPermission, String groupName, boolean canBeOneTimeUsed, boolean onlyContainsOneCommand, ItemStack guiIcon, List<String> exampleCommands, List<String> containedCommands) {
        super(permBuyPrice, oneTimeBuyPrice, neededPermission, groupName, canBeOneTimeUsed, onlyContainsOneCommand, guiIcon, exampleCommands);
        this.containedCommands = containedCommands;
    }

    @Override
    public boolean containsCommand(String command) {
        return containedCommands.contains(command);
    }
}
