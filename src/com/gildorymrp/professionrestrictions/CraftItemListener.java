package com.gildorymrp.professionrestrictions;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorymclasses.CharacterProfession;

public class CraftItemListener implements Listener {

	private GildorymProfessionRestrictions plugin;

	public CraftItemListener(GildorymProfessionRestrictions plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCraftItem(CraftItemEvent event) {
		Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
		CharacterProfession[] professions = gildorym.getActiveCharacters().get(event.getViewers().get(0).getName()).getProfessions();
		if (event.getViewers().get(0).getGameMode() != GameMode.CREATIVE) {
			if (!plugin.getConfig().getStringList("default.craft").contains(event.getRecipe().getResult().getType().toString())){
				if (professions == null || !plugin.getConfig().getStringList(professions[0].toString().toLowerCase() + ".craft").contains(event.getRecipe().getResult().getType().toString())) {
					event.setCancelled(true);
				}
			}
		}
	}

}
