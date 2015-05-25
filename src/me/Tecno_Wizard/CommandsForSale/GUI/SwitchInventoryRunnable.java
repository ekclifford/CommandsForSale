package me.Tecno_Wizard.CommandsForSale.GUI;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SwitchInventoryRunnable extends BukkitRunnable {
	private Player player;
	private boolean sendConfirmMenu;
    private int type;

    /**
     * runnable that switches the active inventory
     * @param player player
     * @param sendConfirmMenu true to send menu
     * @param type 0 for no pass, 1 for pass
     */
	public SwitchInventoryRunnable(Player player, boolean sendConfirmMenu, int type) {
		this.player = player;
		this.sendConfirmMenu = sendConfirmMenu;
        this.type = type;
	}

	@Override
	public void run() {
		player.closeInventory();
		// if true, the command needs to be confirmed. Send the confirm menu
        if (sendConfirmMenu) {
            if (type == 1) {
                player.openInventory(GUIConstructor.getConfirmPageWPass());
            } else {
                player.openInventory(GUIConstructor.getConfirmPageWOPass());
            }
        }
        this.cancel();
	}

}
