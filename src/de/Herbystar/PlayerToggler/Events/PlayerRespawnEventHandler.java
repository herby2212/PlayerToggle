package de.Herbystar.PlayerToggler.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import de.Herbystar.PlayerToggler.Main;
import de.Herbystar.PlayerToggler.Utilities.ItemHandler;

public class PlayerRespawnEventHandler implements Listener {
	
	Main plugin;
	public PlayerRespawnEventHandler(Main main) {
		plugin = main;
	}
	
	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		
		if(plugin.getConfig().getBoolean("PlayerToggle.ItemOnJoin") == true) {
			if(!plugin.getConfig().getStringList("PlayerToggle.Worlds").contains(p.getWorld().getName())) {
				ItemHandler.createToggleItem(1, p, false, true);			    
			    ItemHandler.createToggleItem(2, p, true, true);
			} else {
			    if(plugin.hidden.contains(p.getName())) {
			    	ItemHandler.createToggleItem(1, p, true, false);
			    } else {
			    	ItemHandler.createToggleItem(2, p, true, false);
			    }
			}
		}
	}

}
