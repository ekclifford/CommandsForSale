package me.Tecno_Wizard.CommandsForSale.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Ethan on 4/2/2015.
 *
 */
public class ItemstackConverter {

    public static ItemStack getItemstackFromCode(String input) {
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


    // I started on this and didn't want to waste it, but it was dumb.
//    public static ItemStack getItemstackFromCode(String input){
//        switch(input){
//            //compass
//            case "1": return new ItemStack(Material.COMPASS, 1);
//            //clock
//            case "2": return new ItemStack(Material.WATCH, 1);
//            //nametag
//            case "3": return new ItemStack(Material.NAME_TAG, 1);
//            //feather
//            case "4": return new ItemStack(Material.FEATHER, 1);
//            //various mineables
//            case "5.1": return new ItemStack(Material.GOLD_INGOT, 1);
//            case "5.2": return new ItemStack(Material.DIAMOND, 1);
//            case "5.3": return new ItemStack(Material.REDSTONE, 1);
//            case "5.4": return new ItemStack(Material.IRON_INGOT, 1);
//            case "5.5": return new ItemStack(Material.EMERALD, 1);
//            //music disk
//            case "6": return new ItemStack(Material.RECORD_10, 1);
//            //potion
//            case "7": return new ItemStack(Material.POTION, 1);
//            //slimeball
//            case "8": return new ItemStack(Material.SLIME_BALL,1);
//            //fishing rod
//            case "9": return new ItemStack(Material.FISHING_ROD, 1);
//        }
//    }
}
