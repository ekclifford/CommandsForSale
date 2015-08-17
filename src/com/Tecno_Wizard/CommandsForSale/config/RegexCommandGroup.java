package com.Tecno_Wizard.CommandsForSale.config;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Ethan Zeigler on 8/6/2015 for CommandsForSale.
 */
public class RegexCommandGroup extends CommandGroup {
    private String regex;

    public RegexCommandGroup(double permBuyPrice, double oneTimeBuyPrice, String neededPermission, String groupName, boolean canBeOneTimeUsed, ItemStack guiIcon, List<String> exampleCommands, String regex) {
        super(permBuyPrice, oneTimeBuyPrice, neededPermission, groupName, canBeOneTimeUsed, false, guiIcon, exampleCommands);
        this.regex = regex;
    }

    @Override
    public boolean containsCommand(String command) {
        return command.matches(regex);
    }
}
