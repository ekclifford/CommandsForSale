package me.Tecno_Wizard.CommandsForSale.commandProcessors.modCommands;

import me.Tecno_Wizard.CommandsForSale.core.Main;

import org.bukkit.command.CommandSender;
/**
 * Displays info on the last update attempt through the command /cfs updatestatus. Controlled by ModCommandsController
 * @author Ethan Zeigler
 *
 */
public class UpdatePluginExecutor
{
	Main main;
	String pluginPrefix;

	public UpdatePluginExecutor(Main main)
	{
		this.main = main;
		pluginPrefix = main.getResources().getPluginPrefix();
	}

	public void runCommand(CommandSender sender)
	{
		if(Main.getUpdater() != null)
		{
			sender.sendMessage(String.format("[%s] Displaying information from last reload/restart. To check again, reload the server:", pluginPrefix));
			switch(Main.getUpdater().getResult().toString().toLowerCase())
			{
			case "success": sender.sendMessage(String.format("[%s] The next version is ready. Reload the server to implement the new version of CommandsForSale", pluginPrefix));
			break;
			case "disabled": sender.sendMessage(String.format("[%s] The automatic updater is off. Change this in the config file.", pluginPrefix));
			break;
			case "fail_noversion":
			case "fail_badid":
				sender.sendMessage(String.format("[%s] Something is wrong with the plugin file. Please contact Tecno_Wizard immedietly through the plugin page, but check to make sure it is not already listed as a known problem.", pluginPrefix));
				break; 
			case "no_update": sender.sendMessage(String.format("[%s] You're running the newest version, YAY!", pluginPrefix));
			break;
			case "fail_dbo":
			case "fail_download": sender.sendMessage(String.format("[%s] Failed to connect with BukkitDev. Prehaps the site is down?", pluginPrefix));
			break;
			case "fail_apikey": sender.sendMessage(String.format("[%s] Something with this server's API is not correct. Change this in the Updater config file.", pluginPrefix));
			break;
			case "update_available": sender.sendMessage(String.format("[%s] There is an update avalible. Turning on auto-updates in the config then reloading will update the plugin's file (if no other issues exist), or it can be done manually through the BukkitDev site. As soon as the server is reloaded again or restarted, the update will be impemented.", pluginPrefix));
			}
			return;
		}
		sender.sendMessage(String.format("[%s] Updater is off. No further information.", pluginPrefix));
	}
}
