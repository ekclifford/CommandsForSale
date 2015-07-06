package me.Tecno_Wizard.CommandsForSale.GUI;

import me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.MetaUtils;
import me.Tecno_Wizard.CommandsForSale.core.Resources;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SwitchInventoryRunnable extends BukkitRunnable {
    private static final String META_KEY_CMD = "CommandToBuy";
	private Player player;
	private boolean sendConfirmMenu;
    private int type;
    private Resources resources;

    /**
     * runnable that switches the active inventory
     * @param player player
     * @param sendConfirmMenu true to send menu
     * @param type 0 for no pass, 1 for pass
     */
	public SwitchInventoryRunnable(Player player, boolean sendConfirmMenu, int type, Resources resources) {
        this.resources = resources;
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
                player.openInventory(GUIConstructor.getConfirmPageWPass(resources.getCommand(MetaUtils.getMetadataValueAsString(player, META_KEY_CMD)), resources.getCurrencyPlural()));
            } else {
                player.openInventory(GUIConstructor.getConfirmPageWOPass());
            }
        }
        this.cancel();
	}

}
