package me.mike.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mike.Main;
import me.mike.menus.CurrentReports;

public class ViewReportsCommand implements CommandExecutor{
	
	/*
	 * TODO: View an inventory of current reports
	 * TODO: /viewreports
	 * TODO: Only staff can use this command
	 */
	
	public Main plugin = Main.getPlugin(Main.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			if(sender.hasPermission("mreports.view") || sender.isOp()) {
				Player player = (Player) sender;
				CurrentReports.createReportInventory(player);
			}
		}
		return false;
	}
}
