package fr.iyc;

import java.util.List;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Config
{
	public static AntiGriefWorldPvp plugin;
	public static FileConfiguration config;

	public void load(AntiGriefWorldPvp plugin)
	{
		this.plugin = plugin;
		config = plugin.getConfig();
		List<World> str = plugin.getServer().getWorlds();
		if (config.get("config") == null)
		{
			config.set("config.worlds", "world");
			addWorldOnConfig("world");
			for(int i = 0; i<str.size(); i++){
				if(config.get("config."+ str.get(i).getName()) == null){
					String world = str.get(i).getName();
					addWorldOnConfig(world);
				}
			}
			plugin.saveConfig();
		}
	}
	
	public static void addWorldOnConfig(String world){
		config.set("config."+world+".restrictedBlocks", "true");

		config.set("config."+world+".itemBreakable", "4,12,13,17,18,20,31,32,35");

		config.set("config."+world+".messageUnbreakble", "Mmmh sa me semble dur à casser");

		String cleanInventory = "true";
		config.set("config."+world+".cleanInventoryInJoin", "true");
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

	public static String getMessageUnbreakble(String world) {
		return plugin.getColorMessage("config."+world+".messageUnbreakble");
	}
}