package com.gildorym.professionrestrictions;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;

import com.gildorym.basicchar.BasicChar;
import com.gildorym.basicchar.CharacterProfession;

public class InventoryClickListener implements Listener {
	
	private ProfessionRestrictions plugin;
	
	public InventoryClickListener(ProfessionRestrictions plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getType() == InventoryType.FURNACE) {
			if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
				BasicChar basicChar = (BasicChar) plugin.getServer().getPluginManager().getPlugin("BasicChar");
				CharacterProfession profession = basicChar.professions.get(event.getWhoClicked().getName());
				if (profession != null) {
					if (!plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".smelt").contains(this.getFurnaceResult(event.getCurrentItem().getType()).toString())
							&& !plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".smelt").contains(this.getFurnaceResult(event.getCurrentItem().getType()).getId())) {
						event.setCancelled(true);
					}
				} else {
					event.setCancelled(true);
				}
			}
		}
	}
	
	private Material getFurnaceResult(Material ingredient) {
		Material result = Material.AIR;
		Iterator<Recipe> recipeIterator = Bukkit.getServer().recipeIterator();
		while (recipeIterator.hasNext()) {
			Recipe recipe = recipeIterator.next();
			if (recipe instanceof FurnaceRecipe) {
				FurnaceRecipe furnaceRecipe = (FurnaceRecipe) recipe;
				if (furnaceRecipe.getInput().getType() == ingredient) {
					result = furnaceRecipe.getResult().getType();
				}
			}
		}
		return result;
	}

}
