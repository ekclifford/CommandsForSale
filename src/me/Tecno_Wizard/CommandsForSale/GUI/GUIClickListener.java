package me.Tecno_Wizard.CommandsForSale.GUI;

import me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.BuyCommandController;
import me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.BuyCommandOnce.BuyOnceExecutor;
import me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.MetaUtils;
import me.Tecno_Wizard.CommandsForSale.core.Command;
import me.Tecno_Wizard.CommandsForSale.core.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

public class GUIClickListener implements Listener {
	private Main main;
	private BuyCommandController buyCmdCont;
    private BuyOnceExecutor buyOnceExecutor;
    private static final String META_KEY_CMD = "CommandToBuy";

	public GUIClickListener(Main main) {
		this.main = main;
		buyCmdCont = new BuyCommandController(main);
        buyOnceExecutor = new BuyOnceExecutor(main);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		// checks to see if the inventory is the buycommand menu
		if (ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase(main.getResources().getPluginPrefix())) {

			// cancel the event
			e.setCancelled(true);

			// catch errors that may occur due to the player clicking on things besides buttons
			try{
				// checks to see if the item has metadata (is one of the buttons)
				if (e.getCurrentItem().hasItemMeta()) {

					// now see what button was clicked
					ItemMeta meta = e.getCurrentItem().getItemMeta();
					
					// get the player. This cast is safe because nothing other than a player can open it.
					Player player = (Player) e.getWhoClicked();

					// if it starts with /, it is the first window.
					if (ChatColor.stripColor(meta.getDisplayName()).startsWith("/")) {
						// removes the / in front of the name
						String command = ChatColor.stripColor(meta.getDisplayName()).substring(1);

						/* run the command with notifications off
						if true is returned, there are no issues and can continue to confirm.
						 */

						boolean purchaseReadyToConfirm = buyCmdCont.preformBuyCmd(player, new String[]{command}, true);

						// close the inventory (The SAFE way) and open the second
                        Command cmd = main.getResources().getCommand(command.toLowerCase());
                        int type = cmd.canBeOneTimeUsed()? 1 : 0;

						BukkitTask task = new SwitchInventoryRunnable(player, purchaseReadyToConfirm, type).runTaskLater(main, 1);
						return;
					}
					// end of if starts with /
					
					// if is purchase confirm
					if(ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("Confirm Purchase")){
						buyCmdCont.preformConfirm(player, new String[0]);
						BukkitTask task = new SwitchInventoryRunnable(player, false, 0).runTaskLater(main, 1);
						
					}
					
					// if is purchase deny
					else if (ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("Deny Purchase")) {
						buyCmdCont.preformDeny(player, new String[0]);
						BukkitTask task = new SwitchInventoryRunnable(player, false, 0).runTaskLater(main, 1);
					}

                    // if it is buying a pass

                    else if(ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("One Use Pass")) {
                        String command = MetaUtils.getMetadataValueAsString(player, META_KEY_CMD);
                        buyOnceExecutor.execute(player, new String[]{command});
                    }
				}
			} catch (Exception error){}
		}
	}
}
