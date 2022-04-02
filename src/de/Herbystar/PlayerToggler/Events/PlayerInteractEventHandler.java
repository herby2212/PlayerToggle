package de.Herbystar.PlayerToggler.Events;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.Herbystar.PlayerToggler.Main;
import de.Herbystar.PlayerToggler.Utilities.ItemHandler;
import de.Herbystar.TTA.TTA_Methods;
import de.Herbystar.TTA.Sound.TTA_Sounds;

public class PlayerInteractEventHandler implements Listener {
	
	Main plugin;
	public PlayerInteractEventHandler(Main main) {
		plugin = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		
		int fadeint = plugin.getConfig().getInt("PlayerToggle.Titles.FadeIn");
		int stayt = plugin.getConfig().getInt("PlayerToggle.Titles.Stay");
		int fadeoutt = plugin.getConfig().getInt("PlayerToggle.Titles.FadeOut");
		
		final Player p = e.getPlayer();
		if(plugin.getConfig().getStringList("PlayerToggle.Worlds").contains(p.getWorld().getName())) {
			if(plugin.getConfig().getBoolean("PlayerToggle.InventoryMode") == false) {
				if ((((e.getAction() == Action.RIGHT_CLICK_AIR ? 1 : 0) | (e.getAction() == Action.RIGHT_CLICK_BLOCK ? 1 : 0)) != 0) && (e.getItem() != null)) {
					if(ItemHandler.compareMaterials(e.getItem().getType(), 2)) {
						if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
							e.setCancelled(true);
							if(!Main.instance.cooldownQueue.containsKey(p.getUniqueId()) || p.hasPermission("PlayerToggle.CDBP")) {
								if(!plugin.alreadyhidden.contains(p.getName())) {
									for(Player players : plugin.notwhitelist) {
										try {
											p.hidePlayer(plugin, players);
										} catch(NoSuchMethodError ex) {
											p.hidePlayer(players);
										}
									}
									plugin.hidden.add(p.getName());
									plugin.alreadyhidden.add(p.getName());
									plugin.alreadyvisible.remove(p.getName());
									
									if(!p.hasPermission("PlayerToggle.CDBP")) {
										Main.instance.cooldownQueue.put(p.getUniqueId(), Main.instance.cd);
									}
									ItemHandler.createToggleItem(1, p, true, false);								
						            ItemHandler.createToggleItem(2, p, false, true);
					            
						            p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
						            p.playEffect(p.getLocation(), Effect.valueOf(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.EffectOnToggle").toUpperCase()), 4);
						            try {
							            p.playSound(p.getLocation(), TTA_Sounds.GetSoundByName(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.SoundOnToggle").toUpperCase()), 10, 1);
						            } catch(Exception ex) {
				    		        	Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cError while playing sound effect! \n Name of the sound need to be replaced to match the current MC version!");
						            }

						            
									String t = plugin.getConfig().getString("PlayerToggle.Titles.Title.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
									String st = plugin.getConfig().getString("PlayerToggle.Titles.Subtitle.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
									String ab = plugin.getConfig().getString("PlayerToggle.ActionBar.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
									
									if(plugin.getConfig().getBoolean("PlayerToggle.ActionBar.Enabled") == true) {
										TTA_Methods.sendActionBar(p, ab);
									}
									if(plugin.getConfig().getBoolean("PlayerToggle.Titles.Enabled") == true) {
										TTA_Methods.sendTitle(p, t, fadeint, stayt, fadeoutt, st, fadeint, stayt, fadeoutt);
									}
								} else {
						            p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.AlreadyVisible/HiddenItemUse.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
								}
							} else {
								p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.Cooldown.CooldownMessage").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä").replace("[T]", new StringBuilder(String.valueOf(Main.instance.cooldownQueue.get(p.getUniqueId()))).toString()));
							}
						}
					}
					if(ItemHandler.compareMaterials(e.getItem().getType(), 1)) {
						if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
							e.setCancelled(true);
							if(!Main.instance.cooldownQueue.containsKey(p.getUniqueId()) || p.hasPermission("PlayerToggle.CDBP")) {
								if(!plugin.alreadyvisible.contains(p.getName())) {
									for(Player players : plugin.notwhitelist) {
										try {
											p.showPlayer(plugin, players);
										} catch(NoSuchMethodError ex) {
											p.showPlayer(players);
										}
									}
									plugin.hidden.remove(p.getName());
									plugin.alreadyhidden.remove(p.getName());
									plugin.alreadyvisible.add(p.getName());
									
									if(!p.hasPermission("PlayerToggle.CDBP")) {
										Main.instance.cooldownQueue.put(p.getUniqueId(), Main.instance.cd);
									}
									
									ItemHandler.createToggleItem(2, p, true, false);
									ItemHandler.createToggleItem(1, p, false, true);
						            
						            p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));

						            p.playEffect(p.getLocation(), Effect.valueOf(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.EffectOnToggle").toUpperCase()), 4);
						            try {
							            p.playSound(p.getLocation(), TTA_Sounds.GetSoundByName(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.SoundOnToggle").toUpperCase()), 10, 1);
						            } catch(Exception ex) {
				    		        	Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cError while playing sound effect! \n Name of the sound need to be replaced to match the current MC version!");
						            }
						            
									String t = plugin.getConfig().getString("PlayerToggle.Titles.Title.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
									String st = plugin.getConfig().getString("PlayerToggle.Titles.Subtitle.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
									String ab = plugin.getConfig().getString("PlayerToggle.ActionBar.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
									
									if(plugin.getConfig().getBoolean("PlayerToggle.ActionBar.Enabled") == true) {
										TTA_Methods.sendActionBar(p, ab);
									}
									if(plugin.getConfig().getBoolean("PlayerToggle.Titles.Enabled") == true) {
										TTA_Methods.sendTitle(p, t, fadeint, stayt, fadeoutt, st, fadeint, stayt, fadeoutt);
									}
								} else {
						            p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.AlreadyVisible/HiddenItemUse.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
								}
							} else {
								p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.Cooldown.CooldownMessage").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä").replace("[T]", new StringBuilder(String.valueOf(Main.instance.cooldownQueue.get(p.getUniqueId()))).toString()));
							}
						}
					}
				}
			} else {
				if ((((e.getAction() == Action.RIGHT_CLICK_AIR ? 1 : 0) | (e.getAction() == Action.RIGHT_CLICK_BLOCK ? 1 : 0)) != 0) && (e.getItem() != null)) {
					if(ItemHandler.compareMaterials(e.getItem().getType(), 1) | ItemHandler.compareMaterials(e.getItem().getType(), 2)) {
						if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä")) | e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
							plugin.inv = Bukkit.getServer().createInventory(p, 9, plugin.getConfig().getString("PlayerToggle.Inventory.InventoryTitle").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
					        
					    	ItemStack Filler = ItemHandler.returnCustomizedItemStack(10);
							ItemStack I1 = ItemHandler.returnCustomizedItemStack(5);
							ItemStack I2 = ItemHandler.returnCustomizedItemStack(6);
							ItemStack I3 = ItemHandler.returnCustomizedItemStack(7);

					        plugin.inv.setItem(0, I1);
					        plugin.inv.setItem(1, Filler);
					        plugin.inv.setItem(2, Filler);
					        plugin.inv.setItem(3, Filler);
					        plugin.inv.setItem(4, I2);
					        plugin.inv.setItem(5, Filler);
					        plugin.inv.setItem(6, Filler);
					        plugin.inv.setItem(7, Filler);
					        plugin.inv.setItem(8, I3);

					        p.openInventory(plugin.inv);
						}
					}
				}
			}
		}
	}

}
