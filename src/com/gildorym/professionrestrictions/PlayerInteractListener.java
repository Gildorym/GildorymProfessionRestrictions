package com.gildorym.professionrestrictions;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gildorym.basicchar.BasicChar;
import com.gildorym.basicchar.CharacterProfession;

public class PlayerInteractListener implements Listener {

	private ProfessionRestrictions plugin;
	
	public PlayerInteractListener(ProfessionRestrictions plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		BasicChar basicChar = (BasicChar) plugin.getServer().getPluginManager().getPlugin("BasicChar");
		CharacterProfession profession = basicChar.professions.get(event.getPlayer().getName());
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
