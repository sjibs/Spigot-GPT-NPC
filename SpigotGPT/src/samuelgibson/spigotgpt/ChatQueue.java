package samuelgibson.spigotgpt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import com.google.gson.JsonArray;

public class ChatQueue {
	public static HashMap<String, String> playerThreads = new HashMap<String, String>();
	public static HashSet<String> conversations = new HashSet<String>();
	public static HashSet<String> conversationsInRequest = new HashSet<String>();

	public static void sendMessage(Player p, Character c, String message) {
		if (conversationsInRequest.contains(p.getUniqueId().toString() +  c.getAssistant_id()))
			return;
		conversationsInRequest.add(p.getUniqueId().toString() +  c.getAssistant_id());

		String realName=   Utils.realNameResolver(p);
		if (isMessaging(p, c.getAssistant_id())) {

		} else {
			// TODO: if memories exist load,
			// TODO: if no memories exist, introduce player as a person you've never met
			// before
			conversations.add(p.getUniqueId().toString() +  c.getAssistant_id());
			message += realName + " has initiated a conversation with you";

		}
		message = Utils.realNameResolver(p) + " says \\\"" + message + "\\\"";

		try {
			if (playerThreads.containsKey(p.getUniqueId().toString() +  c.getAssistant_id())) {
				String threadID = playerThreads.get(p.getUniqueId().toString() +  c.getAssistant_id());
				ChatGPTAssistant.sendMessage(threadID, message).body();
				String string = ChatGPTAssistant.runThread(threadID,  c.getAssistant_id(), realName).body();
				RunReturnObject run = new RunReturnObject(string);
				if (run.isRequiresAction()) {
					handleActions(run,c);
				} else {
					String[] messages = run.getText().split("\n");
					for(String s: messages) {
						if(!s.equals("")) {
							Bukkit.broadcastMessage(ChatColor.GRAY + "<" + c.getChatName()+ ChatColor.GRAY +"> " + s);
							Thread.sleep((long) (Math.random()*3000 + 1000));
						}
					}
				}
			} else {
				String string = ChatGPTAssistant.createAndRunThread(message,  c.getAssistant_id(), realName).body();
				RunReturnObject run = new RunReturnObject(string);
				Bukkit.broadcastMessage(ChatColor.GRAY + "<" + c.getChatName()+ ChatColor.GRAY +"> " + run.getText());
				playerThreads.put(p.getUniqueId().toString() +  c.getAssistant_id(), run.getThreadID());
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
		conversationsInRequest.remove(p.getUniqueId().toString() +  c.getAssistant_id());
	}

	public static void handleActions(RunReturnObject run, Character c) throws URISyntaxException, IOException, InterruptedException {
		JsonArray json = Utils.objectToJSONArray(run.getFunctionParameters());
		String executeString = "";
		for (int j = 0; j < json.size(); j++) {
			
			if(j>0 ) executeString += ", ";
			List<String> keys = json.get(j).getAsJsonObject().entrySet().stream().map(i -> i.getKey())
					.collect(Collectors.toCollection(ArrayList::new));
			executeString += resolveFunction(keys.get(0),keys.get(1),
					json.get(j).getAsJsonObject().get(keys.get(1)).getAsJsonObject().get("person").getAsString());
		}
		
		String string = ChatGPTAssistant.resolveRun(run.getRunID(), run.getThreadID(), executeString).body();
		Bukkit.broadcastMessage(ChatColor.AQUA + string);
		RunReturnObject runreturnObject = new RunReturnObject(string);
		Bukkit.broadcastMessage(ChatColor.GRAY + "<" + ChatColor.YELLOW + "Mayor Humphrey"+ ChatColor.GRAY +"> " + run.getText());
	}

	public static String resolveFunction(String callID, String function, String user) {
		Bukkit.broadcastMessage(ChatColor.RED + "[Action Required] " + function + user);
		UUID playerUUID = UUID.fromString(Utils.nameUUIDResolver(user));
		Player p = Bukkit.getPlayer(playerUUID);
		if (p==null || !p.isOnline()) {
			return "{tool_call_id:"+callID+", failure: james is offline}";
		} 
		String returnString = "";
		double h =  p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
		switch (function) {
		case "kill":
			p.setHealth(0);
			return "{tool_call_id:"+callID+", success: true}";
		case "punish":
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Math.max(h-1, 1));
			return "{tool_call_id:"+callID+", success: true}";
		case "reward":
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Math.min(h+1, 20));
			return "{tool_call_id:"+callID+", success: true}";
		case "jail":
			p.teleport(new Location(Bukkit.getWorld("world"),-394, 67, -191));
			return "{tool_call_id:"+callID+", success: true}";
		}
		return returnString;
	}

	public static void endConversation(Player p, String assistantID) {
		conversations.remove(p.getUniqueId().toString() + assistantID);
	}

	public static boolean isMessaging(Player player, String assistantID) {
		if (conversations.contains(player.getUniqueId().toString() + assistantID))
			return true;
		return false;
	}
}
