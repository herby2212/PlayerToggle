package de.Herbystar.PlayerToggler.Events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import b.TTA_Sounds;
import de.Herbystar.PlayerToggler.Main;
import de.Herbystar.PlayerToggler.Utilities.ItemHandler;
import de.Herbystar.TTA.TTA_Methods;

public class InventoryClickEventHandler implements Listener {
	
	Main plugin;
	public InventoryClickEventHandler(Main main) {
		plugin = main;
	}
	
	private String getInventoryName(InventoryClickEvent e) {
		try {
			return e.getView().getTitle();
		} catch(Exception ex) {
			return e.getInventory().getName();
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		
		int fadeint = plugin.getConfig().getInt("PlayerToggle.Titles.FadeIn");
		int stayt = plugin.getConfig().getInt("PlayerToggle.Titles.Stay");
		int fadeoutt = plugin.getConfig().getInt("PlayerToggle.Titles.FadeOut");
		
		Player p = (Player) e.getWhoClicked();
		if(plugin.getConfig().getBoolean("PlayerToggle.DisableItemMove(OfTheTogglers)") && plugin.getConfig().getStringList("PlayerToggle.Worlds").contains(p.getWorld().getName())) {
			if(e.getCurrentItem() != null) {
				if(ItemHandler.compareMaterials(e.getCurrentItem().getType(), 1)) {
//				if(typeId == plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.ID", (byte) plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.DataValue"))) {
					if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
						e.setCancelled(true);
					}
				}
				if(ItemHandler.compareMaterials(e.getCurrentItem().getType(), 2)) {
//				if(typeId == plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.ID", (byte) plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.DataValue"))) {
					if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
						e.setCancelled(true);
					}
				}
			}
		}
	    if(this.getInventoryName(e).equals(plugin.getConfig().getString("PlayerToggle.Inventory.InventoryTitle").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
	    	e.setCancelled(true);
	    	if(e.getCurrentItem() != null) {
	    		if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Inventory.Item1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
	    			if(!plugin.I1IIAS.contains(p.getName())) {
	    				for(Player players : plugin.notwhitelist) {
							try {
								p.hidePlayer(plugin, players);
							} catch(NoSuchMethodError ex) {
								p.hidePlayer(players);
							}
	    				}
	    				plugin.hidden.add(p.getName());
	    				plugin.I1IIAS.add(p.getName());
	    				plugin.I2IIAS.remove(p.getName());
	    				plugin.I3IIAS.remove(p.getName());
	    				
	    		        p.playEffect(p.getLocation(), Effect.valueOf(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.EffectOnToggle").toUpperCase()), 4);
	    		        try {
	    		        	p.playSound(p.getLocation(), TTA_Sounds.GetSoundByName(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.SoundsOnToggle").toUpperCase()), 10, 1);
	    		        } catch(Exception ex) {
	    		        	Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cError while playing Sounds effect! \n Name of the Sounds need to be replaced to match the current MC version!");
	    		        }    		        
	    		        
	    		        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
	    		        
						String t = plugin.getConfig().getString("PlayerToggle.Titles.Title.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
						String st = plugin.getConfig().getString("PlayerToggle.Titles.Subtitle.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
						String ab = plugin.getConfig().getString("PlayerToggle.ActionBar.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
						
						if(plugin.getConfig().getBoolean("PlayerToggle.ActionBar.Enabled") == true) {
							TTA_Methods.sendActionBar(p, ab);
						}
						if(plugin.getConfig().getBoolean("PlayerToggle.Titles.Enabled") == true) {
							TTA_Methods.sendTitle(p, t, fadeint, stayt, fadeoutt, st, fadeint, stayt, fadeoutt);
						}
						p.closeInventory();
	    			} else {
	    		        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.AlreadyVisible/HiddenItemUse.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
	    			}
	    		}
	    		
	    		if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Inventory.Item2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
	    			if(!plugin.I2IIAS.contains(p.getName())) {
	    				for(Player players : Bukkit.getOnlinePlayers()) {
							try {
								p.showPlayer(plugin, players);
							} catch(NoSuchMethodError ex) {
								p.showPlayer(players);
							}
							List<String> friends = plugin.getConfig().getStringList("PlayerToggle.Friends." + p.getUniqueId());
							if(!friends.contains(players.getUniqueId().toString())) {
								plugin.nofriends.add(players);
							}
	    				}
	    				for(Player nofriends : plugin.nofriends) {
							try {
								p.hidePlayer(plugin, nofriends);
							} catch(NoSuchMethodError ex) {
								p.hidePlayer(nofriends);
							}
	    				}
	    				plugin.nofriends.clear();
	    		        p.playEffect(p.getLocation(), Effect.valueOf(plugin.getConfig().getString("PlayerToggle.Inventory.Item2.EffectOnToggle").toUpperCase()), 4);
	    		        try {
	    		        	p.playSound(p.getLocation(), TTA_Sounds.GetSoundByName(plugin.getConfig().getString("PlayerToggle.Inventory.Item2.SoundsOnToggle").toUpperCase()), 10, 1);
	    		        } catch(Exception ex) {
	    		        	Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cError while playing Sounds effect! \n Name of the Sounds need to be replaced to match the current MC version!");
	    		        }    		        

	    				plugin.I1IIAS.remove(p.getName());
	    				plugin.I2IIAS.add(p.getName());
	    				plugin.I3IIAS.remove(p.getName());
	    		        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Friends").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
	    		        
						String t = plugin.getConfig().getString("PlayerToggle.Titles.Title.Friends").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
						String st = plugin.getConfig().getString("PlayerToggle.Titles.Subtitle.Friends").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
						String ab = plugin.getConfig().getString("PlayerToggle.ActionBar.Friends").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
						
						if(plugin.getConfig().getBoolean("PlayerToggle.ActionBar.Enabled") == true) {
							TTA_Methods.sendActionBar(p, ab);
						}
						if(plugin.getConfig().getBoolean("PlayerToggle.Titles.Enabled") == true) {
							TTA_Methods.sendTitle(p, t, fadeint, stayt, fadeoutt, st, fadeint, stayt, fadeoutt);
						}
						p.closeInventory();
	    			} else {
	    		        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.AlreadyVisible/HiddenItemUse.Friends").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
	    			}
	    		}
	    		
	    		if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Inventory.Item3.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
	    			if(!plugin.I3IIAS.contains(p.getName())) {
	    				for(Player players : plugin.notwhitelist) {
							try {
								p.showPlayer(plugin, players);
							} catch(NoSuchMethodError ex) {
								p.showPlayer(players);
							}
	    				}
	    				plugin.hidden.add(p.getName());
	    				plugin.I3IIAS.add(p.getName());
	    				plugin.I1IIAS.remove(p.getName());
	    				plugin.I2IIAS.remove(p.getName());
	    				
	    		        p.playEffect(p.getLocation(), Effect.valueOf(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.EffectOnToggle").toUpperCase()), 4);
	    		        try {
	    		        	p.playSound(p.getLocation(), TTA_Sounds.GetSoundByName(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.SoundsOnToggle").toUpperCase()), 10, 1);
	    		        } catch(Exception ex) {
	    		        	Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cError while playing Sounds effect! \n Name of the Sounds need to be replaced to match the current MC version!");
	    		        }    		        
	    		        
	    		        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
	    		        
						String t = plugin.getConfig().getString("PlayerToggle.Titles.Title.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
						String st = plugin.getConfig().getString("PlayerToggle.Titles.Subtitle.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
						String ab = plugin.getConfig().getString("PlayerToggle.ActionBar.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
						
						if(plugin.getConfig().getBoolean("PlayerToggle.ActionBar.Enabled") == true) {
							TTA_Methods.sendActionBar(p, ab);
						}
						if(plugin.getConfig().getBoolean("PlayerToggle.Titles.Enabled") == true) {
							TTA_Methods.sendTitle(p, t, fadeint, stayt, fadeoutt, st, fadeint, stayt, fadeoutt);
						}
						p.closeInventory();
	    			} else {
	    		        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.AlreadyVisible/HiddenItemUse.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
	    			}
	    		}
	    	}
	    }
	}

}
