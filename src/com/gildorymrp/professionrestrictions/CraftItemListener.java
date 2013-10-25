package com.gildorymrp.professionrestrictions;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gildorymrp.gildorymclasses.CharacterProfession;
import com.gildorymrp.gildorymclasses.GildorymClasses;

public class CraftItemListener implements Listener {

	private GildorymProfessionRestrictions plugin;

	public CraftItemListener(GildorymProfessionRestrictions plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCraftItem(CraftItemEvent event) {
		GildorymClasses gildorymClasses = (GildorymClasses) plugin.getServer().getPluginManager().getPlugin("GildorymClasses");
		CharacterProfession profession = gildorymClasses.professions.get(event.getViewers().get(0).getName());
		if (event.getViewers().get(0).getGameMode() != GameMode.CREATIVE) {
			if (!plugin.getConfig().getStringList("default.craft").contains(event.getRecipe().getResult().getType().toString())){
				if (profession == null || !plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".craft").contains(event.getRecipe().getResult().getType().toString())) {
					event.setCancelled(true);
				}
			}
		}
	}

}
