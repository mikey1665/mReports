package me.mike.commands;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mike.Main;
import me.mike.utils.MessageUtils;

public class ReportCommand implements CommandExecutor{
	
	/*
	 * Report command.
	 * Usage: /report {player} {reason}
	 * 
	 * TODO: add a cooldown so a player can report a player every 10 seconds (prevent spam)
	 */
	
	Main plugin = Main.getPlugin(Main.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				player.sendMessage(MessageUtils.coloredMessage("&cCorrect Usage: /report {player} {reason}"));
				return false;
			} else {
				if(args.length == 1) {
					player.sendMessage(MessageUtils.coloredMessage("&cPlease specify a reason!"));
					return false;
				}
				if(args[0].equalsIgnoreCase(player.getName()) && !player.isOp()){
					player.sendMessage(MessageUtils.coloredMessage("&cYou cannot report yourself!"));
					return false;
				}
				StringBuilder str = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					str.append(args[i] + " ");
				}
				String reason = str.toString();
				
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
				if(target == null) {
					player.sendMessage(MessageUtils.coloredMessage("&cPlayer not found!"));
					return false;
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
				Date currTime= new Date();
				
				
				//We want it so when a player reports the same player, it adds the other reason onto the list.
				
				if(plugin.getReports().getData().contains("ActiveReports." + target.getUniqueId())) {
					
					int numOfReports = plugin.getReports().getData().getInt("ActiveReports." + target.getUniqueId() + ".Reports");
					
					//Get the current List of Strings we have, add another reason onto it.
					List<String> reasons = plugin.getReports().getData().getStringList("ActiveReports." + target.getUniqueId() + ".Reasons");
					reasons.add(reason);
					
					plugin.getReports().getData().set("ActiveReports." + target.getUniqueId() + ".Reasons", reasons);
					plugin.getReports().getData().set("ActiveReports." + target.getUniqueId() + ".Submitted", sdf.format(currTime));
					plugin.getReports().getData().set("ActiveReports." + target.getUniqueId() + ".Reports", numOfReports + 1);
					plugin.getReports().saveData();
				} else {
					plugin.getReports().getData().set("ActiveReports." + target.getUniqueId() + ".Reasons", Arrays.asList(reason));
					plugin.getReports().getData().set("ActiveReports." + target.getUniqueId() + ".Submitted", sdf.format(currTime));
					plugin.getReports().getData().set("ActiveReports." + target.getUniqueId() + ".Reports", 1);
					plugin.getReports().saveData();
				}
				
				player.sendMessage(MessageUtils.centerMessage(MessageUtils.coloredMessage("&b&l=-=-= &6Report &b&l=-=-=")));
				player.sendMessage(MessageUtils.centerMessage(MessageUtils.coloredMessage("&dDate of report: &e" + sdf.format(currTime))));
				player.sendMessage(MessageUtils.centerMessage(MessageUtils.coloredMessage("&aPlayer: &e" + target.getName())));
				player.sendMessage(MessageUtils.centerMessage(MessageUtils.coloredMessage("&aReason: &e" + reason)));
				
				for(Player players : Bukkit.getServer().getOnlinePlayers()){
					if(players.hasPermission("mreports.chatview") || player.isOp()){
						players.sendMessage(MessageUtils.coloredMessage("&4&l!! &r&cA new report has been submitted!"));
					}
				}
			}
		}
		return false;
	}

}
