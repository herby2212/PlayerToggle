package de.Herbystar.PlayerToggler.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.Herbystar.PlayerToggler.Main;
import de.Herbystar.PlayerToggler.Utilities.ItemHandler;

public class PlayerDropItemEventHandler implements Listener {
	
	Main plugin;
	public PlayerDropItemEventHandler(Main main) {
		plugin = main;
	}
	
	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		
		if(plugin.getConfig().getBoolean("PlayerToggle.DisableItemDrop(OfTheTogglers)") == true && plugin.getConfig().getStringList("PlayerToggle.Worlds").contains(p.getWorld().getName())) {
			if(e.getItemDrop().getItemStack().hasItemMeta()) {
				if(ItemHandler.compareMaterials(e.getItemDrop().getItemStack().getType(), 1)) {
					if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
						e.setCancelled(true);
					}
				}
				if(ItemHandler.compareMaterials(e.getItemDrop().getItemStack().getType(), 2)) {
					if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

}
