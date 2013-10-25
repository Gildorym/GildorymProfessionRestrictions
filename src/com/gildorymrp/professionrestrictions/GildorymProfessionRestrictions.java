package com.gildorymrp.professionrestrictions;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class GildorymProfessionRestrictions extends JavaPlugin {
	
	public void onEnable() {
		this.saveDefaultConfig();
		this.registerListeners(new CraftItemListener(this), new PlayerInteractListener(this), new InventoryClickListener(this), new BlockBreakListener(this), new PlayerInteractEntityListener(this));
	}
	
	private void registerListeners(Listener... listeners) {
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}
	public void professionMessage(Player player, String Profession){
		
	}

}
