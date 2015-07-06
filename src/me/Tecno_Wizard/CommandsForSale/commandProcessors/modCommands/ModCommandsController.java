package me.Tecno_Wizard.CommandsForSale.commandProcessors.modCommands;

import me.Tecno_Wizard.CommandsForSale.core.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Controls all Moderator only commands.
 * @author Ethan Zeigler
 *
 */
public class ModCommandsController implements CommandExecutor 
{
    Main main;
    String pluginPrefix;

    public ModCommandsController(Main main) {
        this.main = main;
        pluginPrefix = main.getResources().getPluginPrefix();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "reload":
                    ReloadConfigExecutor rce = new ReloadConfigExecutor(main);
                    rce.runCommand(sender, args);
                    break;
                case "addcmd":
                    AddCmdExecutor ace = new AddCmdExecutor(main);
                    ace.runCommand(sender, args);
                    break;
                case "updatestatus":
                    UpdatePluginExecutor uce = new UpdatePluginExecutor(main);
                    uce.runCommand(sender);
                    break;
                case "addalias":
                    AddCmdAliasExecutor acae = new AddCmdAliasExecutor(main);
                    acae.runCommand(sender, args);
                    break;
                default:
                    sender.sendMessage(String.format("[%s] That is not a valid subcommand. <reload || addcmd || addalias || updatestatus>", pluginPrefix));
                    break;
            }
        } else {
            sender.sendMessage(String.format("[%s] That requires a subcommand. <reload || addcmd || addalias || updatestatus>", pluginPrefix));
        }
        return true;
    }
}