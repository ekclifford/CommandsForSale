package com.Tecno_Wizard.CommandsForSale.language;

import com.skionz.dataapi.DataFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Tecno_Wizard on 6/15/2015.
 * Protected under GNU General Public License V3
 */
public class Language {
    JavaPlugin plugin;

    public enum Name{
        ENGLISH,
        FRANCAIS
    }
    private DataFile file;

    public Language(JavaPlugin plugin) {
        this.plugin = plugin;
        String language = plugin.getConfig().getString("language");
        switch(language) {
            case "ENGLISH":
                plugin.saveResource("EN.txt", false);
                file = new DataFile("")

        }
    }


    public void switchLanguage(Name name) {
        switch(name) {
            case ENGLISH:
                plugin.

        }
    }
}
