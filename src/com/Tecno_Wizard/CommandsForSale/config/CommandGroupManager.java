package com.Tecno_Wizard.CommandsForSale.config;

import java.util.List;

/**
 * Created by Ethan Zeigler on 8/6/2015 for CommandsForSale.
 */
public class CommandGroupManager {
    private static CommandGroupManager ourInstance;
    List<CommandGroup> commandGroups;


    public static void reset() {

    }

    public static CommandGroupManager getInstance() {
        return ourInstance;
    }

    private CommandGroupManager() {

    }



    public CommandGroup getCommandGroup(String containedCommand) {

    }

    public List<CommandGroup> getCommandGroups() {
        return commandGroups;
    }
}
