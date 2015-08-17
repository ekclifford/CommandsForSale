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
    private static String[] illegals = {"AIR", "WATER", "STATIONARY_WATER", "LAVA", "STATIONARY_LAVA", "BED_BLOCK", "PISTON_EXTENSION", "PISTON_MOVING_PIECE",
            "DOUBLE_STEP", "FIRE", "REDSTONE_WIRE", "CROPS", "SIGN_POST", "WOODEN_DOOR", "WALL_SIGN", "IRON_DOOR_BLOCK", "GLOWING_REDSTONE_ORE",
            "REDSTONE_TORCH_OFF", "SUGAR_CANE_BLOCK", "PORTAL", "CAKE_BLOCK", "DIODE_BLOCK_OFF", "DIODE_BLOCK_ON", "PUMPKIN_STEM",
            "MELON_STEM", "NETHER_WARTS", "BREWING_STAND", "CAULDRON", "ENDER_PORTAL", "REDSTONE_LAMP_ON", "WOOD_DOUBLE_STEP", "COCOA",
            "TRIPWIRE", "FLOWER_POT", "CARROT", "POTATO", "SKULL", "REDSTONE_COMPARATOR_OFF", "REDSTONE_COMPARATOR_ON", "STANDING_BANNER",
            "WALL_BANNER", "DAYLIGHT_DETECTOR_INVERTED", "DOUBLE_STONE_SLAB2", "SPRUCE_DOOR", "BIRCH_DOOR", "JUNGLE_DOOR",
            "ACACIA_DOOR", "DARK_OAK_DOOR"};

    public static ItemStack getItemstackFromString(String input) {
        try {
            // System.out.println(input + ": " + isLegal(input));
            if (isLegal(input)) {
                return new ItemStack(Material.valueOf(input), 1);
            } else Bukkit.getConsoleSender().sendMessage(ChatColor.RED
                    + "[CommandsForSale] Error: could not parse item entry \"" + input + "\". It does not match any existing materials. Double check the material list. " +
                    "Replaced with fermented spider eye.");
        } catch (IllegalArgumentException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED
                    + "[CommandsForSale] Error: could not parse item entry \"" + input + "\". It does not match any existing materials. Double check the material list. " +
                    "Replaced with fermented spider eye.");
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED
                    + "[CommandsForSale] Error: could not parse item entry \"" + input + "\". It does not match any existing materials. Double check the material list. " +
                    "Replaced with fermented spider eye.");
        }
        return new ItemStack(Material.FERMENTED_SPIDER_EYE, 1);
    }

    private static boolean isLegal(String input) {
        boolean isLegal = true;
        for (String value : illegals) {
            if (value.equalsIgnoreCase(input)) {
                isLegal = false;
                break;
            }
        }
        return isLegal;
    }
}