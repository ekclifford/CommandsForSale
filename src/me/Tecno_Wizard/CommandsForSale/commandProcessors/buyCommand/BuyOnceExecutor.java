package me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand;

import me.Tecno_Wizard.CommandsForSale.core.Main;
import me.Tecno_Wizard.CommandsForSale.core.Resources;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static org.bukkit.ChatColor.*;

/**
 * Created by Ethan on 3/10/2015.
 */
public class BuyOnceExecutor implements CommandExecutor{
    private Main main;

    public BuyOnceExecutor(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){

        } else {
            if(args.length == 2) {
                if(args[1].equalsIgnoreCase("price")){
                    // checking price of arg 1
                    if(main.getResources().getCommand(args[1].toLowerCase()) != null){
                        //TODO check for price, if can be one time bought
                    } else {
                        Resources.sendMessage("Error, " + args[1].toLowerCase() + " is not seen as a command." +
                                "\n make sure that you are using the MAIN command, not an alias.", sender, ChatColor.RED);
                        return true;
                    }
                }
                sender.sendMessage("Command ");
            } else if(args.length > 2){
                Bukkit.getConsoleSender().sendMessage(String.format("%s[%s] Error: invalid parameters. Console can only check price of commands." +
                        "Use %s/buyonce <Command Name> price %sto view price.", RED, main.getResources().getPluginPrefix(),
                        GOLD, RED));
            }
        }
        return true;
    }


}
