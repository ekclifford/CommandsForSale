package me.Tecno_Wizard.CommandsForSale.core;

import java.util.List;

/**
 * @author Ethan Zeigler
 */
public class Command {
    // command name
    private String cmdName;
    // permanent purchase price
    private double permPrice;
    // single use price
    private double singlePrice;
    // permission
    private String perm;
    // aliases
    private List<String> aliases;
    // can be one time used
    private boolean canBeOneTimeUsed;

    public Command(String cmdName, double permPrice, double singlePrice, String perm, boolean oneTimeuse, List<String> aliases) {
        this.cmdName = cmdName;
        this.permPrice = permPrice;
        this.singlePrice = singlePrice;
        this.perm = perm;
        this.aliases = aliases;
        this.canBeOneTimeUsed = oneTimeuse;
    }

    public String getCommandName() {
        return cmdName;
    }

    public double getPermanentPrice() {
        return permPrice;
    }

    public double getSinglePrice(){
        return this.singlePrice;
    }

    public String getPermission() {
        return perm;
    }

    public List<String> getAliases(){
        return this.aliases;
    }

    public boolean needsPerm() {
        if (perm == null) return false;
            return true;
    }

    public boolean canBeOneTimeUsed(){
        return canBeOneTimeUsed;
    }
}
