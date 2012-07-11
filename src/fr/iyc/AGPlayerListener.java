package fr.iyc;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AGPlayerListener implements Listener{

	public AntiGriefWorldPvp plugin;
	public Config config = new Config();

	public AGPlayerListener(AntiGriefWorldPvp pl){
		plugin = pl;
	}

	@EventHandler
	public void joinEvent(PlayerJoinEvent event)
	{
		if(config.getCleanInventoryOnJoin(event.getPlayer().getWorld().getName()) && config.worldIsConfig(event.getPlayer())){
			event.getPlayer().getInventory().setArmorContents(null);
			event.getPlayer().getInventory().clear();
		}
	}
	
	@EventHandler
	public void moveEvent(PlayerMoveEvent event){
		Player player = event.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE && !player.isOp()){
			player.sendMessage("[AntigriefWorldPvp] Je force le mode survival");
			player.setGameMode(GameMode.SURVIVAL);
		}
	}
	
	@EventHandler
	public void gameModeChange(PlayerGameModeChangeEvent event){
		Player player = event.getPlayer();
		if(!player.isOp() && player.getGameMode() == GameMode.SURVIVAL){
			event.setCancelled(true);
		}
	}
}
