package me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand;

import me.Tecno_Wizard.CommandsForSale.core.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.skionz.dataapi.ListFile;

/**
 * Handles the execution of /confirm
 * @author Ethan Zeigler
 *
 */
public class ConfirmCommandExecutor {
	private static final String META_KEY_CMD = "CommandToBuy";
	private static Economy econ;
	private static String pluginPrefix;
	static Main main;

	public ConfirmCommandExecutor(Main main) {
		ConfirmCommandExecutor.main = main;
		pluginPrefix = main.getResources().getPluginPrefix();
		econ = Main.econ;
	}

	public ConfirmationResponse perform(Player sender) {
		if(MetaUtils.hasMetadata(sender, META_KEY_CMD)) {
			String toBuy = MetaUtils.getMetadataValueAsString(sender, META_KEY_CMD);
			Double price = main.getResources().getCommand(toBuy).getPermanentPrice();

			//double check of if player can afford
			if(econ.getBalance(sender) >= price) {
				//takes out the money (if it can)
				EconomyResponse eResponse = econ.withdrawPlayer(sender, price);
				if(eResponse.transactionSuccess()) {
					ListFile file = main.getResources().getPlayerPermanentFile(sender.getUniqueId().toString());
					//adds the main command to usable
					file.addLine(toBuy.toLowerCase());
					// adds the aliases
					for(String alis: main.getResources().getCommand(toBuy).getAliases()) {
						//adds to the list of usable commands
						file.addLine(alis.toLowerCase());
					}
					//add the purchase to the log
					main.getResources().logPermanentPurchase(sender, toBuy, price);
					return ConfirmationResponse.CONFIRM_READY;
				}
				//if transaction failed
				sender.sendMessage(String.format("%s[%s] Transaction failure: %s", ChatColor.RED, pluginPrefix, eResponse.errorMessage));
				return ConfirmationResponse.FAILED_ECONOMY;
			}
			return ConfirmationResponse.DENIED_NOT_ENOUGH_MONEY;
		}
		return ConfirmationResponse.FAIL_NOT_AWAITING_RESPONSE;
	}
}