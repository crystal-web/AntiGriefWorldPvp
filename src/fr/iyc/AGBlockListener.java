package fr.iyc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class AGBlockListener implements Listener{
	
public AntiGriefWorldPvp plugin;
	
	public AGBlockListener(AntiGriefWorldPvp pl){
		plugin = pl;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		 String block = String.valueOf(event.getBlock().getTypeId());
	     Player player = event.getPlayer();
	     String world = event.getPlayer().getWorld().getName();
	     
	     // Le joueur est dans le monde concerné
	     if (world.equalsIgnoreCase(Config.getWorld()))
	     {
	    	 plugin.getLogger().info("World is " + Config.getWorld());
	    	 // Se n'est pas l'operateur
	    	 if (!event.getPlayer().isOp())
	    	 {
	    		 if (event.getPlayer().getGameMode() == org.bukkit.GameMode.CREATIVE)
	    		 {
	    			 player.sendMessage("[AntigriefWorldPvp] Je force le mode survival");
	    			 event.getPlayer().setGameMode(org.bukkit.GameMode.SURVIVAL);
	    		 }
	    		 
	    		 String item[] = Config.getItemBreakable();
	    		 
    		 	//for (int i = 0; i<item.length; i++)
    		 	for(String str : item)
    			{
    		 		plugin.getLogger().info(Config.getItemBreakable().toString());
    		 		if (str.trim() == block )
    		 		{
    		 			plugin.getLogger().info("Bloque desctructible");
    		 			return;
    		 		}
    		 	}
    		 	
    		 	plugin.getLogger().info(Config.getItemBreakable().toString());
    		 	
	 			player.sendMessage(Config.getMessageUnbreakble());
    		 	event.isCancelled();
	    	 }

	    //String m = "Block id:." + b;
	   //  plugin.getLogger().info(conf.getItemBreakable().toString());
	  //   e.getBlock().setType(Material.AIR);
	     }
	     else
	     {
	    	 plugin.getLogger().info("A no World is " + Config.getWorld());
	     }
	}

}
