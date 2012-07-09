package fr.iyc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class AntiGriefWorldPvp  extends JavaPlugin implements Listener
{

	 private boolean kickall = false;
	 private Config conf;
	  
	  public void onDisable()
	  {
		  getLogger().info("Désactivation de AntiGriefWorldPvp 1.1");
	  }

	  public void onEnable() {
	   
	    getServer().getPluginManager().registerEvents(this, this);
	    
	    getLogger().info("Activation de AntiGriefWorldPvp");
	    this.conf = new Config();
	    this.conf.load(this);
	    getLogger().info("Chargement de la configuration");	    
	    
	    
	    /*
	    this.conf = new Config();
	    this.conf.load(this);
	    getLogger().info("Chargement de la configuration");

	    this.sql = new MySQL(getLogger(), "", this.conf.getHost(), this.conf.getPort(), this.conf.getDbname(), this.conf.getDbuser(), this.conf.getDbpass());
	    this.sql.open();
		
	    
	    getLogger().info("Test de connexion SQL");
		    if (this.sql.checkConnection()) {
		      getLogger().info("Connexion accepté");
		      getServer().getPluginManager().registerEvents(this, this);
		    }
		    else
		    {
		        getServer().getPluginManager().registerEvents(this, this);
		    	getLogger().info("Connexion impossible");
		    	getLogger().info("Tous les joueurs seront kické");
		    	this.kickall = true;
		    }
	 	*/
	    
	  }
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak (BlockBreakEvent event)
	{
		getLogger().info("event load");
		String block = String.valueOf(event.getBlock().getTypeId());

	     Player player = event.getPlayer();
	     
	     String world = event.getPlayer().getWorld().getName();
	     
	     // Le joueur est dans le monde concerné
	     if (world.equalsIgnoreCase(Config.getWorld()))
	     {
	    	 getLogger().info("World is " + Config.getWorld());
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
    		 		getLogger().info(Config.getItemBreakable().toString());
    		 		if (str.trim() == block )
    		 		{
    		 			getLogger().info("return");
    		 			return;
    		 		}
    		 	}
    		 	
    		 	getLogger().info("no item in stack");
    		 	getLogger().info(Config.getItemBreakable().toString());
    		 	
	 			player.sendMessage(Config.getMessageUnbreakble());
    		 	event.isCancelled();
	    	 }
	    	 else
	    	 {
	    		 
	    		 getLogger().info("has op");
	    	 }
	     //
	    //String m = "Block id:." + b;
	   //  getLogger().info(conf.getItemBreakable().toString());
	  //   e.getBlock().setType(Material.AIR);
	     }
	     else
	     {
	    	 getLogger().info("A no World is " + Config.getWorld());
	     }
	}
	
	public void PlayerJoinEvent(Player playerJoined)
	{
		playerJoined.getPlayer().getInventory().clear();
	}
	
	
}
