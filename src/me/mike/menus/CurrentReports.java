package me.mike.menus;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mike.Main;
import me.mike.utils.MessageUtils;

public class CurrentReports {
	
	public static Main plugin = Main.getPlugin(Main.class);
	
	public static void createReportInventory(Player player) {
		Inventory reports = Bukkit.createInventory(null, 9*5, "Active Reports: ");
		ConfigurationSection section = plugin.getReports().getData().getConfigurationSection("ActiveReports");
		for(String s : section.getKeys(false)) {
			ConfigurationSection cs2 = section.getConfigurationSection(s);
			String reportedPlayers = cs2.getName().toString();
			Player target = (Player) Bukkit.getOfflinePlayer(UUID.fromString(reportedPlayers));
			
			String reason = plugin.getReports().getData().getString("ActiveReports." + reportedPlayers + ".Reason");
			String date = plugin.getReports().getData().getString("ActiveReports." + reportedPlayers + ".Submitted");
			int reportsAmount = plugin.getReports().getData().getInt("ActiveReports." + reportedPlayers + ".Reports");
			
			Bukkit.getServer().broadcastMessage(cs2.toString());

			Bukkit.getServer().broadcastMessage(reportedPlayers);
			
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + target.getName().toString());
			meta.setLore(Arrays.asList(" ",
					MessageUtils.coloredMessage("&dDate Submitted &r") + date,
					MessageUtils.coloredMessage("&d# of Reports: &r") + reportsAmount,
					MessageUtils.coloredMessage("&dReport Reason: &r") + reason));
			item.setItemMeta(meta);
			reports.addItem(item);

		}
		
		if(section.getKeys(false).size() <= 36){
			ItemStack nextPage = new ItemStack(Material.STAINED_GLASS_PANE);
			reports.setItem(49, nextPage);
		}
		
		player.openInventory(reports);
	}

}
