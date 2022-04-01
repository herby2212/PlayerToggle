package de.Herbystar.PlayerToggler.Events;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import b.TTA_Sounds;
import de.Herbystar.PlayerToggler.Main;
import de.Herbystar.PlayerToggler.Utilities.ItemHandler;
import de.Herbystar.TTA.TTA_Methods;

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
//					if(typeId == plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.ID", (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.DataValue"))) {
						if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
							e.setCancelled(true);
							if(!Main.instance.cooldownQueue.containsKey(p.getUniqueId()) || p.hasPermission("PlayerToggle.CDBP")) {
//							if(!plugin.cooldown.contains(p.getName()) | p.hasPermission("PlayerToggle.CDBP")) {
								if(!plugin.alreadyhidden.contains(p.getName())) {
									for(Player players : plugin.notwhitelist) {
										try {
											p.hidePlayer(plugin, players);
										} catch(NoSuchMethodError ex) {
											p.hidePlayer(players);
										}
									}
									plugin.hidden.add(p.getName());
//									plugin.cooldown.add(p.getName());
									plugin.alreadyhidden.add(p.getName());
									plugin.alreadyvisible.remove(p.getName());
									
									if(!p.hasPermission("PlayerToggle.CDBP")) {
										Main.instance.cooldownQueue.put(p.getUniqueId(), Main.instance.cd);
//										plugin.cd1id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
//
//											@Override
//											public void run() {
//												plugin.cd -= 1;
//												if(plugin.cd == 0) {
//													plugin.cooldown.remove(p.getName());
//													plugin.cd = plugin.getConfig().getInt("PlayerToggle.Cooldown.CooldownOfToggle(InSeconds)");
//													Bukkit.getScheduler().cancelTask(plugin.cd1id);
//												}
//											}
//											
//										}
//										, 0L, 20L);
									}
									ItemHandler.createToggleItem(1, p, true, false);
									
//								    ItemStack PS1 = new ItemStack(Material.YELLOW_DYE, 1);
////									ItemStack PS1 = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.DataValue"));
//						            ItemMeta PS1Meta = PS1.getItemMeta();
//						            PS1Meta.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//						            PS1Meta.setDisplayName(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//						            PS1.setItemMeta(PS1Meta);

						            ItemHandler.createToggleItem(2, p, false, true);
//						            ItemStack PS2 = new ItemStack(Material.LIME_DYE, 1);
////						            ItemStack PS2 = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.DataValue"));
//						            ItemMeta PS2Meta = PS2.getItemMeta();
//						            PS2Meta.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//						            PS2Meta.setDisplayName(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//						            PS2.setItemMeta(PS2Meta);
//
//						            p.getInventory().remove(PS2);
//						            p.getInventory().setItem(plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.Slot(0-8)"), PS1);
//						            p.updateInventory();
//						            
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
//								p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.Cooldown.CooldownMessage").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä").replace("[T]", new StringBuilder(String.valueOf(plugin.cd)).toString()));
							}
						}
					}
					if(ItemHandler.compareMaterials(e.getItem().getType(), 1)) {
//					if(typeId == plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.ID", (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.DataValue"))) {
						if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
							e.setCancelled(true);
							if(!Main.instance.cooldownQueue.containsKey(p.getUniqueId()) || p.hasPermission("PlayerToggle.CDBP")) {
//							if(!plugin.cooldown.contains(p.getName()) | p.hasPermission("PlayerToggle.CDBP")) {
								if(!plugin.alreadyvisible.contains(p.getName())) {
									for(Player players : plugin.notwhitelist) {
										try {
											p.showPlayer(plugin, players);
										} catch(NoSuchMethodError ex) {
											p.showPlayer(players);
										}
									}
									plugin.hidden.remove(p.getName());
//									plugin.cooldown.add(p.getName());
									plugin.alreadyhidden.remove(p.getName());
									plugin.alreadyvisible.add(p.getName());
									
									if(!p.hasPermission("PlayerToggle.CDBP")) {
										Main.instance.cooldownQueue.put(p.getUniqueId(), Main.instance.cd);
//										plugin.cd2id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
//
//											@Override
//											public void run() {
//												plugin.cd -= 1;
//												if(plugin.cd == 0) {
//													plugin.cooldown.remove(p.getName());
//													plugin.cd = plugin.getConfig().getInt("PlayerToggle.Cooldown.CooldownOfToggle(InSeconds)");
//													Bukkit.getScheduler().cancelTask(plugin.cd2id);
//												}
//											}
//											
//										}
//										, 0L, 20L);
									}
									
									ItemHandler.createToggleItem(2, p, true, false);
//									ItemStack PS2 = new ItemStack(Material.LIME_DYE, 1);
////						            ItemStack PS2 = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.DataValue"));
//						            ItemMeta PS2Meta = PS2.getItemMeta();
//						            PS2Meta.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//						            PS2Meta.setDisplayName(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//						            PS2.setItemMeta(PS2Meta);
//						            p.getInventory().setItem(plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.Slot(0-8)"), PS2);

									ItemHandler.createToggleItem(1, p, false, true);
//						            ItemStack PS3 = new ItemStack(Material.YELLOW_DYE, 1);
////						            ItemStack PS3 = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.DataValue"));
//						            ItemMeta PS3Meta = PS3.getItemMeta();
//						            PS3Meta.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//						            PS3Meta.setDisplayName(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//						            PS3.setItemMeta(PS3Meta);
//
//						            p.getInventory().remove(PS3);
//					                p.updateInventory();
						            
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
//								p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.Cooldown.CooldownMessage").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä").replace("[T]", new StringBuilder(String.valueOf(plugin.cd)).toString()));
							}
						}
					}
				}
			} else {
				if ((((e.getAction() == Action.RIGHT_CLICK_AIR ? 1 : 0) | (e.getAction() == Action.RIGHT_CLICK_BLOCK ? 1 : 0)) != 0) && (e.getItem() != null)) {
					if(ItemHandler.compareMaterials(e.getItem().getType(), 1) | ItemHandler.compareMaterials(e.getItem().getType(), 2)) {
//					if(typeId == plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.ID", (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler2.DataValue")) | typeId == plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.ID", (byte)plugin.getConfig().getInt("PlayerToggle.Items.Toggler1.DataValue"))) {
						if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä")) | e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"))) {
							plugin.inv = Bukkit.getServer().createInventory(p, 9, plugin.getConfig().getString("PlayerToggle.Inventory.InventoryTitle").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));

//					        ItemStack Filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
////					        ItemStack Filler = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Inventory.Filler.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Inventory.Filler.DataValue"));
//					        ItemMeta FillerM = Filler.getItemMeta();
//					        FillerM.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Inventory.Filler.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//					        FillerM.setDisplayName(plugin.getConfig().getString("PlayerToggle.Inventory.Filler.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//					        Filler.setItemMeta(FillerM);
//
//					        ItemStack I1 = new ItemStack(Material.YELLOW_DYE, 1);
////					        ItemStack I1 = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Inventory.Item1.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Inventory.Item1.DataValue"));
//					        ItemMeta I1M = I1.getItemMeta();
//					        I1M.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Inventory.Item1.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//					        I1M.setDisplayName(plugin.getConfig().getString("PlayerToggle.Inventory.Item1.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//					        I1.setItemMeta(I1M);
//
//					        ItemStack I2 = new ItemStack(Material.ORANGE_DYE, 1);
////					        ItemStack I2 = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Inventory.Item2.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Inventory.Item2.DataValue"));
//					        ItemMeta I2M = I1.getItemMeta();
//					        I2M.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Inventory.Item2.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//					        I2M.setDisplayName(plugin.getConfig().getString("PlayerToggle.Inventory.Item2.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//					        I2.setItemMeta(I2M);
//
//					        ItemStack I3 = new ItemStack(Material.LIME_DYE, 1);
////					        ItemStack I3 = new ItemStack(plugin.getConfig().getInt("PlayerToggle.Inventory.Item3.ID"), 1, (byte)plugin.getConfig().getInt("PlayerToggle.Inventory.Item3.DataValue"));
//					        ItemMeta I3M = I3.getItemMeta();
//					        I3M.setLore(Arrays.asList(new String[] { plugin.getConfig().getString("PlayerToggle.Inventory.Item3.Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
//					        I3M.setDisplayName(plugin.getConfig().getString("PlayerToggle.Inventory.Item3.Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
//					        I3.setItemMeta(I3M);
					        
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
