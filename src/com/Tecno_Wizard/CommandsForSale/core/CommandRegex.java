package com.Tecno_Wizard.CommandsForSale.core;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ethan Zeigler
 */
public class CommandRegex {
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
    // material representation
    private String material;
    // regex
    private Matcher regex;

    public CommandRegex(Pattern pattern, String cmdName, double permPrice, double singlePrice, String perm, boolean oneTimeuse, List<String> aliases, String material) {

        this.cmdName = cmdName;
        this.permPrice = permPrice;
        this.singlePrice = singlePrice;
        this.perm = perm;
        this.aliases = aliases;
        this.canBeOneTimeUsed = oneTimeuse;
        this.material = material;
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

    public String getMaterial() {
        return material;
    }
}
