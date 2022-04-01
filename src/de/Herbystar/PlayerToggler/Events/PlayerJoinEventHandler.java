package de.Herbystar.PlayerToggler.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import de.Herbystar.PlayerToggler.Main;
import de.Herbystar.PlayerToggler.Utilities.ItemHandler;

public class PlayerJoinEventHandler implements Listener {
	
	Main plugin;
	public PlayerJoinEventHandler(Main main) {
		plugin = main;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(p.isOp() && plugin.UpdateAviable == true) {
			if(plugin.getConfig().getBoolean("PlayerToggle.AutoUpdater") == true) {
	    	    p.sendMessage("§4[§aPlayerToggle§4] §a-=> Update is available! <=-");
	    	    p.sendMessage("§aDownload: §ehttp://herbystar.eu/resources/ultimate-playertoggle.17/");
			}
		}
		plugin.notwhitelist.add(p);
//		if(!p.hasPermission("PlayerToggle.WhiteList")) {
//			plugin.notwhitelist.add(p);
//		}
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
