package me.Tecno_Wizard.CommandsForSale.GUI;

import me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.MetaUtils;
import me.Tecno_Wizard.CommandsForSale.core.Main;
import me.Tecno_Wizard.CommandsForSale.core.Resources;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class SwitchInventoryRunnable extends BukkitRunnable {
	private Player player;
	private boolean sendConfirmMenu;
    private int type;
    private Main main;
    public static final String META_KEY_CMD = "CommandToBuy";

    /**
     * runnable that switches the active inventory
     * @param player player
     * @param sendConfirmMenu true to send menu
     * @param type 0 for no pass, 1 for pass
     */
	public SwitchInventoryRunnable(Player player, boolean sendConfirmMenu, int type, Main main) {
		this.player = player;
		this.sendConfirmMenu = sendConfirmMenu;
        this.type = type;
        this.main = main;
	}

	@Override
	public void run() {
		player.closeInventory();
		// if true, the command needs to be confirmed. Send the confirm menu
        if (sendConfirmMenu) {
            if (type == 1) {
                // get confirm menu
                Inventory inv = GUIConstructor.getConfirmPageWPass();
                // get command name and get Command object
                String cmdName = MetaUtils.getMetadataValueAsString(player, META_KEY_CMD);
                // get price
                double price = main.getResources().getCommand(cmdName).getSinglePrice();
                // item 6 is the pass icon (Really should have made a dict for that.. Little late now)
                ItemStack icon = inv.getItem(6).clone(); 
                ItemMeta meta = icon.getItemMeta();

                // all of the following is necessary to prevent alias modification. Cloned objects, ugh why.
                
                List<String> lore = meta.getLore();
                String currency = main.getResources().getCurrencyPlural();
                lore.set(0, lore.get(0) + price + " " + currency);
                meta.setLore(lore);
                icon.setItemMeta(meta);
                inv.setItem(6, icon);
                player.openInventory(inv);
            } else {
                player.openInventory(GUIConstructor.getConfirmPageWOPass());
            }
        }
        this.cancel();
	}

}
