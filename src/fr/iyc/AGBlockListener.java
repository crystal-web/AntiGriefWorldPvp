package fr.iyc;

import org.bukkit.GameMode;
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

		if(Config.worldIsConfig(player) && Config.getRestrictionBlock(world)){
			// Le joueur est dans le monde concerné
			if (world.equalsIgnoreCase(Config.getWorld(player.getName())))
			{

				// Se n'est pas l'operateur
				if (!event.getPlayer().isOp())
				{
					if (player.getGameMode() == GameMode.CREATIVE)
					{
						player.sendMessage("[AntigriefWorldPvp] Je force le mode survival");
						event.getPlayer().setGameMode(GameMode.SURVIVAL);
					}

					String item[] = Config.getItemBreakable(world);

					for (int i = 0; i<item.length; i++)
					{
						if (item[i].trim().equalsIgnoreCase(block) )
						{
							plugin.getLogger().info("Bloque desctructible");
							return;
						}
					}

					plugin.getLogger().info("Bloque indesctructible " + event.getBlock().getTypeId());


					player.sendMessage(Config.getMessageUnbreakble(world));
					event.setCancelled(true);
				}
			}
		}
	}

}
