package me.Tecno_Wizard.CommandsForSale.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private static ItemStack passIcon;
    private static Inventory confirmPageWOPass;
    private static Inventory confirmPageWPass;
    private static ItemStack overflowIcon;

    public GUIConstructor(Main main) {
        this.main = main;
        this.pluginPrefix = main.getResources().getPluginPrefix();
        this.prepare();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {

        if(sender instanceof Player) {
            if (!sender.hasPermission("cmdsforsale.buyexempt")) {
                if (args.length == 0) {
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
            sender.sendMessage(String.format("%s[%s] You do not need to buy commands! You are exempt!", ChatColor.GREEN, main.getResources().getPluginPrefix()));
            return true;
        }
        // sender will be console
        sender.sendMessage(String.format("[%s] Silly console, you have all of the commands!", pluginPrefix));

        return true;
    }

    private void prepare(){
        // create menu ArrayList
        for(String command: main.getConfig().getStringList("MainCommands")){
            ItemStack icon = getItemstackFromString(main.getResources().getCommand(command.toLowerCase()).getMaterial());
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

        // pass icon
        passIcon = new ItemStack(Material.NAME_TAG);
        ItemMeta passMeta = passIcon.getItemMeta();
        passMeta.setDisplayName(ChatColor.GOLD + "One Use Pass");
        lore.clear();
        lore.add(ChatColor.AQUA + "Click to buy a 1 time use pass for ");
        passMeta.setLore(lore);
        passIcon.setItemMeta(passMeta);


        // create confirm inventory w/o pass
        confirmPageWOPass = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE + pluginPrefix);
        confirmPageWOPass.setItem(3, confirmIcon);
        confirmPageWOPass.setItem(5, denyIcon);

        // create confirm inventory w/ pass
        confirmPageWPass = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE + pluginPrefix);
        confirmPageWPass.setItem(2, confirmIcon);
        confirmPageWPass.setItem(4, denyIcon);
        confirmPageWPass.setItem(6, passIcon);


		/* if overflow icon is null, only existent because the in plugin reload does not delete icons*/
        if (overflowIcon == null){
            ItemStack overflowIconRaw = new ItemStack(Material.FIREBALL, 1);
            ItemMeta meta = overflowIconRaw.getItemMeta();
            meta.setDisplayName(String.format("%sWARNING: OVERFLOW!", ChatColor.RED));
            // create lore
            lore = new ArrayList<>();
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
                if ((perm.equalsIgnoreCase("void") || player.hasPermission(perm))) {
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

    public static ItemStack getItemstackFromString(String input) {
        try {
            return new ItemStack(Material.valueOf(input), 1);
        } catch (IllegalArgumentException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED
                    + "[CommandsForSale] Error: could not parse item entry \"" + input + "\". It does not match any existing materials." +
                    "The list of correct values depends on your craftbukkit/spigot version, and can be found by googling \"" +
                    "bukkit materials 'your version number'. They are case sensitive! You have been given a spider eye.");
            return new ItemStack(Material.FERMENTED_SPIDER_EYE, 1);
        }
        catch (NullPointerException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED
                    + "[CommandsForSale] Error: could not parse item entry \"" + input + "\". It does not match any existing materials." +
                    "The list of correct values depends on your craftbukkit/spigot version, and can be found by googling \"" +
                    "bukkit materials 'your version number'. They are case sensitive! You have been given a spider eye.");
            return new ItemStack(Material.FERMENTED_SPIDER_EYE, 1);
        }
    }

    public static Inventory getConfirmPageWPass(me.Tecno_Wizard.CommandsForSale.core.Command cmd, String currency) {
        ItemStack editedPass = passIcon.clone();
        ItemMeta meta = editedPass.getItemMeta();
        List<String> lore = meta.getLore();
        lore.set(0, meta.getLore().get(0) + cmd.getSinglePrice() + " " + currency);
        meta.setLore(lore);
        editedPass.setItemMeta(meta);
        confirmPageWPass.setItem(6, editedPass);
        return confirmPageWPass;
    }

    public static Inventory getConfirmPageWOPass() {
        return confirmPageWOPass;
    }

}