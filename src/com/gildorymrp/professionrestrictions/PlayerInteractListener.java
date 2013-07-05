package com.gildorymrp.professionrestrictions;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gildorymrp.gildorymclasses.CharacterProfession;
import com.gildorymrp.gildorymclasses.GildorymClasses;

public class PlayerInteractListener implements Listener {

	private GildorymProfessionRestrictions plugin;
	
	public PlayerInteractListener(GildorymProfessionRestrictions plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		GildorymClasses gildorymClasses = (GildorymClasses) plugin.getServer().getPluginManager().getPlugin("GildorymClasses");
		CharacterProfession profession = gildorymClasses.professions.get(event.getPlayer().getName());
		if (profession != null) {
			if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
				if (!plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".use").contains(event.getPlayer().getItemInHand().getType().toString())
						&& !plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".use").contains(event.getPlayer().getItemInHand().getTypeId())) {
					event.setCancelled(true);
				}
				
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if (!plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".interact").contains(event.getClickedBlock().getType().toString())
							&& !plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".interact").contains(event.getClickedBlock().getTypeId())) {
						event.setCancelled(true);
					}
				}
			}
		} else {
			event.setCancelled(true);
		}
	}

}
