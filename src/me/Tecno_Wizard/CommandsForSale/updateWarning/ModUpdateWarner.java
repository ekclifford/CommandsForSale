package me.Tecno_Wizard.CommandsForSale.updateWarning;

import me.Tecno_Wizard.CommandsForSale.core.Main;
import me.Tecno_Wizard.CommandsForSale.core.Updater.UpdateResult;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.UUID;

public class ModUpdateWarner implements Listener {
	Main main;
	ArrayList<UUID> warned = new ArrayList<>();
	
	public ModUpdateWarner(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onModLogin(PlayerJoinEvent e){
		// if player is a moderator
		if(e.getPlayer().hasPermission("cmdsforsale.moderator")){
			if(Main.getUpdater().getResult().equals(UpdateResult.UPDATE_AVAILABLE)) {
				e.getPlayer().sendMessage(String.format("%s|%s[%s] There is an update available! Change AutomaticallyUpdate to true or manually download from BukkitDev.%s|",
						ChatColor.MAGIC, ChatColor.RESET, "CommandsForSale", ChatColor.MAGIC));
			}
			if(main.getResources().displayVerisonInfo() && !warned.contains(e.getPlayer().getUniqueId())){
				e.getPlayer().sendMessage(main.getResources().getVersionInformation());
				warned.add(e.getPlayer().getUniqueId());
			}
		}
	}
}
