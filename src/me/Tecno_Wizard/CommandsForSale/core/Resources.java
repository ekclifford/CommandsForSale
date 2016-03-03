package me.Tecno_Wizard.CommandsForSale.core;

import com.skionz.dataapi.DataFile;
import com.skionz.dataapi.ListFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
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
    private static String blockedCmdMessage;
    private static HashMap<String, Command> cmds = new HashMap<>();
    private static ListFile purchaseLog;

    private boolean displayVerisonInfo;
    private String versionInformation =
            ChatColor.AQUA + "[CommandsForSale] Welcome to CommandsForSale Version 1.2.7!\n" +
                    "Broke annoying auto updater forever, 1.9 compatibility, added one time use price in GUI";


    Resources(Main main) {
        FileConfiguration config = main.getConfig();
        currencyPlural = config.getString("CurrencyPlural");
        pluginPrefix = config.getString("PluginPrefix");
        blockedCmdMessage  = config.getString("Messages.hasNotBoughtCmd");

        for (String cmd : main.getConfig().getStringList("MainCommands")) {
            // get values
            double permPrice = config.getDouble("CommandOptions." + cmd.toLowerCase() + ".price");
            double oneTimePrice = config.getDouble("CommandOptions." + cmd.toLowerCase() + ".oneTimeUsePrice");
            String perm = config.getString("CommandOptions." + cmd.toLowerCase() + ".permission");
            boolean canBeOneTimeUsed = config.getBoolean("CommandOptions." + cmd.toLowerCase() + ".canBeOneTimeUsed");
            String material = config.getString("CommandOptions." + cmd.toLowerCase() + ".GUIIcon");
            // set perm to null if void
            if (perm.equalsIgnoreCase("void"))
                perm = null;

            List<String> aliases = config.getStringList("Aliases." + cmd);

            // add cmd
            cmds.put(cmd, new Command(cmd, permPrice, oneTimePrice, perm, canBeOneTimeUsed, aliases, material));

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
            double oneTimePrice = config.getDouble("CommandOptions." + cmd.toLowerCase() + ".oneTimeUsePrice");
            String perm = config.getString("CommandOptions." + cmd.toLowerCase() + ".permission");
            boolean canBeOneTimeUsed = config.getBoolean("CommandOptions." + cmd.toLowerCase() + ".canBeOneTimeUsed");
            String material = config.getString("CommandOptions." + cmd.toLowerCase() + ".GUIIcon");

            // set perm to null if void
            if (perm.equalsIgnoreCase("void"))
                perm = null;

            List<String> aliases = config.getStringList("Aliases." + cmd);

            // add cmd
            cmds.put(cmd, new Command(cmd.toLowerCase(), price, oneTimePrice, perm, canBeOneTimeUsed, aliases, material));
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

    public ListFile getPlayerFile(OfflinePlayer player){
        return new ListFile("plugins/CommandsForSale/Players/" + player.getUniqueId(), "txt");
    }

    public ListFile getPlayerPermanentFile(String UUID){
        return new ListFile("plugins/CommandsForSale/Players/" + UUID, "txt");
    }

    public DataFile getPlayerPassFile(String UUID){
        return new DataFile("plugins/CommandsForSale/PlayerPasses/" + UUID, "txt");
    }

    public DataFile getPlayerPassFile(Player player){
        return this.getPlayerPassFile(player.getUniqueId().toString());
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

    public void logPermanentPurchase(Player sender, String boughtCmd, Double price) {
        Date date = new Date();
        String timeStamp = date.toString();

        // true format (Ex) "[Mon May 04 09:51:52 CDT 2009] Tecno_Wizard||a090200e-42f4-49da-8426-44a2010a8483 bought the command test for 20.0 dollars
        purchaseLog.addLine(String.format("[%s] %s||%s bought the command %s for %s %s",
                timeStamp, sender.getName(), sender.getUniqueId(), boughtCmd, price.toString(), getCurrencyPlural()));
    }


    public void logPassPurchase(Player sender, String boughtCmd, Double price){
        Date date = new Date();
        String timeStamp = date.toString();

        purchaseLog.addLine(String.format("[%s] %s||%s bought a one time pass for %s for %s %s",
                timeStamp, sender.getName(), sender.getUniqueId(), boughtCmd, price.toString(), getCurrencyPlural()));
    }

    public void logString(String msg){
        purchaseLog.addLine(msg);
    }

    public static void sendMessage(String msg, CommandSender recipient){
        sendMessage(msg, recipient, ChatColor.RESET);
    }
    public static void sendMessage(String msg, CommandSender recipient, ChatColor startColor){
        recipient.sendMessage(String.format("%s[%s] %s", startColor, pluginPrefix, msg));
    }


    public static String getBlockedCmdMessage() {
        return blockedCmdMessage;
    }

    public boolean isDisplayVerisonInfo() {
        return displayVerisonInfo;
    }
}

