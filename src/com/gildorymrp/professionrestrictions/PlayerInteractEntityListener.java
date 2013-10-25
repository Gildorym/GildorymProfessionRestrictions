package com.gildorymrp.professionrestrictions;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gildorymrp.gildorymclasses.CharacterProfession;
import com.gildorymrp.gildorymclasses.GildorymClasses;

public class PlayerInteractEntityListener implements Listener {

	private GildorymProfessionRestrictions plugin;
	
	public PlayerInteractEntityListener(GildorymProfessionRestrictions plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		GildorymClasses gildorymClasses = (GildorymClasses) plugin.getServer().getPluginManager().getPlugin("GildorymClasses");
		CharacterProfession profession = gildorymClasses.professions.get(event.getPlayer().getName());
		if (profession != null) {
			if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
				if (event.getPlayer().getItemInHand().getType() != Material.AIR){
					if (!plugin.getConfig().getStringList("default.entity").contains(event.getRightClicked().getType().toString())){
						if (!plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".entity").contains(event.getRightClicked().getType().toString())) {
							event.setCancelled(true);
							event.getPlayer().sendMessage("You can not interact with " + event.getRightClicked().getType().toString() + " as a " + profession.toString());
						}
					}
				}
			}
		} else {
			event.setCancelled(true);
		}
	}
}
