package me.Tecno_Wizard.CommandsForSale.core;

import com.skionz.dataapi.ListFile;
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
    private static List<String> allCmds;

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
            allCmds = config.getStringList("AllCommands");
        }

        Date date = new Date();
        String rawDate = date.toString();
        rawDate = rawDate.replace(":", ".");
        purchaseLog = new ListFile("plugins/CommandsForSale/PurchaseLogs/" + rawDate, "txt");
        purchaseLog.addLine("The plugin was started at " + date.toString());
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

