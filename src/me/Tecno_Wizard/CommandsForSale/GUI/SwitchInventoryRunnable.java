package me.Tecno_Wizard.CommandsForSale.GUI;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SwitchInventoryRunnable extends BukkitRunnable {
	Player player;
	boolean sendConfirmMenu;
	
	public SwitchInventoryRunnable(Player player, boolean sendConfirmMenu) {
		this.player = player;
		this.sendConfirmMenu = sendConfirmMenu;
	}
	@Override
	public void run() {
		player.closeInventory();
		// if true, the command needs to be confirmed. Send the confirm menu
		if (this.sendConfirmMenu) {
			player.openInventory(GUIConstructor.getSecondInventory());
		}
		this.cancel();
	}

}
