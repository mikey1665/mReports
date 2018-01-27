package me.mike.menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
	 * TODO: Inventory or Class for ReportAction ReportAction will handle banning,
	 * kicking, and warning.
	 * 
	 */

	private static int amountOfReasons = 3;

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
			emptyMeta.setLore(Arrays.asList("", MessageUtils.coloredMessage("&eWoohoo!"),
					MessageUtils.coloredMessage("&eThere's nothing to worry about.")));
			empty.setItemMeta(emptyMeta);
			reports.setItem(4, empty);
		} else {
			for (String s : section.getKeys(false)) {
				ConfigurationSection cs2 = section.getConfigurationSection(s);
				String reportedPlayers = cs2.getName().toString();
				OfflinePlayer target = Bukkit.getOfflinePlayer(reportedPlayers);

				String date = plugin.getReports().getData()
						.getString("ActiveReports." + reportedPlayers + ".Submitted");
				int reportsAmount = plugin.getReports().getData()
						.getInt("ActiveReports." + reportedPlayers + ".Reports");

				List<String> reasonsList = plugin.getReports().getData()
						.getStringList("ActiveReports." + target.getName() + ".Reasons");
				String[] reasons = reasonsList.toArray(new String[0]);
				

				String[] colorNode = new String[] {"&f", "&e", "&c", "&4"} ;

				List<String> lore = new ArrayList<String>();
				lore.add(" ");
				lore.add(MessageUtils.coloredMessage("&dDate Submitted &r") + date);
				lore.add(MessageUtils.coloredMessage("&d# of Reports: " + colorNode[determineColor(reportsAmount)]) + reportsAmount);
				lore.add(MessageUtils.coloredMessage("&6(3) &dReport Reasons: &r"));

				for (int i = 0; i < amountOfReasons; i++) {
					if (reasons[i] == null)
						break;
					lore.add(MessageUtils.coloredMessage("&8&l- &f") + reasons[i]);
				}

				String onlineOffline = isOnline(UUID.fromString(target.getName())) ? "&aOnline" : "&cOffline";
				lore.add(MessageUtils.coloredMessage("&dPlayer Status: &r" + onlineOffline));
				lore.add("");

				meta.setDisplayName(ChatColor.YELLOW + target.getName());
				meta.setLore(lore);
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

	public static boolean isOnline(UUID uuid) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.getUniqueId().equals(uuid))
				return true;
		}
		return false;
	}
	
	public static int determineColor(int reportsAmount) {
		int colorNumber = 0;
		if (reportsAmount >= 1 && reportsAmount <= 3) {
			 return 0;
		} else if (reportsAmount >= 4 && reportsAmount <= 6) {
			return 1;
		} else if (reportsAmount >= 7 && reportsAmount <= 9) {
			return 2;
		} else if (reportsAmount >= 10) {
			return 3;
		}
		return 0;
	}

}
