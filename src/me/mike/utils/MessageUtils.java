package me.mike.utils;

import org.bukkit.ChatColor;
import org.bukkit.util.ChatPaginator;

public class MessageUtils {
	
	public static String coloredMessage(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static String centerMessage(String s) {
		String FINAL = "";
		int lineWidth = ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH;
		int stringWidth = s.length();
		int spacedToSide = lineWidth - stringWidth;
		int divided = spacedToSide / 2;
		for(int i = 0; i < divided; i++) {
			FINAL = FINAL + " ";
		}
		FINAL = FINAL + s;
		for(int i = 0; i < divided; i++) {
			FINAL = FINAL + " ";
		}
		return FINAL;
	}
}
