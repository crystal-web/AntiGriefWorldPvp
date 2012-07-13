package fr.iyc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiGriefWorldPvp extends JavaPlugin implements Listener
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
			sender.sendMessage("Vous devez êtes un joueur pour faire cela !");
			return false;
		}
		Player player = (Player)sender;
		if(player.isOp()){
			player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande !");
			return false;
		}
		if(label.equalsIgnoreCase("agpvp")&& args.length <=0){
			showHelp(player);
		}
		if(label.equalsIgnoreCase("agpvp") && args.length > 0){
			if(args[0].equalsIgnoreCase("addblock")){
				if(args[2] == null|| args[1] == null){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp addblock <world> <blockID>");
					return false;
				}
				for(int i =2;i<args.length;i++){
					setItemToList(args[1], args[2]);
					player.sendMessage(ChatColor.GREEN + "Le block a bien été rajouté a la liste");
				}
			}
			if(args[0].equalsIgnoreCase("delblock")){
				if(args[2] == null|| args[1] == null){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp delblock <world> <blockID>");
					return false;
				}
				for(int i =2;i<args.length;i++){
					removeItemFromList(args[1], args[2]);
					player.sendMessage(ChatColor.GREEN + "Le block a bien été supprimé de la liste");
				}
			}
			if(args[0].equalsIgnoreCase("cleaninv")){
				if(args[2] == null|| args[1] == null){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp cleaninv <world> <true/false>");
					return false;
				}
				if(!args[2].equalsIgnoreCase("true")&&!args[2].equalsIgnoreCase("false")){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp cleaninv <world> <true/false>");
					return false;
				}
				getConfig().set("config."+args[1]+".cleanInventoryInJoin", args[2]);
				player.sendMessage(ChatColor.GREEN + "La valeur a bien été modifiée");
			}
			if(args[0].equalsIgnoreCase("blacklist")){
				if(args[2] == null || args[1] == null){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp blacklist <world> <true/false>");
					return false;
				}
				if(!args[2].equalsIgnoreCase("true")&&!args[2].equalsIgnoreCase("false")){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp blacklist <world> <true/false>");
					return false;
				}
				if(getConfig().get("config."+args[1]) == null){
					player.sendMessage(ChatColor.RED + "Ce monde n'existe pas !");
					return false;
				}
				getConfig().set("config."+args[1]+".restrictedBlocks", args[2]);
				player.sendMessage(ChatColor.GREEN + "La valeur a bien été modifiée");
			}
			if(args[0].equalsIgnoreCase("help")){
				showHelp(player);
			}
			if(args[0].equalsIgnoreCase("create")){
				if(args[1] == null){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp create <world>");
					return false;
				}
				if(getConfig().get("config."+args[1]) != null){
					player.sendMessage(ChatColor.RED + "Une configuration existe déja pour ce monde !");
					return false;
				}
				Config.addWorldOnConfig(args[1]);
				saveConfig();
				reloadConfig();
				player.sendMessage(ChatColor.GREEN + "La configuration pour le monde "+ args[1]+ " a bien été crée !");
			}
			if(args[0].equalsIgnoreCase("addworld")){
				if(args[1] == null){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp addworld <world>");
					return false;
				}
				if(getConfig().get("config."+args[1]) == null){
					player.sendMessage(ChatColor.RED + "Ce monde n'existe pas !");
					return false;
				}
					setWorldOnList(args[1]);
					player.sendMessage(ChatColor.GREEN + "La configration a bien été activée pour le monde: " + ChatColor.RED + args[1]	);
				
			}
			if(args[0].equalsIgnoreCase("delworld")){
				if(args[1] == null){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp delworld <world>");
					return false;
				}
				if(getConfig().getString("config."+args[1]) == null){
					player.sendMessage(ChatColor.RED + "Ce monde n'existe pas !");
					return false;
				}
					removeWorldFromList(args[1]);
					player.sendMessage(ChatColor.GREEN + "La configration a bien été désactivée pour le monde: " + ChatColor.RED + args[1]	);
			}
			if(args[0].equalsIgnoreCase("reload")){
				reloadConfig();
				player.sendMessage(ChatColor.GREEN + "La configuration a bien été rechargée !");
			}
			if(args[0].equalsIgnoreCase("getblocks")){
				if(args[1] == null){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp getblocks <world>");
					return false;
				}
				if(getConfig().getString("config."+args[1]) == null){
					player.sendMessage(ChatColor.RED + "Ce monde n'existe pas !");
					return false;
				}
				player.sendMessage("Blocks blacklistés pour le monde " + args[1]+ ": " + getConfig().getString("config."+args[1]+".itemBreakable"));
			}
			if(args[0].equalsIgnoreCase("setmessage")){
				if(args[1] == null || args[2] == null){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp setmessage <world> <message>");
					return false;
				}
				if(getConfig().getString("config."+args[1]) == null){
					player.sendMessage(ChatColor.RED + "Ce monde n'existe pas !");
					return false;
				}
				StringBuilder strb = new StringBuilder();
				for(int i = 2; i <args.length; i++){
					strb.append(args[i] + " ");
				}
				getConfig().set("config."+args[1]+".messageUnbreakble", strb.toString());
				player.sendMessage(ChatColor.GREEN + "Le message a bien été modifié !");
				saveConfig();
				reloadConfig();
			}
			if(args[0].equalsIgnoreCase("getmessage")){
				if(args[1] == null){
					player.sendMessage(ChatColor.RED + "Usage: /agpvp getmessage <world>");
					return false;
				}
				if(getConfig().getString("config."+args[1]) == null){
					player.sendMessage(ChatColor.RED + "Ce monde n'existe pas !");
					return false;
				}
				player.sendMessage("Message du monde "+args[1] + ": "+getConfig().getString("config."+args[1]+".messageUnbreakble").replaceAll("(&([a-f0-9]))", "§$2"));
			}
		}
		return true;
	}

	public void showHelp(Player player){
		player.sendMessage(ChatColor.GOLD +" -------------Aide de AntiGriefWorldPVP-------------");
		player.sendMessage("/agpvp addblock <world> <blockID>     - Ajoute un block à la blacklist");
		player.sendMessage("/agpvp delblock <world> <blockID>     -Retire un block de la blacklist");
		player.sendMessage("/agpvp getblocks <world>     -Affiche les blocks blacklisté d'un monde");
		player.sendMessage("/agpvp create <world>     -Crée une configuration pour le monde");
		player.sendMessage("/agpvp addworld <world>     - Active la configuration d'un monde");
		player.sendMessage("/agpvp delworld <world>     -Désactive la configuration d'un monde");
		player.sendMessage("/agpvp cleaninv <world> <true/false>     -Active ou désactive le cleanInventory");
		player.sendMessage("/agpvp blacklist <world> <true/false>     -Active ou désactive la blacklist d'un monde");
		player.sendMessage("/agpvp setmessage <world> <message>     -Change le message lors d'un block blacklisté cassé");
		player.sendMessage("/agpvp getmessage <world>     -Affiche le message lors d'un block blacklisté cassé");
		player.sendMessage("/agpvp reload     -Recharge la configration du plugin");
	}

	public void setItemToList(String world, String block){
		String[] str = (getConfig().getString("config."+world+".itemBreakable").split(","));
		ArrayList<String> array = new ArrayList();
		for(int i =0;i<str.length;i++){
			array.add(str[i]);
		}
		for(int i =0; i<array.size();i++){
			if(array.get(i).trim() == block){
				return;
			}
		}
		String strb = new StringBuilder().append(getConfig().getString("config."+world+".itemBreakable")).append("," + block).toString();
		getConfig().set("config."+world+".itemBreakable", strb);
		this.saveConfig();
		this.reloadConfig();
	}

	public void removeItemFromList(String world, String block){
		String str[] = (getConfig().getString("config."+world+".itemBreakable").split(","));
		ArrayList<String> array = new ArrayList();
		for(int i = 0; i<str.length;i++){
			array.add(str[i]);
		}
		for(int i = 0; i < array.size(); i++){
			String test = array.get(i);
			if(Double.parseDouble(test.trim()) == Double.parseDouble(block.trim())){
				array.remove(i);
			}
		}
		StringBuilder strb = new StringBuilder();
		for(int i =0; i <array.size();i++){
			strb.append(array.get(i) + ",");
		}
		getConfig().set("config."+world+".itemBreakable", strb.toString());
		this.saveConfig();
		this.reloadConfig();
	}

	public void setWorldOnList(String world){
		String[] str = (getConfig().getString("config.worlds").split(","));
		ArrayList<String> array = new ArrayList();
		if(str.length>0){
			for(int i =0;i<str.length;i++){
				array.add(str[i]);
			}

			for(int i =0; i<array.size();i++){
				if(array.get(i).trim() == world){
					return;
				}
			}
		}
		String strb = new StringBuilder().append(getConfig().getString("config.worlds")).append("," + world).toString();
		String worlds = strb.replace(",,", ",");
		getConfig().set("config.worlds", worlds);
		this.saveConfig();
		this.reloadConfig();
	};

	public void removeWorldFromList(String world){
		String str[] = (getConfig().getString("config.worlds").split(","));
		ArrayList<String> array = new ArrayList();
		for(int i = 0; i<str.length;i++){
			array.add(str[i]);
		}
		for(int i = 0; i < array.size(); i++){
			String test = array.get(i);
			if(test.trim().equalsIgnoreCase(world)){
				array.remove(i);
			}
		}
		StringBuilder strb = new StringBuilder();
		for(int i =0; i <array.size();i++){
			strb.append(array.get(i) + ",");
		}
		String str1 = strb.toString();
		if(strb.charAt(0) == ','){
			str1 = str1.replace(str1.charAt(0) + "", "");
		}
		getConfig().set("config.worlds", str1);
		this.saveConfig();
		this.reloadConfig();
	}
	
	public String getColorMessage(String path){
		return getConfig().getString(path).replaceAll("(&([a-f0-9]))", "§$2");
	}
}