package com.Tecno_Wizard.CommandsForSale.config;

import java.util.regex.Pattern;

/**
 * Created by Ethan Zeigler on 8/6/2015 for CommandsForSale.
 */
public class RegexCommandGroup extends CommandGroup {
    Pattern pattern;

    @Override
    public boolean containsCommand() {
        return false;
    }
}
