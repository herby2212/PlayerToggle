package de.Herbystar.PlayerToggler.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import de.Herbystar.PlayerToggler.Main;
import de.Herbystar.PlayerToggler.Utilities.ItemHandler;

public class PlayerChangeWorldEventHandler implements Listener {
	
	Main plugin;
	public PlayerChangeWorldEventHandler(Main main) {
		plugin = main;
	}
	
	@EventHandler
	public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		
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
