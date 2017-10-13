package me.mike.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.mike.Main;

public class ReportManager {
	
	private Main plugin = Main.getPlugin(Main.class);

	String configName = "reportData.yml";

	public FileConfiguration reportData;
	public File reportFile;

	public void setup() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		reportFile = new File(plugin.getDataFolder(), configName);
		if (!reportFile.exists()) {
			try {
				reportFile.createNewFile();
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GREEN + configName + " successfully loaded!");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
				.sendMessage(ChatColor.RED + configName + " could not be loaded!");
			}
		}
		reportData = YamlConfiguration.loadConfiguration(reportFile);
	}

	public void saveData() {
		try {
			reportData.save(reportFile);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + configName + " successfully saved!");
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + configName + " could not be saved!");
		}
	}

	public void reloadData() {
		reportData = YamlConfiguration.loadConfiguration(reportFile);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + configName + " has been reloaded!");
	}

	public FileConfiguration getData() {
		return reportData;
	}

}
