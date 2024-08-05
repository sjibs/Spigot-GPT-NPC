package samuelgibson.spigotgpt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatQueue {
	public static HashMap<String, String> playerThreads = new HashMap<String,String>();
	public static HashSet<String> conversations = new HashSet<String>();
	public static HashSet<String> conversationsInRequest = new HashSet<String>();
	
	public static void sendMessage(Player p,String assistantID, String message) {
		if(conversationsInRequest.contains(p.getUniqueId().toString()+assistantID)) return;
		conversationsInRequest.add(p.getUniqueId().toString() + assistantID);
		
		if(isMessaging(p,assistantID)) {
			
		}else {
			conversations.add(p.getUniqueId().toString() + assistantID);
		}
		
		
		try {
		if(playerThreads.containsKey(p.getUniqueId().toString()+assistantID)) {
			ChatGPTAssistant chatGPTAssistant = new ChatGPTAssistant();
			String threadID= playerThreads.get(p.getUniqueId().toString()+assistantID);
			chatGPTAssistant.sendMessage(threadID, message).body();
			String string = chatGPTAssistant.runThread(threadID, assistantID, "Samuel").body();
			RunReturnObject run = new RunReturnObject(string);
			if(run.isRequiresAction()) {
				Bukkit.broadcastMessage(ChatColor.RED+"[Action Required]");
				Bukkit.broadcastMessage(run.getFunctionParameters());
			}
			Bukkit.broadcastMessage(ChatColor.YELLOW+"<Mayor Humphrey> "+run.getText());
		}else {
			ChatGPTAssistant chatGPTAssistant = new ChatGPTAssistant();
			String string = chatGPTAssistant.createAndRunThread(message,assistantID, "Samuel").body();
			RunReturnObject run = new RunReturnObject(string);
			Bukkit.broadcastMessage(ChatColor.YELLOW+"<Mayor Humphrey> "+run.getText());
			playerThreads.put(p.getUniqueId().toString()+assistantID, run.getThreadID());
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conversationsInRequest.remove(p.getUniqueId().toString() + assistantID);
	}
	
	
	public static void handleActions(String actionsJSON) {
		
	}
	
	public static void endConversation(Player p , String assistantID) {
		conversations.remove(p.getUniqueId().toString() + assistantID);
	}

	public static boolean isMessaging(Player player, String assistantID) {
		if(conversations.contains(player.getUniqueId().toString()+assistantID))
		return false;
		return true;
	}
}
