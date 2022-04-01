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
				
//				ItemStack PS3 = new ItemStack(Material.YELLOW_DYE, 1);
////			    ItemStack PS3 = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.DataValue"));
//			    ItemMeta PS3Meta = PS3.getItemMeta();
//			    PS3Meta.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//			    PS3Meta.setDisplayName(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//			    PS3.setItemMeta(PS3Meta);
//			    if(p.getInventory().contains(PS3)) {
//			      p.getInventory().remove(PS3);
//			      p.updateInventory();
//			    }
			    
			    ItemHandler.createToggleItem(2, p, true, true);
			    
//			    ItemStack PS2 = new ItemStack(Material.LIME_DYE, 1);
////			    ItemStack PS2 = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.DataValue"));
//			    ItemMeta PS2Meta = PS2.getItemMeta();
//			    PS2Meta.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//			    PS2Meta.setDisplayName(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//			    PS2.setItemMeta(PS2Meta);
//			    p.getInventory().setItem(plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.Slot(0-8)"), PS2);
//			    if(p.getInventory().contains(PS2)) {
//			      p.getInventory().remove(PS2);
//			      p.updateInventory();
//			    }		    
			} else {
			    if(plugin.hidden.contains(p.getName())) {
			    	ItemHandler.createToggleItem(1, p, true, false);
			    	
//			    	ItemStack PS1 = new ItemStack(Material.YELLOW_DYE, 1);
////			        ItemStack PS1 = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.DataValue"));
//			        ItemMeta PS1Meta = PS1.getItemMeta();
//			        PS1Meta.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//			        PS1Meta.setDisplayName(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//			        PS1.setItemMeta(PS1Meta);
//			        p.getInventory().setItem(plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.Slot(0-8)"), PS1);
			    } else {
			    	ItemHandler.createToggleItem(2, p, true, false);
			    	
//			    	ItemStack PS = new ItemStack(Material.LIME_DYE, 1);
////			        ItemStack PS = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.DataValue"));
//			        ItemMeta PSMeta = PS.getItemMeta();
//			        PSMeta.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//			        PSMeta.setDisplayName(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//			        PS.setItemMeta(PSMeta);
//			        p.getInventory().setItem(plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.Slot(0-8)"), PS);
			    }
			}
		}
	}

}
