package com.Tecno_Wizard.CommandsForSale.config;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Ethan Zeigler on 8/6/2015 for CommandsForSale.
 */
public abstract class CommandGroup {
    private double permBuyPrice;
    private double oneTimeBuyPrice;
    private String neededPermission;
    private String groupName;
    private boolean canBeOneTimeUsed;
    private ItemStack guiIcon;
    private List<String> exampleCommands;

    /** Represents a group of purchasable commands
     *
     * @param permBuyPrice Price to permanently buy
     * @param oneTimeBuyPrice Price to buy a 1 time use pass
     * @param neededPermission Permission needed to buy the command group
     * @param groupName Name of the command group
     * @param canBeOneTimeUsed Can be used once
     * @param guiIcon ItemStack used as display in GUI
     * @param exampleCommands A list of Commands that display as an example. Primarily for regexes, but also for very large command groups.
     */
    public CommandGroup(double permBuyPrice, double oneTimeBuyPrice, String neededPermission,
                        String groupName, boolean canBeOneTimeUsed, ItemStack guiIcon,
                        List<String> exampleCommands) {
        this.permBuyPrice = permBuyPrice;
        this.oneTimeBuyPrice = oneTimeBuyPrice;
        this.neededPermission = neededPermission;
        this.groupName = groupName;
        this.canBeOneTimeUsed = canBeOneTimeUsed;
        this.guiIcon = guiIcon;
        this.exampleCommands = exampleCommands;
    }

    public abstract boolean containsCommand();

    public List<String> getExampleCommands() {
        return exampleCommands;
    }

    public double getPermBuyPrice() {
        return permBuyPrice;
    }

    public double getOneTimeBuyPrice() {
        return oneTimeBuyPrice;
    }

    public String getNeededPermission() {
        return neededPermission;
    }

    public String getGroupName() {
        return groupName;
    }

    public boolean isCanBeOneTimeUsed() {
        return canBeOneTimeUsed;
    }

    public ItemStack getGuiIcon() {
        return guiIcon;
    }
}