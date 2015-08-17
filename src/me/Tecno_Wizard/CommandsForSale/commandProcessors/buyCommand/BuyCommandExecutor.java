package me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand;

import me.Tecno_Wizard.CommandsForSale.core.Main;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.skionz.dataapi.ListFile;

/**
 * Handels the execution of /buycmd to the controller
 * @author Ethan
 *
 */
public class BuyCommandExecutor {
    private static Main main;
    private static Economy econ;
    public static final String META_KEY_CMD = "CommandToBuy";
    private static String pluginPrefix;

    public BuyCommandExecutor(Main main) {
        BuyCommandExecutor.main = main;
        econ = Main.econ;
        pluginPrefix = main.getResources().getPluginPrefix();
    }

    public BuyCommandResponse perform(Player sender, String[] args, boolean isGUI){
        args[0] = args[0].toLowerCase();
        // System.out.println(MetaUtils.getMetadataValueAsString(sender, META_KEY_CMD));
        if(!MetaUtils.hasMetadata(sender, META_KEY_CMD)) {
            if(main.getResources().getCmds().contains(args[0].toLowerCase())) {
                ListFile file = main.getResources().getPlayerPermanentFile(sender.getUniqueId().toString());
                //read player file to see if has cmd
                if(!file.read().contains(args[0]))
                {
                    String perm = main.getResources().getCommand(args[0]).getPermission();
                    if (!main.getResources().getCommand(args[0].toLowerCase()).needsPerm() || sender.hasPermission(perm)) {
                        // purchase process
                        Double price = main.getResources().getCommand(args[0]).getPermanentPrice();//gets the price of the command
                        //if the user has enough money to buy the command
                        if (isGUI || econ.getBalance(sender) >= price){
                            // is ready, check to see if GUI
                            if (!isGUI) {
                                sender.sendMessage(String.format("%s[%s] Do you want to buy the command %s%s %sfor %s%s %s%s? %s\n/confirm or /deny",
                                        ChatColor.YELLOW, pluginPrefix, ChatColor.GOLD, args[0], ChatColor.YELLOW, ChatColor.GOLD, price.toString(),
                                        ChatColor.YELLOW, main.getResources().getCurrencyPlural(), ChatColor.YELLOW));
                            }
                            return BuyCommandResponse.CONFIRM;
                        }
                        //if not enough money
                        return BuyCommandResponse.DENY_FUNDS;
                    }
                    //if did not have perm
                    return BuyCommandResponse.DENY_DOES_NOT_HAVE_PERM;
                }
                //if already has command
                return BuyCommandResponse.DENY_HAS_COMMAND;
            }
            //if command does not exist as specified
            return BuyCommandResponse.DENY_CMD_DOES_NOT_EXIST;
        }
        //if is waiting for command response already
        return BuyCommandResponse.DENY_AWAITNG_CONFIRM_RESPONSE;
    }
}
