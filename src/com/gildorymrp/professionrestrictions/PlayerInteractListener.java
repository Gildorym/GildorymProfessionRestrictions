package com.gildorymrp.professionrestrictions;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.Potion;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorymclasses.CharacterProfession;

public class PlayerInteractListener implements Listener {

	private GildorymProfessionRestrictions plugin;

	public PlayerInteractListener(GildorymProfessionRestrictions plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
		CharacterProfession[] professions = gildorym.getActiveCharacters().get(event.getPlayer().getName()).getProfessions();
		String prof = "default";
		
		if (professions != null) {
			prof = professions[0].toString();
		} 
		
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if (event.getPlayer().getItemInHand() != null) {
				if (event.getPlayer().getItemInHand().getType() == Material.POTION) {
					Potion potion = Potion.fromItemStack(event.getPlayer().getItemInHand());
					if (!plugin.getConfig().getStringList("default.use").contains(potion.getType().toString())){
						if (!plugin.getConfig().getStringList(prof.toLowerCase() + ".use").contains(potion.getType().toString())) {
							event.setCancelled(true);
							String message = "You can not use a " + potion.getType().toString();
							if (professions != null) {
								message += " as a " + prof;
							} else {
								message += " without the correct training!";
								event.getPlayer().sendMessage(message);
							}
						}
					}
				} else {
					if (!plugin.getConfig().getStringList("default.use").contains(event.getPlayer().getItemInHand().getType().toString())) {
						if (!plugin.getConfig().getStringList(prof.toLowerCase() + ".use").contains(event.getPlayer().getItemInHand().getType().toString())) {
							event.setCancelled(true);
							String message = "You can not use a " + event.getPlayer().getItemInHand().getType().toString();
							if (professions != null) {
								message += " as a " + prof;
							} else {
								message += " without the correct training!";
								event.getPlayer().sendMessage(message);
							}
						}
					}
				}
			}

			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (!plugin.getConfig().getStringList("default.interact").contains(event.getClickedBlock().getType().toString())){
					if (!plugin.getConfig().getStringList(prof.toLowerCase() + ".interact").contains(event.getClickedBlock().getType().toString())) {
						event.setCancelled(true);
						String message = "You can not interact with " + event.getClickedBlock().getType().toString();
						if (professions != null) {
							message += " as a " + prof;
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
