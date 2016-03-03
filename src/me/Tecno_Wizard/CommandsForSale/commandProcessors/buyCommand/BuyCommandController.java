package me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand;

import me.Tecno_Wizard.CommandsForSale.core.Main;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class BuyCommandController implements CommandExecutor {
	Main main;
	private static Economy econ;
	private static final String META_KEY_CMD = "CommandToBuy";
	private static String pluginPrefix;
	private BuyCommandExecutor bce;
	ConfirmCommandExecutor cce;

	public BuyCommandController(Main main) {
		this.main = main;
		econ = Main.econ;
		pluginPrefix = main.getResources().getPluginPrefix();
		bce = new BuyCommandExecutor(main);
		cce = new ConfirmCommandExecutor(main);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// if sender is a player
		if (sender instanceof Player) {
			if(!sender.hasPermission("cmdsforsale.buyexempt")){
				// the following methods are called in this manner to allow the commands to be called by the GUI
				if (cmd.getName().equalsIgnoreCase("buycmd")) {
					this.preformBuyCmd(sender, args, false);
				}

				else if (cmd.getName().equalsIgnoreCase("confirm")) {
					this.preformConfirm(sender, args);
				}

				else if (cmd.getName().equalsIgnoreCase("deny")) {
					this.preformDeny(sender, args);
				}
				// handled, close
				return true;
			}
			sender.sendMessage(String.format("%s[%s] You do not need to buy commands. You are exempt!",
					ChatColor.GREEN, main.getResources().getPluginPrefix()));
			return true;
		}
		// sender is console
		sender.sendMessage(String.format("[%s] Silly console, you have %s%sALL the commands!",
				pluginPrefix, ChatColor.ITALIC, ChatColor.UNDERLINE));
		return true;
	}

	public boolean preformBuyCmd(CommandSender sender, String[] args, boolean isGUI){
		// if sender is a player
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 1) {
				BuyCommandResponse response = bce.perform(player, args, isGUI);
				switch (response) {
				case CONFIRM:
					// Message is performed inside of perform() to have
					// access to the price variable
					player.setMetadata(META_KEY_CMD, new FixedMetadataValue(main, args[0].toLowerCase()));
					return true;

				case DENY_AWAITNG_CONFIRM_RESPONSE:
					player.sendMessage(String.format("%s[%s] You are currently awaiting confirmation for another command." +
									" %s/confirm %sor %s/deny %sfirst.",
							ChatColor.RED, pluginPrefix,
							ChatColor.GOLD, ChatColor.RED,
							ChatColor.GOLD, ChatColor.RED));
					break;

				case DENY_CMD_DOES_NOT_EXIST:
					player.sendMessage(String.format("%s[%s] That command is not purchasable." +
									" If you are typing in an alias of the main command, such as bal instead of balance," +
									" it will not be recognized.",
							ChatColor.RED, pluginPrefix));
					break;

				case DENY_FUNDS:
					player.sendMessage(String.format("%s[%s] You do not have enough %s to buy this command",
							ChatColor.RED, pluginPrefix,
							econ.currencyNamePlural()));
					break;

				case DENY_HAS_COMMAND:
					sender.sendMessage(String.format("%s[%s] You already have this command",
							ChatColor.AQUA, pluginPrefix));
					break;

				case DENY_DOES_NOT_HAVE_PERM:
					player.sendMessage(String.format("%s[%s] You don't have permission to buy that command!",
							ChatColor.RED, pluginPrefix));
					break;
				}
				//handled, return
				return false;
			}
			// incorrect syntax
			player.sendMessage(String.format("%s[%s] The correct usage is %s/buycmd <Command Name>",
					ChatColor.YELLOW, pluginPrefix, ChatColor.GOLD));
			return false;
		}
		// sender is console
		sender.sendMessage(String.format("[%s] Silly console, you have %s%sALL the commands!",
				pluginPrefix, ChatColor.ITALIC, ChatColor.UNDERLINE));
		return false;
	}

	public void preformConfirm(CommandSender sender, String[] args){
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				ConfirmationResponse response = cce.perform(player);
				switch (response) {
				case CONFIRM_READY:
					player.sendMessage(String.format(
							"%s[%s] You bought the command %s!",
							ChatColor.GOLD, pluginPrefix, MetaUtils
							.getMetadataValueAsString(player,
									META_KEY_CMD)));
					// meta is not removed in the perform for printout
					player.removeMetadata(META_KEY_CMD, main);
					/* ((Player) sender).playSound(((Player) sender).getLocation(),
							Sound.CHICKEN_EGG_POP, 1, 5); */

					// above is breaking in 1.9. Will wait until 1.9 becomes standard to make breaking for 1.8 and below
					break;
				case FAIL_NOT_AWAITING_RESPONSE:
					player.sendMessage(String.format("%s[%s] You are not trying to purchase any command",
							ChatColor.RED, pluginPrefix));
					break;
				case DENIED_NOT_ENOUGH_MONEY:
					sender.sendMessage(String.format("%s[%s] You cannot afford this!",
							ChatColor.RED, pluginPrefix));
					player.removeMetadata(META_KEY_CMD, main);
					break;
				case FAILED_ECONOMY:
					// message was already sent to player in preform()
					Bukkit.getLogger().warning(String.format("[%s] An error occurred when %s tried to buy a command. "
									+ "If this continues, check the status of your economy plugin.",
									pluginPrefix, player.getDisplayName()));
					break;
					// CONFIRM_DENIED is missing, exclusive use for deny
				default:
					player.sendMessage(String.format(
							"%s[%s] UNKNOWN ERROR. TRY AGAIN",
							ChatColor.RED, pluginPrefix));
					break;
				}
				// has been handled, close
				return;
			}
			// Incorrect syntax
			player.sendMessage(String.format("%s[%s] Incorrect usage. No arguments are necessary for this command.",
					ChatColor.YELLOW, pluginPrefix));
			return;
		}
		// sender is console
		sender.sendMessage(String.format("[%s] Silly console, you have %s%sALL the commands!",
				pluginPrefix, ChatColor.ITALIC, ChatColor.UNDERLINE));
	}

	public void preformDeny(CommandSender sender, String[] args){
		if(sender instanceof Player){
			Player player = (Player) sender;

			if (args.length == 0) {
				ConfirmationResponse response = DenyCommandExecutor.perform(player);
				switch (response) {
				case CONFIRM_DENIED:
					player.sendMessage(String.format("%s[%s] Purchase cancelled",
							ChatColor.GRAY,pluginPrefix));
					player.removeMetadata(META_KEY_CMD, main);
					break;
				case FAIL_NOT_AWAITING_RESPONSE:
					player.sendMessage(String.format("%s[%s] You are not trying to purchase any command",
							ChatColor.RED, pluginPrefix));
					break;
				default:
					player.sendMessage(String.format(
							"%s[%s] UNKNOWN ERROR. TRY AGAIN",
							ChatColor.RED, pluginPrefix));
				}
				return;
			}
			// Incorrect syntax
			player.sendMessage(String.format("%s[%s] Incorrect usage. No arguments are necessary for this command.",
							ChatColor.YELLOW, pluginPrefix));
			return;
		}
		sender.sendMessage(String.format("[%s] Silly console, you have %s%sALL the commands!",
				pluginPrefix, ChatColor.ITALIC, ChatColor.UNDERLINE));
	}
}