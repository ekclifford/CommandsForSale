package me.Tecno_Wizard.CommandsForSale.commandProcessors;

import me.Tecno_Wizard.CommandsForSale.core.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * This class will be assigned to the commands /buycmd, /confirm, and /deny when
 * the economy has failed
 * 
 * @author Ethan
 *
 */
public class VaultFailedBuyCmdsExecutor implements CommandExecutor {
	Main main;

	public VaultFailedBuyCmdsExecutor(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(String.format("%s[%s] Oops! It seems that Vault or Vault's economy hookup has failed. Please contact a server administrator.",
						ChatColor.RED,
						main.getConfig().getString("PluginPrefix")));
		return true;
	}

}
