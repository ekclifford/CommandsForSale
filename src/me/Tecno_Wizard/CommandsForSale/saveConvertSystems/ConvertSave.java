package me.Tecno_Wizard.CommandsForSale.saveConvertSystems;

import com.skionz.dataapi.DataFile;
import com.skionz.dataapi.DataUtils;
import com.skionz.dataapi.ListFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import me.Tecno_Wizard.CommandsForSale.core.Main;
import me.Tecno_Wizard.CommandsForSale.saveSystems.Config;
import me.Tecno_Wizard.CommandsForSale.saveSystems.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ConvertSave
{
	public static void attemptConvert(Main main)
	{
		if (!DataUtils.fileExists("plugins" + File.separator + "CommandsForSale" + File.separator + "save", "yml")) {
			return;
		}
		Logger log = Bukkit.getLogger();

		ConfigManager sManager = new ConfigManager(main);
		Config save = sManager.getNewConfig("save.yml");

		int numberOfFailures = 0;
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[CommandForSale] Converting old save file to new structure. Please be patient, as this may freeze your server.");

		DataFile mainSave = new DataFile("plugins" + File.separator + "CommandsForSale" + File.separator + "MainSave", "txt");
		mainSave.set("NumberOfTimesRan", Integer.valueOf(save.getInt("NumberOfTimesRan")));
		mainSave.save();


		Set<String> keys = save.getKeys();
		keys.remove("HasRunBefore");
		keys.remove("NumberOfTimesRan");
		for (String key : keys) {
			if (!key.startsWith("CommandsForSale")) {
				try
				{
					ArrayList<String> toWrite = new ArrayList();
					UUID id = UUID.fromString(key);
					ListFile playerSave = new ListFile("plugins" + File.separator + "CommandsForSale" + File.separator + "Players" + File.separator + id.toString(), "txt");
					ArrayList<String> oldBoughtCmds = (ArrayList)save.getStringList(id.toString());
					for (String string : oldBoughtCmds) {
						toWrite.add(string);
					}
					for (String string : playerSave.read()) {
						if (!oldBoughtCmds.contains(string)) {
							toWrite.add(string);
						}
					}
					playerSave.write(oldBoughtCmds);
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
					numberOfFailures++;
					log.warning("[CommandsForSale] An error occurred moving files. You may need to speak with players who feel as if they lost their commands. If you see this a lot of times, well, you've got some issues on your hands. The save file will not be deleted if this error occurs 3 times or more.  Report this on the plugin site and also post the save file");
				}
			}
		}
		if (numberOfFailures < 3)
		{
			File oldSave = new File("Plugins" + File.separator + "CommandsForSale" + File.separator + "save.yml");
			oldSave.delete();
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[CommandsForSale] Done! " + numberOfFailures + " errors occured.");
		}
		else
		{
			log.info(String.format("%s[CommandsForSale] Due to the amount of errors thrown, the save file was not deleted. Send your save file to Tecno_Wizard on BukkitDev to be inspected.%nMany file transfers failed.", new Object[] {
					ChatColor.RED }));
		}
	}
}
