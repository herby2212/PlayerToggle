package de.Herbystar.PlayerToggler.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.Herbystar.PlayerToggler.Main;

public class PlayerQuitEventHandler implements Listener {
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(Main.instance.notwhitelist.contains(p)) {
			Main.instance.notwhitelist.remove(p);
		}
		if(Main.instance.cooldownQueue.containsKey(p.getUniqueId())) {
			Main.instance.cooldownQueue.remove(p.getUniqueId());
		}
	}

}
