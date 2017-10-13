package me.mike;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.mike.commands.ReportCommand;
import me.mike.commands.ViewReportsCommand;
import me.mike.files.ReportManager;

public class Main extends JavaPlugin{
	
	
	public static ReportManager reportFile;
	
	@Override
	public void onEnable() {
		getCommand("report").setExecutor(new ReportCommand());
		getCommand("viewreports").setExecutor(new ViewReportsCommand());

		loadReportManager();
		getServer().getConsoleSender().sendMessage("[mReports] " + ChatColor.GREEN + "Enabled!");
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage("[mReports] " + ChatColor.RED + "Disabled!");
	}
	
	public void loadReportManager() {
		reportFile = new ReportManager();
		reportFile.setup();
		reportFile.saveData();
		reportFile.reloadData();
	}
	
	public ReportManager getReports() {
		return reportFile;
	}
}
