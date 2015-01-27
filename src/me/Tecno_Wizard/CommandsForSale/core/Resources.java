package me.Tecno_Wizard.CommandsForSale.core;

import com.skionz.dataapi.ListFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Ethan Zeigler
 *         Holds quick resources the CommandsForSale config
 */
public class Resources {
    private static String pluginPrefix;
    private static String currencyPlural;
    private static HashMap<String, Command> cmds = new HashMap<>();
    private static ListFile purchaseLog;

    private boolean displayVerisonInfo;
    private String versionInformation =
            ChatColor.AQUA + "[CommandsForSale] Welcome to CommandsForSale Version 1.2!\n" +
                    "Here's what has been added:\n" +
                    "Players can now be marked exempt for buying commands using the permission node cmdsforsale.buyexempt\n" +
                    "This allows you to specify purchase exempt players without giving them moderator abilites\n" +
                    "GUI's are here! The system should be pretty self explanatory, just type /buycmd instead of /buycmd <Command>\n" +
                    "Note that specifying the command will still work as it used to. The GUI can be turned off in the config.\n" +
                    "All purchases are now logged in the plugin's folder. Go and check it out!\n" +
                    "The next update will feature one time use commands!";

    Resources(Main main) {
        FileConfiguration config = main.getConfig();
        currencyPlural = config.getString("CurrencyPlural");
        pluginPrefix = config.getString("PluginPrefix");

        for (String cmd : main.getConfig().getStringList("MainCommands")) {
            // get values
            double price = config.getDouble("CommandOptions." + cmd.toLowerCase() + ".price");
            String perm = config.getString("CommandOptions." + cmd.toLowerCase() + ".permission");

            // set perm to null if void
            if (perm.equalsIgnoreCase("void"))
                perm = null;

            List<String> aliases = config.getStringList("Aliases." + cmd);

            // add cmd
            cmds.put(cmd, new Command(cmd, price, perm, aliases));

            // get all commands
        }

        Date date = new Date();
        String rawDate = date.toString();
        rawDate = rawDate.replace(":", ".");
        purchaseLog = new ListFile("plugins/CommandsForSale/PurchaseLogs/" + rawDate, "txt");
        displayVerisonInfo = false;
    }

    public static void refresh(Main main){
        FileConfiguration config = main.getConfig();
        currencyPlural = config.getString("CurrencyPlural");
        pluginPrefix = config.getString("PluginPrefix");

        // clear cmds
        cmds.clear();

        // reset cmds
        for (String cmd : main.getConfig().getStringList("MainCommands")) {
            // get values
            double price = config.getDouble("CommandOptions." + cmd.toLowerCase() + ".price");
            String perm = config.getString("CommandOptions." + cmd.toLowerCase() + ".permission");

            // set perm to null if void
            if (perm.equalsIgnoreCase("void"))
                perm = null;

            List<String> aliases = config.getStringList("Aliases." + cmd);

            // add cmd
            cmds.put(cmd, new Command(cmd.toLowerCase(), price, perm, aliases));
        }
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }

    public String getCurrencyPlural() {
        return currencyPlural;
    }

    public Command getCommand(String cmd){
        return cmds.get(cmd);
    }

    public Set<String> getCmds(){
        return cmds.keySet();
    }

    public ListFile getPlayerFile(Player player){
        return new ListFile("plugins/CommandsForSale/Players/" + player.getUniqueId(), "txt");
    }

    public ListFile getPlayerFile(String UUID){
        return new ListFile("plugins/CommandsForSale/Players/" + UUID, "txt");
    }

    public String getVersionInformation() {
        return versionInformation;
    }

    public boolean displayVerisonInfo() {
        return displayVerisonInfo;
    }

    public void setDisplayVerisonInfo(boolean value){
        displayVerisonInfo = value;
        if (value){
            Bukkit.getConsoleSender().sendMessage(versionInformation);
        }
    }

    public void logPurchase(Player sender, String boughtCmd, Double price) {
        Date date = new Date();
        String timeStamp = date.toString();

        // true format (Ex) "[Mon May 04 09:51:52 CDT 2009] Tecno_Wizard||a090200e-42f4-49da-8426-44a2010a8483 bought the command test for 20.0 dollars
        purchaseLog.addLine(String.format("[%s] %s||%s bought the command %s for %s %s",
                timeStamp, sender.getName(), sender.getUniqueId(), boughtCmd, price.toString(), getCurrencyPlural()));
    }

    public void logString(String msg){
        purchaseLog.addLine(msg);
    }

}

