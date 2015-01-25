package me.Tecno_Wizard.CommandsForSale.commandProcessors.modCommands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.Tecno_Wizard.CommandsForSale.core.Main;
/**
 * Adds a command to the config file. Controlled by ModCommandsController
 * @author Ethan Zeigler
 *
 */
public class AddCmdExecutor {
	Main main;
	String pluginPrefix;

	public AddCmdExecutor(Main main) {
		this.main = main;
		pluginPrefix = main.getResources().getPluginPrefix();
	}

	//called by the Mod command controller
	public void runCommand(CommandSender sender, String[] args) {
		if (checkForValidArguments(sender, args)) {
			Set<String> listWithNewCommand = main.getResources().getCmds();
			listWithNewCommand.add(args[1].toLowerCase());
			main.getConfig().set("MainCommands", listWithNewCommand);
			main.saveConfig();
			main.setUpConfig();//to create options menu for added command

			sender.sendMessage(String.format("%s[%s] The command was added." +
					" Check the config to set the aliases of the command, price, and permission node.\n" +
					"The command will appear in the GUI at the next reload of the plugin.\n" +
					"/cfs reload"
					, ChatColor.GREEN, pluginPrefix));
		}
	}

	private boolean checkForValidArguments(CommandSender sender, String[] args) {
		if (args.length == 2) {
			// args[0] is addcommand, args[1] is the command name
			// if there isn't a command already with the
			if (!main.getConfig().getStringList("AllCommands").contains(args[1].toLowerCase())) {
				return true;
			} else {
				// if the command already exists
				sender.sendMessage(String.format("%s[%s] The command that was given is already stated in the config."
						, ChatColor.RED, pluginPrefix));
			}
		} else {
			//invalid syntax
			sender.sendMessage(String.format("%s[%s] The correct usage is %s/cmdsforsale addcmd <Command Name>"
					, ChatColor.YELLOW, pluginPrefix, ChatColor.GOLD));
		}
		return false;
	}
}