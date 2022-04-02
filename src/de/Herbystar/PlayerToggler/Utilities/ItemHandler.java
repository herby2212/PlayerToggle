package de.Herbystar.PlayerToggler.Utilities;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.Herbystar.PlayerToggler.Main;

public class ItemHandler {
	
	public static class ToggleItem {
		String configPath = "";
		String material = "";

		public ToggleItem(String mat, String confPath) {
			this.configPath = confPath;
			this.material = mat;
		}
	}
	
	//Normal Switch Items
	private static ToggleItem ti1;
	private static ToggleItem ti2;
	
	//Inventory Items
	private static ToggleItem tiIV1;
	private static ToggleItem tiIV2;
	private static ToggleItem tiIV3;
	private static ToggleItem tiIVF;
	
	public static void createToggleItem(int id, Player player, boolean setItem, boolean updateInv) {
		ItemHandler.pullMatsAndPath();
		
		if(player == null) {
			return;
		}
		
		String configPath = ItemHandler.getToggleItemBasedOnID(id).configPath;
		String material = ItemHandler.getToggleItemBasedOnID(id).material;
				
		XMaterial xm = XMaterial.matchXMaterial(material).get();

		ItemStack PS = xm.parseItem();
	    ItemMeta PSMeta = PS.getItemMeta();
	    PSMeta.setLore(Arrays.asList(new String[] { Main.instance.getConfig().getString(configPath + "Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
	    PSMeta.setDisplayName(Main.instance.getConfig().getString(configPath + "Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
	    PS.setItemMeta(PSMeta);
	    
	    if(setItem == true) {
		    player.getInventory().setItem(Main.instance.getConfig().getInt(configPath + "Slot(0-8)"), PS);
	    }
	    if(updateInv == true && player.getInventory().contains(PS)) {
		      player.getInventory().remove(PS);
		      player.updateInventory();
	    }
	}
	
	public static ItemStack returnCustomizedItemStack(int id) {
		ItemHandler.pullMatsAndPath();
		
		String configPath = ItemHandler.getToggleItemBasedOnID(id).configPath;
		String material = ItemHandler.getToggleItemBasedOnID(id).material;
				
		XMaterial xm = XMaterial.matchXMaterial(material).get();

		ItemStack PS = xm.parseItem();
	    ItemMeta PSMeta = PS.getItemMeta();
	    PSMeta.setLore(Arrays.asList(new String[] { Main.instance.getConfig().getString(configPath + "Lore").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä") }));
	    PSMeta.setDisplayName(Main.instance.getConfig().getString(configPath + "Name").replace("&", "§").replace("Oe", "Ö").replace("oe", "ö").replace("Ue", "Ü").replace("Ae", "Ä").replace("ae", "ä"));
	    PS.setItemMeta(PSMeta);
	    
	    return PS;
	}
	
	public static boolean compareMaterials(Material mat, int id) {
		XMaterial xm = XMaterial.matchXMaterial(ItemHandler.getToggleItemBasedOnID(id).material).get();
		if(mat == xm.parseMaterial()) {
			return true;
		}
		 return false;
	}
	
	private static void pullMatsAndPath() {
		if(ti1 != null && ti2 != null && tiIV1 != null && tiIV2 != null && tiIV3 != null && tiIVF != null) {
			return;
		}
		ti1 = new ToggleItem(Main.instance.getConfig().getString("PlayerToggle.Items.Toggler1.Material"), "PlayerToggle.Items.Toggler1.");
		
		ti2 = new ToggleItem(Main.instance.getConfig().getString("PlayerToggle.Items.Toggler2.Material"), "PlayerToggle.Items.Toggler2.");
		
		tiIV1 = new ToggleItem(Main.instance.getConfig().getString("PlayerToggle.Inventory.Item1.Material"), "PlayerToggle.Inventory.Item1.");
		
		tiIV2 = new ToggleItem(Main.instance.getConfig().getString("PlayerToggle.Inventory.Item2.Material"), "PlayerToggle.Inventory.Item2.");
		
		tiIV3 = new ToggleItem(Main.instance.getConfig().getString("PlayerToggle.Inventory.Item3.Material"), "PlayerToggle.Inventory.Item3.");
		
		tiIVF = new ToggleItem(Main.instance.getConfig().getString("PlayerToggle.Inventory.Filler.Material"), "PlayerToggle.Inventory.Filler.");
		
	}
	
	private static ToggleItem getToggleItemBasedOnID(int id) {
		if(id == 1) {
			return ti1;
		} else if(id == 2) {
			return ti2;
		} else if(id == 5) {
			return tiIV1;
		} else if(id == 6) {
			return tiIV2;
		} else if(id == 7) {
			return tiIV3;
		} else if(id == 10) {
			return tiIVF;
		}
		return null;
	}

}
