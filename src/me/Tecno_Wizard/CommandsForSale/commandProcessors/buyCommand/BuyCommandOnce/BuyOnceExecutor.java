package me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.BuyCommandOnce;

import com.skionz.dataapi.DataFile;
import me.Tecno_Wizard.CommandsForSale.GUI.SwitchInventoryRunnable;
import me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.BuyCommandExecutor;
import me.Tecno_Wizard.CommandsForSale.core.Main;
import me.Tecno_Wizard.CommandsForSale.core.Resources;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

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
        if (!(sender instanceof Player)) {
            Resources.sendMessage("Buying one time use passes is for players only!", sender);
        } else {
            execute((Player) sender, args);
        }
        return true;
    }

    public void execute(Player sender, String[] args){
        if (args.length == 1) {
            BuyOnceAnalyzer boa = new BuyOnceAnalyzer(main);
            switch (boa.attemptBuy(main.getResources().getCommand(args[0].toLowerCase()), sender)) {
                case CONFIRM:
                    EconomyResponse response =
                            Main.econ.withdrawPlayer((Player) sender, main.getResources().getCommand(args[0].toLowerCase()).getSinglePrice());
                    if (response.transactionSuccess()) {
                        main.getResources().logPassPurchase((Player)sender, args[0].toLowerCase(),response.amount);
                        givePass(args[0].toLowerCase(), sender);
                    } else {
                        Resources.sendMessage("An error occurred. Please try again later.", sender, ChatColor.DARK_RED);
                    }
                    break;
                case DENY_CANNOT_BE_BOUGHT:
                    Resources.sendMessage("That command must be bought permanently", sender, ChatColor.RED);
                    break;
                case DENY_CMD_DOES_NOT_EXIST:
                    Resources.sendMessage("That command is not recognized. Make sure you are using the main command.", sender, ChatColor.RED);
                    break;
                case DENY_FUNDS:
                    Resources.sendMessage("You don't have enough money for that", sender, ChatColor.RED);
                    break;
                case DENY_DOES_NOT_HAVE_PERM:
                    Resources.sendMessage("You do not have the necessary permissions to buy this", sender, ChatColor.RED);
                    break;
                default:
                    Resources.sendMessage("An unexpected error occurred. Try again later.", sender, ChatColor.RED);
                    break;
            }
            return;
        } else if (args.length == 2) { // is price lenght
            if (args[1].equalsIgnoreCase("price")) {
                BuyOnceAnalyzer boa = new BuyOnceAnalyzer(main);
                switch (boa.checkPrice(main.getResources().getCommand(args[0].toLowerCase()), sender)) {
                    //confirm done in analyzer
                    case CONFIRM:
                        break;
                    case DENY_CMD_DOES_NOT_EXIST:
                        Resources.sendMessage("That command isn't recognized. Make sure you are using the main command", sender, ChatColor.RED);
                        break;
                    case DENY_CANNOT_BE_BOUGHT:
                        Resources.sendMessage("You can't buy a 1 time pass for that command", sender, ChatColor.RED);
                        break;
                    default:
                        Resources.sendMessage("An unexpected error occurred. Please try again later and contact an admin.", sender, ChatColor.RED);
                }
                return;
            } else {
                //invalid args
                Resources.sendMessage("Invalid arguments. For price check, use " + ChatColor.GOLD + "/buyonce <Command> price",
                        sender, ChatColor.RED);
                return;
            }
        }
        else {
            // invalid arguments
            Resources.sendMessage("Invalid arguments. For price check, use " + ChatColor.GOLD + "/buyonce <Command> price\n" +
                            ChatColor.RED + "For buying, use " + ChatColor.GOLD + "/buyonce <Command>",
                    sender, ChatColor.RED);
        }
    }

    private void givePass(String cmd, CommandSender sender) {
        Resources.sendMessage("You bought one pass!", sender, ChatColor.GREEN);
        ((Player) sender).playSound(((Player) sender).getLocation(),
                Sound.CHICKEN_EGG_POP, 1, 5);
        DataFile playerFile = main.getResources().getPlayerPassFile((Player)sender);
        if(!playerFile.isInt(cmd)){
            playerFile.set(cmd, 0);
        }
        playerFile.set(cmd, playerFile.getInt(cmd) + 1);
        playerFile.save();
    }
}