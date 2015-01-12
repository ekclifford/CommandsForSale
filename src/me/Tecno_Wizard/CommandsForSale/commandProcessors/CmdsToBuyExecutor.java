package me.Tecno_Wizard.CommandsForSale.commandProcessors;

import java.util.ArrayList;

import me.Tecno_Wizard.CommandsForSale.core.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Called by /cmdstobuy. Displays all the commands the sender can buy.
 * 
 * @author Ethan Zeigler
 *
 */
public class CmdsToBuyExecutor implements CommandExecutor {
	Main main;
	String pluginPrefix;

	public CmdsToBuyExecutor(Main main) {
		this.main = main;
		pluginPrefix = main.getResources().getPluginPrefix();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			boolean didNotHaveCommand = false;
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%s[%s] The commands you can buy are:",
					ChatColor.AQUA, pluginPrefix));

			ArrayList<String> bought = main.getResources().getPlayerFile((Player) sender).read();
			for (String commands : main.getResources().getCmds()) {
				// if has not been bought
				if (!bought.contains(commands)) {
					didNotHaveCommand = true;
					sb.append(" " + commands);
				}
			}

			if (!didNotHaveCommand) {
				sender.sendMessage(String.format("%s[%s] It looks like you have %saaaaaallllllll the commands!",
								ChatColor.GREEN,
								main.getConfig().getString("PluginPrefix"),
								ChatColor.ITALIC));
			} else {
				sender.sendMessage(sb.toString());
			}
		}
		// sender is console
		else {
			sender.sendMessage(String.format("[%s] You don't need this! You have %saaaaaallllllll the commands!",
							main.getConfig().getString("PluginPrefix"),
							ChatColor.ITALIC));
		}
		return true;
	}
}
