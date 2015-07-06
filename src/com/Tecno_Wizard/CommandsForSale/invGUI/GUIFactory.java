package com.Tecno_Wizard.CommandsForSale.invGUI;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generates the GUIs of the
 * Created by Ethan on 6/14/2015.
 */
public class GUIFactory {



    private void setItemMetadata(ItemStack stack, String description, String... lore) {
        ArrayList<String> convertedLore = (ArrayList) Arrays.asList(lore);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(description);
        meta.setLore(convertedLore);
    }
}
