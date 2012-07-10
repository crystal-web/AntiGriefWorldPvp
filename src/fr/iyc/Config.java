package fr.iyc;

import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Config
{
	public static AntiGriefWorldPvp plugin;


	public void load(AntiGriefWorldPvp plugin)
	{
		this.plugin = plugin;
		FileConfiguration config = plugin.getConfig();

		if (config.get("config") == null)
		{
			config.set("config.worlds", "world");
			String defaultWord = "world";
			config.set("config.world", defaultWord);
			
			config.set("config.world.restrictedBlocks", "true");
			String defaultItem = "4,12,13,17,18,20,31,32,35";
			config.set("config.world.itemBreakable", defaultItem);

			String defaultMessageUnbreakble = "Mmmh sa me semble dur à casser";
			config.set("config.world.messageUnbreakble", defaultMessageUnbreakble);

			String cleanInventory = "true";
			config.set("config.world.cleanInventoryInJoin", cleanInventory);

			plugin.saveConfig();
		}
	}
	
	public static boolean worldIsConfig(Player player){
		String[] worlds = plugin.getConfig().getString("config.worlds").split(",");
		for(int i = 0; i <worlds.length; i++){
			if(player.getWorld().getName().equalsIgnoreCase(worlds[i].trim())){
				return true;
			}
		}
		return false;
	}

	public boolean getCleanInventoryOnJoin(String world)
	{
		if (plugin.getConfig().getString("config."+world+".cleanInventoryInJoin").equalsIgnoreCase("true"))
		{
			return true;
		}

		return false;
	}
	
	public static boolean getRestrictionBlock(String world)
	{
		if (plugin.getConfig().getString("config."+world+".restrictedBlocks").equalsIgnoreCase("true"))
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