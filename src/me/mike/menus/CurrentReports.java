package me.mike.menus;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import me.mike.Main;

public class CurrentReports {
	
	public static void createReportInventory(Main plugin, Player player) {
		ConfigurationSection section = plugin.getReports().getData().getConfigurationSection("ActiveReports");
		for(String s : section.getKeys(false)) {
			ConfigurationSection cs2 = section.getConfigurationSection(s);
			String reportedPlayers = cs2.getName().toString();
			
			//Returns us all the UUID's in the server.
			Bukkit.getServer().broadcastMessage("Current UUID'S: " + reportedPlayers);
		}
	}

}
