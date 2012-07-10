package fr.iyc;

import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
	private static String itemBreakable;
	private static String messageUnbreakble;
	private static String world;
	private String cleanInventoryInJoin;


	public void load(AntiGriefWorldPvp plugin)
	{
		FileConfiguration config = plugin.getConfig();

		if (config.get("config") == null)
		{
			String defaultItem = "4,12,13,17,18,20,31,32,35";
			config.set("config.itemBreakable", defaultItem);

			String defaultMessageUnbreakble = "Mmmh sa me semble du dur à casser";
			config.set("config.messageUnbreakble", defaultMessageUnbreakble);

			String defaultWord = "world";
			config.set("config.world", defaultWord);

			String cleanInventory = "true";
			config.set("config.cleanInventoryInJoin", cleanInventory);

			this.setItemBreakable(defaultItem);
			this.setMessageUnbreakble(defaultMessageUnbreakble);

			plugin.saveConfig();
		}


		this.itemBreakable = config.getString("config.itemBreakable");
		this.messageUnbreakble = config.getString("config.messageUnbreakble");
		this.world = config.getString("config.world");
		this.cleanInventoryInJoin = config.getString("config.cleanInventoryInJoin");
	}


	public boolean getCleanInventoryOnJoin()
	{
		if (cleanInventoryInJoin.equalsIgnoreCase("true") )
		{
			return true;
		}

		return false;
	}

	public static String getWorld()
	{
		return world;
	}

	public static String[] getItemBreakable() {
		String[] str=itemBreakable.split(",");
		return str;
	}



	public void setItemBreakable(String itemBreakable) {
		this.itemBreakable = itemBreakable;
	}



	public static String getMessageUnbreakble() {
		return messageUnbreakble;
	}



	public void setMessageUnbreakble(String messageUnbreakble) {
		this.messageUnbreakble = messageUnbreakble;
	}
}