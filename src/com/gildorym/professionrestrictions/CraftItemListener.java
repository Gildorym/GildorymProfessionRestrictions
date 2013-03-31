package com.gildorym.professionrestrictions;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gildorym.basicchar.BasicChar;
import com.gildorym.basicchar.CharacterProfession;

public class CraftItemListener implements Listener {
	
	private ProfessionRestrictions plugin;

	public CraftItemListener(ProfessionRestrictions plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onCraftItem(CraftItemEvent event) {
		BasicChar basicChar = (BasicChar) plugin.getServer().getPluginManager().getPlugin("BasicChar");
		CharacterProfession profession = basicChar.professions.get(event.getViewers().get(0).getName());
		if (profession != null) {
			if (event.getViewers().get(0).getGameMode() != GameMode.CREATIVE) {
				if (!plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".craft").contains(event.getRecipe().getResult().getType().toString())
						&& !plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".craft").contains(event.getRecipe().getResult().getTypeId())) {
					event.setCancelled(true);
				}
			}
		} else {
			event.setCancelled(true);
		}
	}

}
