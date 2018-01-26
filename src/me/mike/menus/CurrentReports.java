package me.mike.menus;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mike.Main;
import me.mike.utils.MessageUtils;

public class CurrentReports {

	/*
	 * TODO: Inventory or Class for ReportAction ReportAction will
	 * handle banning, kicking, and warning.
	 * 
	 * TODO: Be able to view the top 3 reasons.
	 * 
	 */

	public static Main plugin = Main.getPlugin(Main.class);

	public static void createReportInventory(Player player) {
		Inventory reports = Bukkit.createInventory(null, 9 * 5, MessageUtils.coloredMessage("&aActive Reports: "));

		// Report Item
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		ConfigurationSection section = plugin.getReports().getData().getConfigurationSection("ActiveReports");

		// Fix inventory if there is 0 reports
		if (section == null) {
			ItemStack empty = new ItemStack(Material.PAPER);
			ItemMeta emptyMeta = empty.getItemMeta();

			emptyMeta.setDisplayName(MessageUtils.coloredMessage("&a&lNo Reports!"));
			emptyMeta.setLore(
					Arrays.asList("", MessageUtils.coloredMessage("&eWoohoo!"),
							MessageUtils.coloredMessage("&eThere's nothing to worry about.")));
			empty.setItemMeta(emptyMeta);
			reports.setItem(4, empty);
		} else {
			for (String s : section.getKeys(false)) {
				ConfigurationSection cs2 = section.getConfigurationSection(s);
				String reportedPlayers = cs2.getName().toString();
				OfflinePlayer target = Bukkit.getOfflinePlayer(reportedPlayers);

				String reason = plugin.getReports().getData()
						.getString("ActiveReports." + reportedPlayers + ".Reason");
				String date = plugin.getReports().getData()
						.getString("ActiveReports." + reportedPlayers + ".Submitted");
				int reportsAmount = plugin.getReports().getData()
						.getInt("ActiveReports." + reportedPlayers + ".Reports");

				// Determine Color
				String colorNode = "";
				if(reportsAmount >= 1 && reportsAmount <= 3) {
					colorNode = "&f";
				} else if (reportsAmount >= 4 && reportsAmount <= 6) {
					colorNode = "&e";
				} else if (reportsAmount >= 7 && reportsAmount <= 9) {
					colorNode = "&c";
				} else if(reportsAmount >= 10){
					colorNode = "&4";
				}

				meta.setDisplayName(ChatColor.YELLOW + target.getName());
				meta.setLore(Arrays.asList(" ", 
						MessageUtils.coloredMessage("&dDate Submitted &r") + date,
						MessageUtils.coloredMessage("&d# of Reports: " + colorNode) + reportsAmount,
						MessageUtils.coloredMessage("&dReport Reason: &r") + reason));
				item.setItemMeta(meta);
				reports.addItem(item);
			}
		}

		// Clear All
		ItemStack clearAll = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta clear = clearAll.getItemMeta();
		clear.setDisplayName(MessageUtils.coloredMessage("&cClear All"));
		clear.setLore(Arrays.asList(" ", MessageUtils.coloredMessage("&eClear all active reports.")));
		clearAll.setItemMeta(clear);
		reports.setItem(40, clearAll);

		// Glass pane
		ItemStack grayPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta pane = grayPane.getItemMeta();
		pane.setDisplayName(" ");
		pane.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		grayPane.setItemMeta(pane);
		reports.setItem(44, grayPane);
		reports.setItem(43, grayPane);
		reports.setItem(42, grayPane);
		reports.setItem(41, grayPane);
		reports.setItem(39, grayPane);
		reports.setItem(38, grayPane);
		reports.setItem(37, grayPane);
		reports.setItem(36, grayPane);

		player.openInventory(reports);
	}

}
