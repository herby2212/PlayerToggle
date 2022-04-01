package de.Herbystar.PlayerToggler;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.Herbystar.PlayerToggler.Events.InventoryClickEventHandler;
import de.Herbystar.PlayerToggler.Events.PlayerChangeWorldEventHandler;
import de.Herbystar.PlayerToggler.Events.PlayerDropItemEventHandler;
import de.Herbystar.PlayerToggler.Events.PlayerInteractEventHandler;
import de.Herbystar.PlayerToggler.Events.PlayerJoinEventHandler;
import de.Herbystar.PlayerToggler.Events.PlayerQuitEventHandler;
import de.Herbystar.PlayerToggler.Events.PlayerRespawnEventHandler;
import de.Herbystar.PlayerToggler.Utilities.MySQL;

public class Main extends JavaPlugin {
	
	public String prefix = getConfig().getString("PlayerToggle.CustomPrefix").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä");
	
	public ArrayList<String> hidden = new ArrayList<String>();
	public ArrayList<Player> nofriends = new ArrayList<Player>();
	public ArrayList<String> PT = new ArrayList<String>();
	public ArrayList<String> alreadyhidden = new ArrayList<String>();
	public ArrayList<String> alreadyvisible = new ArrayList<String>();
	public ArrayList<Player> notwhitelist = new ArrayList<Player>();
	public ArrayList<String> I1IIAS = new ArrayList<String>();
	public ArrayList<String> I2IIAS = new ArrayList<String>();
	public ArrayList<String> I3IIAS = new ArrayList<String>();
	
	public int cd = getConfig().getInt("PlayerToggle.Cooldown.CooldownOfToggle(InSeconds)");
	public boolean UpdateAviable;
	public String version;
	public Pattern Version_Pattern = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");
	public Inventory inv;
	public static Main instance;
	
	private BukkitTask cooldownTask;
	public HashMap<UUID, Integer> cooldownQueue = new HashMap<UUID, Integer>();
	
	public void onEnable() {
		loadConfig();
		instance = this;
		getCommands();
		getEvents();
		if(this.CheckDepends() == false) {
			Bukkit.getServer().getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cYou need to download §eTTA§c!");
			Bukkit.getServer().getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cPlayerToggle will now shut down!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		if(getConfig().getBoolean("PlayerToggle.MySQL.Enabled") == true) {
			MySQL m = new MySQL(this);
			m.connect(); 
			String table = getConfig().getString("PlayerToggle.MySQL.Database");
			m.executeupdate("CREATE TABLE IF NOT EXISTS" + table + "(UUID VARCHAR(50),Friends VARCHAR(50)");
		}
		
		this.cooldownTask = new BukkitRunnable() {
			
			@Override
			public void run() {
				try {
					Iterator<Entry<UUID,Integer>> entryIterator = cooldownQueue.entrySet().iterator();
					while(entryIterator.hasNext()) {
						Entry<UUID, Integer> entry = entryIterator.next();
						int cd = entry.getValue();
						UUID key = entry.getKey();
						if(cd == 1) {
							cooldownQueue.remove(key);
						} else {
							entry.setValue(cd - 1);
						}
					}
				} catch(ConcurrentModificationException ex) {}
			}
		}.runTaskTimer(instance, 0L, 20L);
		
		//StartMetrics();
		Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] " + ChatColor.AQUA + "Version: " + getDescription().getVersion() + " §aby " + "§c" + getDescription().getAuthors() + ChatColor.GREEN + " enabled!");
	}
	
	public void onDisable() {
		if(getConfig().getBoolean("PlayerToggle.MySQL.Enabled") == true) {
			MySQL m = new MySQL(this);
			m.disconnect();  
		}
		
		Main.instance.cooldownTask.cancel();
		
		Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] " + ChatColor.AQUA + "Version: " + getDescription().getVersion() + " §aby " + "§c" + getDescription().getAuthors() + ChatColor.GREEN + ChatColor.RED + " disabled!");
	}
	
	//Depends Hook (If false PlayerToggle not work!)
	public boolean CheckDepends() {
		if(Bukkit.getServer().getPluginManager().getPlugin("TTA") != null) {
			return true;
		}
		return false;		
	}
	
	private void getEvents() {
		Bukkit.getPluginManager().registerEvents(new InventoryClickEventHandler(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerChangeWorldEventHandler(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDropItemEventHandler(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteractEventHandler(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinEventHandler(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerRespawnEventHandler(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitEventHandler(), this);
	}
	
	private void getCommands() {
		getCommand("ptoggle").setExecutor(new Commands(this));
		getCommand("pgui").setExecutor(new Commands(this));
		getCommand("preload").setExecutor(new Commands(this));
		getCommand("pfriend").setExecutor(new Commands(this));
		getCommand("phelp").setExecutor(new Commands(this));
	}
	/*
	private void StartMetrics() {
		try {
			Metrics m = new Metrics(this);
			m.start();
			Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §aStarted §eMetrics §asuccessful!");
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §cFailed to start the §eMetrics§c!");
		}
	}
	*/
	public String getServerVersion() {
		if(version != null) {
			return version;
		}
		String pkg = Bukkit.getServer().getClass().getPackage().getName();
		String version1 = pkg.substring(pkg.lastIndexOf(".") +1);
		if(!Version_Pattern.matcher(version1).matches()) {
			version1 = "";
		}
		String version = version1;
		return version = !version.isEmpty() ? version + "." : ""; 
	}
	
	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}
