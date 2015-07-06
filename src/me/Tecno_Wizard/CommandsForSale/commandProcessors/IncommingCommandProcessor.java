package me.Tecno_Wizard.CommandsForSale.commandProcessors;

import java.util.ArrayList;

import com.skionz.dataapi.DataFile;
import me.Tecno_Wizard.CommandsForSale.core.Main;

import me.Tecno_Wizard.CommandsForSale.core.Resources;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Runs before any command runs to check to see if it needs to be purchased
 *
 * @author Ethan Zeigler
 *
 */
public class IncommingCommandProcessor implements Listener {
    Main main;

    public IncommingCommandProcessor(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
        if (!e.getPlayer().hasPermission("cmdsforsale.buyexempt")) {
            String command = e.getMessage();
            command = command.substring(1);
            String[] args = command.split(" ");

            // if the command must be purchased for use
            if (main.getConfig().getStringList("AllCommands").contains(args[0].toLowerCase())) {
                ArrayList<String> purchased = main.getResources().getPlayerFile(e.getPlayer()).read();
                // if the player has not purchased the command
                if (!purchased.contains(args[0].toLowerCase())) {
                    DataFile file = main.getResources().getPlayerPassFile(e.getPlayer());
                    Integer value;
                    if(file.isInt(args[0].toLowerCase())) {
                        value = file.getInt(args[0].toLowerCase());
                    } else value = 0;
                    if(value > 0){
                        Resources.sendMessage("Using command pass! " + (value-1) + " remaining!", e.getPlayer(), ChatColor.GREEN);
                        file.set(args[0].toLowerCase(), value-1);
                        file.save();
                    } else {
                        e.getPlayer().sendMessage(
                                String.format("%s[%s] %s%s %sor its main command such as /balance to /bal must be purchased before it can be used.\n" +
                                                "Use /buyonce <Command Name> to buy a pass to use once, if it is unlocked to do so.",
                                        ChatColor.RED, main.getResources().getPluginPrefix(), ChatColor.GOLD,
                                        args[0], ChatColor.RED));
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
