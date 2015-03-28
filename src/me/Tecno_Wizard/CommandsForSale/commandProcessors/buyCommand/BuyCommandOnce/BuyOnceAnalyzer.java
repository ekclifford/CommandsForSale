package me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.BuyCommandOnce;

import me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.BuyCommandResponse;
import me.Tecno_Wizard.CommandsForSale.core.Command;
import me.Tecno_Wizard.CommandsForSale.core.Main;
import me.Tecno_Wizard.CommandsForSale.core.Resources;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ethan on 3/14/2015.
 */
public class BuyOnceAnalyzer{
    Main main;

    public BuyOnceAnalyzer(Main main) {
        this.main = main;
    }


    /**
     * Prints out price if applicable, otherwise returns error reasons
     * @param cmd command
     * @return response
     */
    public BuyCommandResponse checkPrice(Command cmd, CommandSender sender){
        switch(validate(cmd)){
            case 0:
                return BuyCommandResponse.DENY_CMD_DOES_NOT_EXIST;
            case 1:
                return BuyCommandResponse.DENY_CANNOT_BE_BOUGHT;
            case 2:
                String output = "The cost of " + cmd.getCommandName() + " is " + cmd.getSinglePrice();
                Resources.sendMessage(output, sender, ChatColor.AQUA);
                return BuyCommandResponse.CONFIRM;
        }
        return null;
    }

    public BuyCommandResponse attemptBuy(Command cmd, CommandSender sender){
        switch(validate(cmd)){
            case 0:
                return BuyCommandResponse.DENY_CMD_DOES_NOT_EXIST;
            case 1:
                return BuyCommandResponse.DENY_CANNOT_BE_BOUGHT;
            case 2:
                if(cmd.getPermission() == null || sender.hasPermission(cmd.getPermission())) {
                    if (Main.econ.getBalance((Player) sender) >= cmd.getSinglePrice()) {
                        return BuyCommandResponse.CONFIRM;
                    }
                    return BuyCommandResponse.DENY_FUNDS;
                }
                return BuyCommandResponse.DENY_DOES_NOT_HAVE_PERM;
        }
        return null;
    }

    /**
     * validates command
     * @param cmd
     * @return 0 = noSuchCommand- 1 = cannotBeOneTimeBought- 2 = valid
     */
    private int validate(Command cmd){
        if(cmd != null){
            if(cmd.canBeOneTimeUsed()) {
                return 2;
            }
            return 1;
        }
        return 0;
    }




}
