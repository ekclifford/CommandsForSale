package me.Tecno_Wizard.CommandsForSale.updateWarning;

import me.Tecno_Wizard.CommandsForSale.core.Main;
import me.Tecno_Wizard.CommandsForSale.core.Updater.UpdateResult;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ModUpdateWarner implements Listener {
	String pluginPrefix;
	
	public ModUpdateWarner(Main main) {
		this.pluginPrefix = main.getResources().getPluginPrefix();
	}
	
	@EventHandler
	public void onModLogin(PlayerJoinEvent e){
		// if player is a moderator
		if(e.getPlayer().hasPermission("cmdsforsale.moderator")){
			if(Main.getUpdater().getResult().equals(UpdateResult.UPDATE_AVAILABLE)){
			e.getPlayer().sendMessage(String.format("%s|%s[%s] There is an update avalible! Change AutomaticallyUpdate to true or manually download from BukkitDev.%s|",
					ChatColor.MAGIC, ChatColor.RESET, pluginPrefix, ChatColor.MAGIC));
			}
		}
	}
}
