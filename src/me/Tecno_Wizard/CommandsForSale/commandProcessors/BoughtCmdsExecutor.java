package me.Tecno_Wizard.CommandsForSale.commandProcessors;

import java.util.ArrayList;

import me.Tecno_Wizard.CommandsForSale.core.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.skionz.dataapi.ListFile;

/**
 * Called by /boughtcmds. Prints out the commands that have been bought by a
 * user.
 * 
 * @author Ethan Zeigler
 *
 */

public class BoughtCmdsExecutor implements CommandExecutor {
	private String pluginPrefix;
	private Main main;

	public BoughtCmdsExecutor(Main main) {
		pluginPrefix = main.getResources().getPluginPrefix();
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// if the sender is a player and not the console
		if (sender instanceof Player) {
			// gives commands of self
			if (args.length == 0) {
				StringBuilder sb = new StringBuilder();

				ListFile playerFile = main.getResources().getPlayerFile((Player) sender);
				ArrayList<String> boughtCmds = playerFile.read();

				if (boughtCmds.isEmpty()){
					sender.sendMessage(String.format("%s[%s] You do not have any commands!",
							ChatColor.AQUA, pluginPrefix));
				}
				sb.append(String.format("%s[%s] You have purchased: ",ChatColor.AQUA, pluginPrefix));
				for (String toAdd : boughtCmds) {
					sb.append(toAdd + ", ");
				}
				sb.delete(sb.length()-2, sb.length());
				sender.sendMessage(sb.toString());
			}
			// requesting another player
			else if (args.length == 1) {
				// if the player is a moderator
				if (sender.hasPermission("cmdsforsale.moderator")) {
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					if (target.hasPlayedBefore()) {
						StringBuilder sb = new StringBuilder();
						ArrayList<String> boughtCmds = main.getResources().getPlayerFile((Player) sender).read();
						sb.append(String.format(
								"%s[%s] That player has purchased- ",
								ChatColor.GREEN, pluginPrefix));
						for (String toAdd : boughtCmds) {
							sb.append(toAdd + " ");
						}

						sender.sendMessage(sb.toString());
					}
					// never been on server
					else {
						sender.sendMessage(String.format("%s[%s] That player has never been on the server",
								ChatColor.RED, pluginPrefix));
					}
				}
				// not a moderator

				else {
					sender.sendMessage(String.format("%s[%s] You do not have permission to do that",
							ChatColor.RED, pluginPrefix));
				}
			}
			// wrong syntax
			else {
				sender.sendMessage(String.format("%s[%s] The proper use is /boughtcmds [Player Name]",
						ChatColor.YELLOW, pluginPrefix));
			}
		}
		// sender is console
		else {
			if (args.length == 0) {
				sender.sendMessage(String.format(
						"[%s] Silly console, you have all the commands!",
						pluginPrefix));
				return true;
			} else if (args.length == 1) {
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
				if (target.hasPlayedBefore()) {
					StringBuilder sb = new StringBuilder();
					// gets the list of player's commands
					ArrayList<String> boughtCmds = main.getResources().getPlayerFile(target).read();

					sb.append(String.format("[%s] You have purchased- ",
							pluginPrefix));
					for (String toAdd : boughtCmds) {
						sb.append(toAdd + " ");
					}
					sender.sendMessage(sb.toString());
				}
				// if player has never been on the server / does not exist
				else {
					sender.sendMessage(String.format(
							"[%s] That player has never been on the server",
							pluginPrefix));
				}
			}
			// invalid syntax
			else {
				sender.sendMessage(String.format(
						"[%s] The proper use is /boughtcmds <Player Name>",
						pluginPrefix));
			}
		}
		return true;
	}

}
