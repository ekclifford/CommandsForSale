package me.Tecno_Wizard.CommandsForSale.commandProcessors.modCommands;

import java.util.ArrayList;

import me.Tecno_Wizard.CommandsForSale.core.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AddCmdAliasExecutor
{
	Main main;
	String pluginPrefix;

	public AddCmdAliasExecutor(Main main)
	{
		this.main = main;
		pluginPrefix = main.getResources().getPluginPrefix();
	}
	public void runCommand(CommandSender sender, String[] args)
	{
		if(checkForValidArgs(args, sender))
		{
			ArrayList<String> aliases = (ArrayList<String>) main.getConfig().getStringList("Aliases." + args[1].toLowerCase());
			aliases.add(args[2].toLowerCase());
			main.getConfig().set("Aliases." + args[1].toLowerCase(), aliases);
			sender.sendMessage(String.format("%s[%s] Alias added", ChatColor.GREEN, pluginPrefix));
			main.saveConfig();
			main.setUpConfig();
		}
	}

	public boolean checkForValidArgs(String[] args, CommandSender sender) {
		//one for addcmd, one for main command, one for alias
		if(args.length == 3){
			if(main.getConfig().contains("Aliases." + args[1].toLowerCase())) {
				//if the commands does not already have the alias
				if(!main.getConfig().getStringList("Aliases." + args[1].toLowerCase()).contains(args[2].toLowerCase())){
					return true;
				} else /*if already has alias*/{
					sender.sendMessage(String.format("[%s] That is already listed as an alias", pluginPrefix));
				}
			} else/*if does not contain command*/ {
				sender.sendMessage(String.format("[%s] That command does not exist.", pluginPrefix));
				return false;
			}
		}
		else
		{
			sender.sendMessage(String.format("[%s] Improper use: %s/cfs addalias <Command Name> <Aliase Name>", pluginPrefix, ChatColor.GOLD));
			return false;
		}
		return false;//this should never run if everything works
	}
}
