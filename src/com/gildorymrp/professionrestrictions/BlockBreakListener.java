package com.gildorymrp.professionrestrictions;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.gildorymrp.gildorymclasses.CharacterProfession;
import com.gildorymrp.gildorymclasses.GildorymClasses;

public class BlockBreakListener implements Listener {

	private GildorymProfessionRestrictions plugin;

	public BlockBreakListener(GildorymProfessionRestrictions plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		GildorymClasses gildorymClasses = (GildorymClasses) plugin.getServer().getPluginManager().getPlugin("GildorymClasses");
		CharacterProfession profession = gildorymClasses.professions.get(event.getPlayer().getName());
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if (!plugin.getConfig().getStringList("default.break").contains(event.getBlock().getType().toString())){
				if (profession == null || !plugin.getConfig().getStringList(profession.toString().toLowerCase() + ".break").contains(event.getBlock().getType().toString())) {
					event.setCancelled(true);
					String message = "You can not break " + event.getBlock().getType().toString();
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
