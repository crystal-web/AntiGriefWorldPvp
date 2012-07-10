package fr.iyc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class AGPlayerListener implements Listener{

	public AntiGriefWorldPvp plugin;
	public Config config = new Config();

	public AGPlayerListener(AntiGriefWorldPvp pl){
		plugin = pl;
	}

	@EventHandler
	public void joinEvent(PlayerJoinEvent event)
	{
		if(config.getCleanInventoryOnJoin(event.getPlayer().getWorld().getName())){
			event.getPlayer().getInventory().setArmorContents(null);
			event.getPlayer().getInventory().clear();
		} 
	}
}
