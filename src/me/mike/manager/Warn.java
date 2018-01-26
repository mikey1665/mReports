package me.mike.manager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.mike.Main;
import me.mike.utils.MessageUtils;

public class Warn {
	
	public static Main plugin = Main.getPlugin(Main.class);
	
	private OfflinePlayer player;
	private String reason;
	
	private int maxWarns = 3;
	
	public Warn(OfflinePlayer player,String reason) {
		this.player = player;
		this.reason = reason;
		warn(player, reason);
	}
	
	public void warn(OfflinePlayer player, String reason) {
		if(plugin.getReports().getData().contains("ActiveReports." + player.getUniqueId())) {
			int numOfWarns = plugin.getReports().getData().getInt("ActiveReports." + player.getUniqueId() + ".Warns");
			plugin.getReports().getData().set("ActiveReports." + player.getUniqueId() + ".Warns", numOfWarns + 1);
			plugin.getReports().saveData();
			Bukkit.getServer().broadcastMessage(MessageUtils.coloredMessage("&c&lWARN &r&d: &a" + player.getName() + " &r&dhas been warned for " + reason));
			Player p = Bukkit.getServer().getPlayer(player.getName());
			if(p!=null) {
				int numUntil = maxWarns - numOfWarns;
				p.sendMessage(MessageUtils.coloredMessage("&4You have been warned! You have &d" + numUntil + " warnings! "));
			}
		} else {
			//TODO: Gen7erate the player in the config.
		}
	}

}
