package fr.iyc;

import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
	public static AntiGriefWorldPvp plugin;
	/*private static String itemBreakable;
	private static String messageUnbreakble;
	private static String world;
	private String cleanInventoryInJoin;*/


	public void load(AntiGriefWorldPvp plugin)
	{
		this.plugin = plugin;
		FileConfiguration config = plugin.getConfig();

		if (config.get("config") == null)
		{
			String defaultWord = "world";
			config.set("config.world", defaultWord);
			
			String defaultItem = "4,12,13,17,18,20,31,32,35";
			config.set("config.world.itemBreakable", defaultItem);

			String defaultMessageUnbreakble = "Mmmh sa me semble dur à casser";
			config.set("config.world.messageUnbreakble", defaultMessageUnbreakble);

			String cleanInventory = "true";
			config.set("config.world.cleanInventoryInJoin", cleanInventory);

			/*this.setItemBreakable(defaultItem);
			this.setMessageUnbreakble(defaultMessageUnbreakble);*/

			plugin.saveConfig();
		}
	}


	public boolean getCleanInventoryOnJoin(String world)
	{
		if (plugin.getConfig().getString("config."+world+".itemBreakable").equalsIgnoreCase("true"))
		{
			return true;
		}

		return false;
	}

	public static String getWorld(String player)
	{
		return plugin.getServer().getPlayer(player).getWorld().getName();
	}

	public static String[] getItemBreakable(String world) {
		String[] str= plugin.getConfig().getString("config."+world+".itemBreakable").split(",");
		return str;
	}



	/*public void setItemBreakable(String itemBreakable, String world) {
		this.itemBreakable = itemBreakable;
	}*/



	public static String getMessageUnbreakble(String world) {
		return plugin.getConfig().getString("config."+world+".messageUnbreakble");
	}

	/*public void setMessageUnbreakble(String messageUnbreakble, String world) {
		this.messageUnbreakble = messageUnbreakble;
	}*/
}