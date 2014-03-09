package com.gildorymrp.professionrestrictions;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorymclasses.CharacterProfession;

public class BlockBreakListener implements Listener {

	private GildorymProfessionRestrictions plugin;

	public BlockBreakListener(GildorymProfessionRestrictions plugin) {
		this.plugin = plugin;
	}
	

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
		CharacterProfession[] professions = gildorym.getActiveCharacters().get(event.getPlayer().getName()).getProfessions();
		String prof = "default";
		
		if (professions != null) {
			prof = professions[0].toString();
		} 
		
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if (!plugin.getConfig().getStringList("default.break").contains(event.getBlock().getType().toString())){
				if (!plugin.getConfig().getStringList(prof.toLowerCase() + ".break").contains(event.getBlock().getType().toString())) {
					event.setCancelled(true);
					String message = "You can not break " + event.getBlock().getType().toString();
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
