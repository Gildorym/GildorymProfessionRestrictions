package com.gildorymrp.professionrestrictions;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorymclasses.CharacterProfession;

public class PlayerInteractEntityListener implements Listener {

	private GildorymProfessionRestrictions plugin;

	public PlayerInteractEntityListener(GildorymProfessionRestrictions plugin) {
		this.plugin = plugin;
	}


	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
		CharacterProfession[] professions = gildorym.getActiveCharacters().get(event.getPlayer().getName()).getProfessions();
		String prof = "default";
		
		if (professions != null) {
			prof = professions[0].toString();
		} 
		
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if (event.getPlayer().getItemInHand().getType() != Material.AIR){
				if (!plugin.getConfig().getStringList("default.entity").contains(event.getRightClicked().getType().toString())){
					if (!plugin.getConfig().getStringList(prof.toLowerCase() + ".entity").contains(event.getRightClicked().getType().toString())) {
						event.setCancelled(true);
						String message = "You can not interact with " + event.getRightClicked().getType().toString();
						if (professions != null) {
							message += " as a " + professions[0].toString();
						} else {
							message += " without the correct training!";
							event.getPlayer().sendMessage(message);
						}
					}
				}
			}
		}
	}
}
