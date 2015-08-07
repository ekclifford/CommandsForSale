package me.Tecno_Wizard.CommandsForSale.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Ethan Zeigler on 7/3/2015 for BukkitPluginTemplate.
 */
public class UpdateScheduler implements Updater.UpdateCallback {

    JavaPlugin plugin;
    public UpdateScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when the updater has finished working.
     *
     * @param updater The updater instance
     */
    @Override
    public void onFinish(Updater updater) {
        switch (updater.getResult()) {
            case FAIL_APIKEY:
                Bukkit.getLogger().warning("[CommandsForSale] Updater Failed: bad API key");
                break;
            case FAIL_BADID:
                Bukkit.getLogger().warning("[CommandsForSale] Updater Failed: bad plugin ID- report this to the plugin author.");
                break;
            case FAIL_DOWNLOAD:
                Bukkit.getLogger().warning("[CommandsForSale] Update Failed: unable to download at this time.");
                break;
            case MANUAL_UPDATE_AVAILABLE:
                Bukkit.getLogger().info("[CommandsForSale] Update Available: an update to the plugin is available, but it is marked for manual download. " +
                        "Look for it on dev.bukkit.org");
                break;
            case FAIL_NOVERSION:
                Bukkit.getLogger().warning("[CommandsForSale] Update Failed: the latest version on dev.bukkit.org does not follow correct naming conventions. " +
                        "Report this to the plugin author");
                break;
            case UPDATE_AVAILABLE:
                Bukkit.getLogger().info("[CommandsForSale] Update Available: an update to the plugin is available, but the updater has been instructed to not automatically update. " +
                        "Look for it on dev.bukkit.org");
                break;
            case FAIL_DBO:
                Bukkit.getLogger().warning("[CommandsForSale] Update Failed: unable to contact dev.bukkit.org. It may be experiencing downtime.");
                break;
            case SUCCESS:
                Bukkit.getLogger().info("[CommandsForSale] Update Success!: reload or restart server to load the new version of the plugin");
                Bukkit.getServer().getScheduler().cancelTasks(plugin);
                return;
            case DISABLED:
                return;
            case CRITICAL_UPDATE_AVALIBLE:
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[CommandsForSale] Update Advised!: The latest BukkitDev file is marked as a critical update, meaning that there " +
                        "is likely a significant bug in later versions of the plugin. Please update ASAP.");
        }
    }
}
