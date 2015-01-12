package me.Tecno_Wizard.CommandsForSale.commandProcessors.modCommands;

import me.Tecno_Wizard.CommandsForSale.core.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 * Reloads the config file. Controlled by ModCommandsController
 * @author Ethan Zeigler
 *
 */
public class ReloadConfigExecutor 
{
	Main main;
	String pluginPrefix;

	public ReloadConfigExecutor(Main main) {
		this.main = main;
		pluginPrefix = main.getResources().getPluginPrefix();
	}

	public void runCommand(CommandSender sender, String[] args) {
		if(args.length==1) {
			if(sender instanceof Player) {
				sender.sendMessage(String.format("[CommandsForSale] The plugin was reloaded."));
				Bukkit.getLogger().info(String.format("[CommandsForSale] The plugin was reloaded"));
			} else {
				Bukkit.getLogger().info(String.format("[CommandsForSale] The plugin was reloaded."));
			}
			main.reloadSystem();
		}
		//if incorrect arguments
		else{
			sender.sendMessage(String.format("[%s] That is not the correct usage", pluginPrefix));
		}
	}
}
