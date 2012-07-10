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

	@Override
	public void onDisable()
	{
		getLogger().info("Désactivation de AntiGriefWorldPvp 1.1");
	}

	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(blockListener, this);
		getServer().getPluginManager().registerEvents(playerListener, this);
		getLogger().info("Activation de AntiGriefWorldPvp");
		this.conf = new Config();
		this.conf.load(this);
		getLogger().info("Chargement de la configuration");
	}	
}