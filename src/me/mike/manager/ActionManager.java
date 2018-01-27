package me.mike.manager;

import java.util.UUID;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ActionManager {
	
	public enum ActionType{
		BAN(Material.DIAMOND_AXE, "You have been banned!"),
		KICK(Material.WOOD_AXE, "You have been kicked!"),
		WARN(Material.PAPER, "You have been warned!");
		
		private final Material icon;
		private final String dReason;
		
		ActionType(Material icon, String defaultKick){
			this.icon = icon;
			this.dReason = defaultKick;
		}
		
		public Material getMaterial() {
			return icon;
		}
		
		public String getDefaultReason() {
			return dReason;
		}
	}
	
	/*
	 * Class: ActionManager
	 * Parameters: (UUID, ActionType, Reason) (UUID, ActionType)
	 */
	
	private OfflinePlayer oPlayer;
	private ActionType aType;
	private String reason;
	
	public ActionManager(OfflinePlayer player, ActionType type, String reason) {
		this.oPlayer = player;
		this.aType = type;
		this.reason = reason;
		handleAction(oPlayer, aType, reason);
		
	}
	
	public ActionManager(OfflinePlayer player, ActionType type) {
		this.oPlayer = player;
		this.aType = type;
		this.reason = type.getDefaultReason();
		handleAction(oPlayer, aType, reason);
	}
	
	public void handleAction(OfflinePlayer player, ActionType type, String reason) {
		Player p = (Player) Bukkit.getServer().getOfflinePlayer(player.getUniqueId());
		
		if(type == ActionType.BAN) {
			if(p!=null) {
				//If the player exceeds over 3 warnings, execute a 1 Day ban.
				Bukkit.getBanList(Type.NAME).addBan(p.getName(), reason, /*Calculate 1 Day*/null, "mReports");
			}
		}else if(type == ActionType.KICK) {
			if(p!=null) {
				p.kickPlayer(reason);
			}
		}else if(type == ActionType.WARN) {
			new Warn(player, reason);
		}
	}
	
	public UUID getUUID() {
		return oPlayer.getUniqueId();
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public ActionType getAction() {
		return aType;
	}
}
