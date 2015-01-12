package me.Tecno_Wizard.CommandsForSale.commandProcessors.buyCommand;

import org.bukkit.entity.Player;

/**
 * Handels the /deny command
 * @author Ethan
 *
 */
public class DenyCommandExecutor
{
	private static final String META_KEY_CMD = "CommandToBuy";

	public DenyCommandExecutor(){}

	public static ConfirmationResponse perform(Player sender)
	{
		if(MetaUtils.hasMetadata(sender, META_KEY_CMD))
		{
			return ConfirmationResponse.CONFIRM_DENIED;
		}
		//if did not have meta or was somehow false
		return ConfirmationResponse.FAIL_NOT_AWAITING_RESPONSE;
	}
}
