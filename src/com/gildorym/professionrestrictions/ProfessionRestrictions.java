package com.gildorym.professionrestrictions;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ProfessionRestrictions extends JavaPlugin {
	
	public void onEnable() {
		this.saveDefaultConfig();
		this.registerListeners(new CraftItemListener(this), new PlayerInteractListener(this), new InventoryClickListener(this));
	}
	
	private void registerListeners(Listener... listeners) {
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}

}
