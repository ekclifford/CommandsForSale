package me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.BuyCommandOnce;

import com.skionz.dataapi.DataFile;
import me.Tecno_Wizard.CommandsForSale.core.Main;
import me.Tecno_Wizard.CommandsForSale.core.Resources;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
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
                    BuyOnceAnalyzer boa = new BuyOnceAnalyzer(main);
                    switch(boa.checkPrice(main.getResources().getCommand(args[1].toLowerCase()), sender)){
                        case CONFIRM:
                            EconomyResponse response =
                                    Main.econ.withdrawPlayer((Player)sender, main.getResources().getCommand(args[1].toLowerCase()).getSinglePrice());
                            if(response.transactionSuccess()){
                                Resources.sendMessage("You bought one pass!", sender, ChatColor.GREEN);
                                DataFile playerFile = main.getResources().getPlayerFile((Player)sender);
                            } else {
                                Resources.sendMessage("An error occured. Please try again later.", sender, ChatColor.DARK_RED);
                            }
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
