package com.Tecno_Wizard.CommandsForSale.commandProcessors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ethan on 6/14/2015.
 */
public class BuyingController implements CommandExecutor {

    //controlls /buycmd, /buyonce
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            if(cmd.getName() == "buycmd"){
                // open GUI


            } else {

            }
        }
        return true;
    }
}
