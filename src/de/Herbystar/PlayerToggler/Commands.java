package de.Herbystar.PlayerToggler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import b.TTA_Sounds;
import de.Herbystar.PlayerToggler.Utilities.ItemHandler;
import de.Herbystar.TTA.TTA_Methods;

public class Commands implements CommandExecutor {
	
	Main plugin;
	public Commands(Main main) {
		plugin = main;
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		
		int fadeint = plugin.getConfig().getInt("PlayerToggle.Titles.FadeIn");
		int stayt = plugin.getConfig().getInt("PlayerToggle.Titles.Stay");
		int fadeoutt = plugin.getConfig().getInt("PlayerToggle.Titles.FadeOut");
		
		Player p = null;
		if(sender instanceof Player) {
			p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("ptoggle") && p != null) {
				if(!Main.instance.cooldownQueue.containsKey(p.getUniqueId()) || p.hasPermission("PlayerToggle.CDBP")) {
					if(!plugin.PT.contains(p.getName())) {
						for(Player players : plugin.notwhitelist) {
							try {
								p.hidePlayer(plugin, players);
							} catch(NoSuchMethodError ex) {
								p.hidePlayer(players);
							}
						}
						plugin.PT.add(p.getName());
						plugin.hidden.add(p.getName());
						
						if(!p.hasPermission("PlayerToggle.CDBP")) {
							Main.instance.cooldownQueue.put(p.getUniqueId(), Main.instance.cd);
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
			            
			            p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
			            return true;
					} else {
						for(Player players : plugin.notwhitelist) {
							try {
								p.showPlayer(plugin, players);
							} catch(NoSuchMethodError ex) {
								p.showPlayer(players);
							}
						}
						plugin.PT.remove(p.getName());
						plugin.hidden.remove(p.getName());
						
						if(!p.hasPermission("PlayerToggle.CDBP")) {
							Main.instance.cooldownQueue.put(p.getUniqueId(), Main.instance.cd);
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

			            p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
				        return true;
					}
				} else {
					p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.Cooldown.CooldownMessage").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä").replace("[T]", new StringBuilder(String.valueOf(Main.instance.cooldownQueue.get(p.getUniqueId()))).toString()));
			        return true;
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("phelp") && p != null) {
				p.sendMessage("§4[]==============[§a§lPlayerToggle§4]==============[]");
				p.sendMessage("§c /ptoggle §8-> §aToggle the visibility of the players!");
				p.sendMessage("§c /preload §8-> §aReload the configuration!");
				p.sendMessage("§c /pfriend §8-> §aShows all friend system commands!");
				p.sendMessage("§c /pgui §8-> §aOpen the toggle inventory!");
				p.sendMessage("§4[]==============[§a§lPlayerToggle§4]==============[]");
				return true;
			}
			
			if(cmd.getName().equalsIgnoreCase("pgui") && p != null) {
				if(plugin.getConfig().getBoolean("PlayerToggle.InventoryMode") == true) {
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
			          return true;
				} else {
					if(p.isOp()) {
						p.sendMessage(plugin.prefix + "§cYou must enable the Inventory Mode in the configuration!");
						return true;
					}
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("preload") && p != null) {
				if(p.hasPermission("PlayerToggle.reload")) {
					plugin.reloadConfig();
			        p.sendMessage(plugin.prefix + "§aConfiguration reloaded!");
			        return true;
				} else {
			        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.NoPermMessage"));
			        return true;
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("pfriend") && p != null) {
				if(args.length == 0) {
					p.sendMessage("§e[]==========[§c§lPlayerToggle Friends§e]==========[]");
					p.sendMessage("§e /pfriend add §c<username> §8-> §eAdd a user to your friend list!");
					p.sendMessage("§e /pfriend remove §c<username> §8-> §eRemove a user from your friend list!");
					p.sendMessage("§e /pfriend list §8-> §eLists all your friends!");
					p.sendMessage("§e /pfriend visibility §8-> §eToggle the visibility of your friends!");
					p.sendMessage("§e[]==========[§c§lPlayerToggle Friends§e]==========[]");
					return true;
				}
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("visibility")) {
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
			    		        p.playSound(p.getLocation(), TTA_Sounds.GetSoundByName(plugin.getConfig().getString("PlayerToggle.Inventory.Item2.SoundOnToggle").toUpperCase()), 10, 1);
		    		        } catch(Exception ex) {
		    		        	Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cError while playing sound effect! \n Name of the sound need to be replaced to match the current version!");
		    		        }

		    				plugin.I2IIAS.add(p.getName());
							String t = plugin.getConfig().getString("PlayerToggle.Titles.Title.Friends").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
							String st = plugin.getConfig().getString("PlayerToggle.Titles.Subtitle.Friends").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
							String ab = plugin.getConfig().getString("PlayerToggle.ActionBar.Friends").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
							
							if(plugin.getConfig().getBoolean("PlayerToggle.ActionBar.Enabled") == true) {
								TTA_Methods.sendActionBar(p, ab);
							}
							if(plugin.getConfig().getBoolean("PlayerToggle.Titles.Enabled") == true) {
								TTA_Methods.sendTitle(p, t, fadeint, stayt, fadeoutt, st, fadeint, stayt, fadeoutt);							
							}
							
		    		        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Friends").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
		    		        return true;
						} else {
							if(plugin.I1IIAS.contains(p.getName())) {
			    				for(Player players : plugin.notwhitelist) {
									try {
										p.hidePlayer(plugin, players);
									} catch(NoSuchMethodError ex) {
				    					p.hidePlayer(players);
									}
			    				}
			    				plugin.hidden.add(p.getName());
			    				plugin.I2IIAS.remove(p.getName());
			    				plugin.I3IIAS.remove(p.getName());
			    				
			    		        p.playEffect(p.getLocation(), Effect.valueOf(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.EffectOnToggle").toUpperCase()), 4);
			    		        try {
				    		        p.playSound(p.getLocation(), TTA_Sounds.GetSoundByName(plugin.getConfig().getString("PlayerToggle.Items.Toggler2.SoundOnToggle").toUpperCase()), 10, 1);
			    		        } catch(Exception ex) {
			    		        	Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cError while playing sound effect! \n Name of the sound need to be replaced to match the current version!");
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
								
			    		        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Hidden").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
			    		        return true;
							}
							if(plugin.I3IIAS.contains(p.getName())) {
			    				for(Player players : plugin.notwhitelist) {
									try {
										p.showPlayer(plugin, players);
									} catch(NoSuchMethodError ex) {
										p.showPlayer(players);
									}
			    				}
			    				plugin.hidden.add(p.getName());
			    				plugin.I1IIAS.remove(p.getName());
			    				plugin.I2IIAS.remove(p.getName());
			    				
			    		        p.playEffect(p.getLocation(), Effect.valueOf(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.EffectOnToggle").toUpperCase()), 4);
			    		        try {
				    		        p.playSound(p.getLocation(), TTA_Sounds.GetSoundByName(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.SoundOnToggle").toUpperCase()), 10, 1);
			    		        } catch(Exception ex) {
			    		        	Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cError while playing sound effect! \n Name of the sound need to be replaced to match the current version!");
			    		        }
			    		        
			    		        String ab = plugin.getConfig().getString("PlayerToggle.ActionBar.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
			    		        
					            if(plugin.getConfig().getBoolean("PlayerToggle.ActionBar.Enabled") == true) {
					            	TTA_Methods.sendActionBar(p, ab);
					            }
			    		        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
			    		        return true;
							}
							if(!plugin.I1IIAS.contains(p.getName()) && !plugin.I3IIAS.contains(p.getName())) {
			    				for(Player players : plugin.notwhitelist) {
									try {
										p.showPlayer(plugin, players);
									} catch(NoSuchMethodError ex) {
										p.showPlayer(players);
									}
			    				}
			    				plugin.hidden.add(p.getName());
			    				plugin.I1IIAS.remove(p.getName());
			    				plugin.I2IIAS.remove(p.getName());
			    				
			    		        p.playEffect(p.getLocation(), Effect.valueOf(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.EffectOnToggle").toUpperCase()), 4);
			    		        try {
				    		        p.playSound(p.getLocation(), TTA_Sounds.GetSoundByName(plugin.getConfig().getString("PlayerToggle.Items.Toggler1.SoundOnToggle").toUpperCase()), 10, 1);
			    		        } catch(Exception ex) {
			    		        	Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cError while playing sound effect! \n Name of the sound need to be replaced to match the current version!");
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
			    		        p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.ToggleMessage.Visible").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
			    		        return true;
							}
						}
					}
					if(args[0].equalsIgnoreCase("add")) {
						p.sendMessage(plugin.prefix + "§e/pfriend add §c<username> §8-> §eAdd a user to your friend list!");
						return true;
					}
					if(args[0].equalsIgnoreCase("remove")) {
						p.sendMessage(plugin.prefix + "§e/pfriend remove §c<username> §8-> §eRemove a user from your friend list!");
						return true;
					}
					if(args[0].equalsIgnoreCase("list")) {
						String friends = "";
						List<String> cfriends = plugin.getConfig().getStringList("PlayerToggle.Friends." + p.getUniqueId());
						if(!cfriends.isEmpty()) {
							for(int i = 0; i < cfriends.size(); i++) {
								UUID cf = UUID.fromString(cfriends.get(i));
								if(Bukkit.getPlayer(cf) != null) {
									friends = friends + Bukkit.getServer().getPlayer(cf).getName() + plugin.getConfig().getString("PlayerToggle.FriendOnline").replace("&", "§") + plugin.getConfig().getString("PlayerToggle.FriendListSeparator").replace("&", "§");
								} else {
									friends = friends + Bukkit.getServer().getOfflinePlayer(cf).getName() + plugin.getConfig().getString("PlayerToggle.FriendOffline").replace("&", "§") + plugin.getConfig().getString("PlayerToggle.FriendListSeparator").replace("&", "§");
								}
							}
							p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.FriendList").replace("&", "§") + friends);
							return true;
						} else {
							p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.NoFriends").replace("&", "§"));
							return true;
						}
					}
				}
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("add")) {
						Player e = Bukkit.getServer().getPlayer(args[1]);
						if(e == null) {
							p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.PlayerNotOnline").replace("&", "§").replace("[PN]", Bukkit.getServer().getOfflinePlayer(args[1]).getName()));
							return true;
						} else {
							List<String> testfriends = plugin.getConfig().getStringList("PlayerToggle.Friends." + p.getUniqueId());
							if(testfriends == null) {
								List<String> friends = new ArrayList<String>();
								if(!friends.contains(Bukkit.getPlayer(args[1]).getUniqueId().toString())) {
									friends.add(Bukkit.getServer().getPlayer(args[1]).getUniqueId().toString());
									plugin.getConfig().set("PlayerToggle.Friends." + p.getUniqueId(), friends);
									plugin.saveConfig();
									p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.PlayerAddToFriendList").replace("&", "§").replace("[PN]", Bukkit.getServer().getPlayer(args[1]).getName()));
									return true;
								} else {
									p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.PlayerAlreadyOnYourFriendList").replace("&", "§"));
									return true;
								}
							} else {
								List<String> friends = plugin.getConfig().getStringList("PlayerToggle.Friends." + p.getUniqueId());
								if(!friends.contains(Bukkit.getPlayer(args[1]).getUniqueId().toString())) {
									friends.add(Bukkit.getServer().getPlayer(args[1]).getUniqueId().toString());
									plugin.getConfig().set("PlayerToggle.Friends." + p.getUniqueId(), friends);
									plugin.saveConfig();
									p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.PlayerAddToFriendList").replace("&", "§").replace("[PN]", Bukkit.getServer().getPlayer(args[1]).getName()));
									return true;
								} else {
									p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.PlayerAlreadyOnYourFriendList").replace("&", "§"));
									return true;
								}
							}
						}						
					}
					if(args[0].equalsIgnoreCase("remove")) {
						Player e = Bukkit.getServer().getPlayer(args[1]);
						if(e == null) {
							p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.PlayerNotOnline").replace("&", "§").replace("[PN]", Bukkit.getServer().getOfflinePlayer(args[1]).getName()));
							return true;
						} else {
							List<String> friends = plugin.getConfig().getStringList("PlayerToggle.Friends." + p.getUniqueId());
							if(friends.contains(Bukkit.getServer().getPlayer(args[1]).getUniqueId().toString())) {
								friends.remove(Bukkit.getServer().getPlayer(args[1]).getUniqueId().toString());
								plugin.getConfig().set("PlayerToggle.Friends." + p.getUniqueId(), friends);
								plugin.saveConfig();
								p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.PlayerRemoveFromFriendList").replace("&", "§").replace("[PN]", Bukkit.getServer().getPlayer(args[1]).getName()));
								return true;
							} else {
								p.sendMessage(plugin.prefix + plugin.getConfig().getString("PlayerToggle.PlayerNotOnYourFriendList").replace("&", "§").replace("[PN]", Bukkit.getServer().getPlayer(args[1]).getName()));
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

}
