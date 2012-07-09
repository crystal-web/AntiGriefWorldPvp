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
	 public AGPlayerListener playerListener = new AGPlayerListener(this);
	 public AGBlockListener blockListener = new AGBlockListener(this);
	 
	  public void onDisable()
	  {
		  getLogger().info("Désactivation de AntiGriefWorldPvp 1.1");
	  }

	  public void onEnable() {
	   
		  //As tu re�u mon trool ?
		  //test
		  
	    getServer().getPluginManager().registerEvents(blockListener, this);
	    getServer().getPluginManager().registerEvents(playerListener, this);
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
}
