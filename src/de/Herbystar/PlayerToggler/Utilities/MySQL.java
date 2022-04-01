package de.Herbystar.PlayerToggler.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import de.Herbystar.PlayerToggler.Main;

public class MySQL {
	
	static Main plugin;
	public MySQL(Main main) {
		plugin = main;
	}
	
	public String host = "";
	public String port = "";
	public String database = "";
	public String username = "";
	public String password = "";
	public Connection con;
	
	
	public void connect() {
		host = plugin.getConfig().getString("PlayerToggle.MySQL.Host");
		port = plugin.getConfig().getString("PlayerToggle.MySQL.Port");
		database = plugin.getConfig().getString("PlayerToggle.MySQL.Database");
		username = plugin.getConfig().getString("PlayerToggle.MySQL.Username");
		password = plugin.getConfig().getString("PlayerToggle.MySQL.Password");
		if(connected() == false) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
				Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §eMySQL§7-§eConnection §aconstructed!");
			} catch (SQLException e) {
				e.printStackTrace();
				Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §eMySQL§7-§eConnection §cfailed!");
			}
		}
	}
	
	public void disconnect() {
		if(connected() == true) {
			try {
				con.close();
				Bukkit.getConsoleSender().sendMessage("§4[§aPlayerToggle§4] §eMySQL§7-§eConnection §cclosed!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean connected() {
		if(con == null) {
			return false;
		}
		return true;
	}
	
	public void executeupdate(String qry) {
		try {
			PreparedStatement ps = con.prepareStatement(qry);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet gerResult(String qry) {
		try {
			PreparedStatement ps;
			ps = con.prepareStatement(qry);
			return ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
