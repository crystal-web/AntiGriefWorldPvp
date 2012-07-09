package fr.iyc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class AGPlayerListener implements Listener{
	
	public AntiGriefWorldPvp plugin;
	
	public AGPlayerListener(AntiGriefWorldPvp pl){
		plugin = pl;
	}
	
	@EventHandler
	public void joinEvent(Player event)
	{
		event.getPlayer().getInventory().clear();
	}

}
