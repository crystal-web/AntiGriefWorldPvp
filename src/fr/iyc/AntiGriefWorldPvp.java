package fr.iyc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class AntiGriefWorldPvp  extends JavaPlugin implements Listener
{
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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("be_player");
			return false;
		}
		Player player = (Player)sender;
		if(!player.isOp()){
			player.sendMessage(ChatColor.RED + "Vous n'avez pasla permission d'utiliser cette commande !");
			return false;
		}
		if(label.equalsIgnoreCase("agpvp") && args.length > 0){
			if(args[0].equalsIgnoreCase("add")){
				for(int i =2;i<args.length;i++){
					setItemToList(args[1], args[2]);
					player.sendMessage(ChatColor.GREEN + "Le block a bien été rajouté a la liste");
				}
			}
			if(args[0].equalsIgnoreCase("remove")){
				for(int i =2;i<args.length;i++){
					removeItemFromList(args[1], args[2]);
					player.sendMessage(ChatColor.GREEN + "Le block a bien été supprimé de la liste");
				}
			}
			if(args[0].equalsIgnoreCase("clean")){
				if(!args[2].equalsIgnoreCase("true")&&!args[1].equalsIgnoreCase("false")){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp clean true/false");
					return false;
				}
				getConfig().set("config."+args[1]+".cleanInventoryInJoin", args[2]);
				player.sendMessage(ChatColor.GREEN + "La valeur a bien été modifiée");
			}
			if(args[0].equalsIgnoreCase("blacklist")){
				if(!args[2].equalsIgnoreCase("true")&&!args[1].equalsIgnoreCase("false")){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp limit true/false");
					return false;
				}
				getConfig().set("config."+args[1]+".restrictedBlocks", args[2]);
				player.sendMessage(ChatColor.GREEN + "La valeur a bien été modifiée");
			}
			if(args[0].equalsIgnoreCase("help")){
				player.sendMessage(ChatColor.GOLD +" -------------Aide de AntiGriefWorldPVP-------------");
				player.sendMessage(ChatColor.ITALIC + "/agpvp add <world> <blockID>     - Ajoute un block à la blacklist");
				player.sendMessage(ChatColor.ITALIC + "/agpvp remove <world> <blockID>     -Retire un block de la blacklist");
				player.sendMessage(ChatColor.ITALIC + "/agpvp clean <world> <true/false>     -Active ou désactive le cleanInventory");
				player.sendMessage(ChatColor.ITALIC + "/agpvp blacklist <world> <true/false>     -Active ou désactive la blacklist d'un monde");
			}
		}
		return true;
	}
	
	public void setItemToList(String world, String block){
		String str = (getConfig().getString("config."+world+".itemBreakable"));
		System.out.println(str);
		if(str.contains(","+block+","))
		{
			return;
		}
		String str2 = str + ","+ block;
		getConfig().set("config."+world+".itemBreakable", str2);
		this.saveConfig();
		this.reloadConfig();
	}
	
	public void removeItemFromList(String world, String block){
		String str = (getConfig().getString("config."+world+".itemBreakable") + ",");
		String str1 = str.replace(","+block+",", ",");
		String str2 = str1.replace(",,", ",");
		getConfig().set("config."+world+".itemBreakable", str2);
		this.saveConfig();
		this.reloadConfig();
	}
}