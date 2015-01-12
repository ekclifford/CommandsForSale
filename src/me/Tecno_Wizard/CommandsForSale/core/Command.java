package me.Tecno_Wizard.CommandsForSale.core;

import java.util.List;

/**
 * @author Ethan Zeigler
 */
public class Command {
    private String cmdName;
    private double price;
    private String perm;
    private List<String> aliases;

    public Command(String cmdName, double price, String perm, List<String> aliases) {
        this.cmdName = cmdName;
        this.price = price;
        this.perm = perm;
        this.aliases = aliases;
    }

    public String getCommandName() {
        return cmdName;
    }

    public double getPrice() {
        return price;
    }

    public String getPermission() {
        return perm;
    }

    public List<String> getAliases(){
        return this.aliases;
    }

    public boolean needsPerm() {
        if (perm == null)
            return false;
        else
            return true;
    }
}
