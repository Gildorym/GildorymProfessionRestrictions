package com.gildorymrp.professionrestrictions;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.Potion;

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
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if (event.getPlayer().getItemInHand() != null) {
				if (event.getPlayer().getItemInHand().getType() == Material.POTION) {
					Potion potion = Potion.fromItemStack(event.getPlayer().getItemInHand());
					if (!plugin.getConfig().getStringList("default.use").contains(potion.getType().toString())){
						if (profession == null || !plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".use").contains(potion.getType().toString())) {
							event.setCancelled(true);
							String message = "You can not use a " + potion.getType().toString();
							if (profession != null) {
								message += " as a " + profession.toString();
							} else {
								message += " without the correct training!";
							}
							//event.getPlayer().sendMessage(message);
						}
					}
				} else {
					if (!plugin.getConfig().getStringList("default.use").contains(event.getPlayer().getItemInHand().getType().toString())) {
						if (profession == null || !plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".use").contains(event.getPlayer().getItemInHand().getType().toString())) {
							event.setCancelled(true);
							String message = "You can not use a " + event.getPlayer().getItemInHand().getType().toString();
							if (profession != null) {
								message += " as a " + profession.toString();
							} else {
								message += " without the correct training!";
							}
							//event.getPlayer().sendMessage(message);
						}
					}
				}
			}

			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (!plugin.getConfig().getStringList("default.interact").contains(event.getClickedBlock().getType().toString())){
					if (profession == null || !plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".interact").contains(event.getClickedBlock().getType().toString())) {
						event.setCancelled(true);
						String message = "You can not interact with " + event.getClickedBlock().getType().toString();
						if (profession != null) {
							message += " as a " + profession.toString();
						} else {
							message += " without the correct training!";
						}						
						//event.getPlayer().sendMessage(message);
					}
				}
			}
		}
	}

}
