package me.Tecno_Wizard.CommandsForSale.GUI;

import java.io.File;
import java.util.ArrayList;

import me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand.BuyCommandController;
import me.Tecno_Wizard.CommandsForSale.core.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.skionz.dataapi.ListFile;

/**
 * handles /buycmd when the GUI is turned on.
 * @author Ethan Zeigler
 *
 */
public class GUIConstructor implements CommandExecutor{
	private Main main;
	private ArrayList<ItemStack> buyableCmds = new ArrayList<>();
	private String pluginPrefix;
	private static Inventory confirmPage = null;
	private static ItemStack overflowIcon = null;

	public GUIConstructor(Main main) {
		this.main = main;
		this.pluginPrefix = main.getResources().getPluginPrefix();
		this.prepare();
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {

		if(sender instanceof Player){
			if(args.length == 0) {
				Inventory inv = this.getFirstInventory((Player) sender);

				// check to see if inventory is empty
				boolean isEmpty = true;
				for (ItemStack item : inv.getContents()) {
					if (item != null)
						isEmpty = false;
				}
				// if the inv is not empty, send it to the player
				if (!isEmpty) {
					((Player) sender).openInventory(getFirstInventory((Player) sender));
					return true;
				}
				// has all the commands
				sender.sendMessage(String.format("%s[%s] You have all of the commands you can own right now!", ChatColor.GREEN, pluginPrefix));
				return true;
			} else if (args.length == 1) {
				// arg was given, use non-gui system.
				BuyCommandController bcc = new BuyCommandController(main);
				bcc.preformBuyCmd(sender, args, false);
			} else {
				// incorrect syntax
				sender.sendMessage(String.format("%s[%s] Incorrect use. %s/buycmd [Command Name]",
						ChatColor.RED, pluginPrefix, ChatColor.GOLD));
			}
			return true;
		}
		// sender will be console
		sender.sendMessage(String.format("[%s] Silly console, you have all of the commands!", pluginPrefix));

		return true;
	}


	private void prepare(){

		ItemStack[] icons = new ItemStack[15];

		icons[0] = new ItemStack(Material.STAINED_GLASS, 1, (short) 1);
		icons[1] = new ItemStack(Material.STAINED_GLASS, 1, (short) 2);
		icons[2] = new ItemStack(Material.STAINED_GLASS, 1, (short) 3);
		icons[3] = new ItemStack(Material.STAINED_GLASS, 1, (short) 4);
		icons[4] = new ItemStack(Material.STAINED_GLASS, 1, (short) 5);
		icons[5] = new ItemStack(Material.STAINED_GLASS, 1, (short) 6);
		icons[6] = new ItemStack(Material.STAINED_GLASS, 1, (short) 7);
		icons[7] = new ItemStack(Material.STAINED_GLASS, 1, (short) 8);
		icons[8] = new ItemStack(Material.STAINED_GLASS, 1, (short) 9);
		icons[9] = new ItemStack(Material.STAINED_GLASS, 1, (short) 10);
		icons[10] = new ItemStack(Material.STAINED_GLASS, 1, (short) 11);
		icons[11] = new ItemStack(Material.STAINED_GLASS, 1, (short) 12);
		icons[12] = new ItemStack(Material.STAINED_GLASS, 1, (short) 13);
		icons[13] = new ItemStack(Material.STAINED_GLASS, 1, (short) 14);
		icons[14] = new ItemStack(Material.STAINED_GLASS, 1, (short) 15);

		// create menu ArrayList
		for(String command: main.getConfig().getStringList("MainCommands")){

			// get the icon symbol and create a meta for it
			// the mess with getting the stack is just randomly generating an int between 1 and 15
			ItemStack icon = new ItemStack(icons[(int)((Math.random()*14) + 0.5)]);
			ItemMeta meta = icon.getItemMeta();

			// get the price of the command
			Double price = main.getConfig().getDouble("CommandOptions." + command + ".price");

			// set the display name of the icon
			meta.setDisplayName(ChatColor.GOLD + "/" + command);

			// set the lore value
			ArrayList<String> lore = new ArrayList<>();
			String currency = main.getResources().getCurrencyPlural();
			lore.add(String.format("%sClick to buy the command %s for %s %s", ChatColor.AQUA, command, price, currency));
			meta.setLore(lore);

			// apply the meta to the object
			icon.setItemMeta(meta);

			// add the icon
			buyableCmds.add(icon);
		}

		if (confirmPage == null) {
			// create inventory for confirm/deny

			// create icons
			ItemStack confirmIcon = new ItemStack(Material.STAINED_GLASS, 1, (short) 5);
			ItemStack denyIcon = new ItemStack(Material.STAINED_GLASS, 1, (short) 14);
			ItemMeta confirmMeta = confirmIcon.getItemMeta();
			ItemMeta denyMeta = denyIcon.getItemMeta();
			confirmMeta.setDisplayName(ChatColor.GOLD + "Confirm Purchase");
			denyMeta.setDisplayName(ChatColor.GOLD + "Deny Purchase");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.AQUA + "Click to confirm purchase");
			confirmMeta.setLore(lore);
			lore.clear();
			lore.add(ChatColor.RED + "Click to deny purchase");
			denyMeta.setLore(lore);
			confirmIcon.setItemMeta(confirmMeta);
			denyIcon.setItemMeta(denyMeta);

			// create inventory
			confirmPage = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE + pluginPrefix);
			confirmPage.setItem(3, confirmIcon);
			confirmPage.setItem(5, denyIcon);
		}

		/* if overflow icon is null */
		if (overflowIcon == null){
			ItemStack overflowIconRaw = new ItemStack(Material.FIREBALL, 1);
			ItemMeta meta = overflowIconRaw.getItemMeta();
			meta.setDisplayName(String.format("%sWARNING: OVERFLOW!", ChatColor.RED));
			// create lore
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.RED + "There are more commands that are not shown.");
			lore.add("");
			lore.add(ChatColor.RED + "If you know what command you want and it is not shown,");
			lore.add(ChatColor.RED + "just type " + ChatColor.GOLD + "/buycmd <Command Name>");
			lore.add("");
			lore.add(ChatColor.RED + "You will be able to buy it if you have permission to.");
			meta.setLore(lore);
			overflowIconRaw.setItemMeta(meta);
			overflowIcon = overflowIconRaw;
		}
	}

	public Inventory getFirstInventory(Player player){

		Inventory iv = Bukkit.createInventory(null, 36, ChatColor.LIGHT_PURPLE + pluginPrefix);

		ListFile file = new ListFile("plugins" + File.separator + "CommandsForSale" + File.separator + "Players"
				+ File.separator + player.getUniqueId().toString(), "txt");
		ArrayList<String> bought = file.read();
		int pos = 1;
		FileConfiguration config = main.getConfig();
		for (ItemStack is: buyableCmds) {
			// lores of 5 is the command name
			String rawLore = is.getItemMeta().getLore().get(0);
			String [] lores = rawLore.split(" ");
			if(!bought.contains(lores[5].toLowerCase())){
				String perm = config.getString("CommandOptions."+ lores[5] + ".permission");
				if (perm.equalsIgnoreCase("void") || player.hasPermission("perm")) {
					iv.addItem(is);
					pos++;
				}
			}
			if(pos == 36) {
				iv.addItem(overflowIcon);
				break;
			}
		}//end of loop through icons
		return iv;
	}

	public static Inventory getSecondInventory(){
		return confirmPage;
	}
}