package samuelgibson.spigotgpt;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Character {
	private Location location;
	private String name;
	private String assistant_id;
	private String triggerName;
	private String chatName;
	
	
	
	public Character(Location location, String name, String assistant_id, String triggerName, String chatName) {
		super();
		this.location = location;
		this.name = name;
		this.assistant_id = assistant_id;
		this.setTriggerName(triggerName);
		this.chatName= chatName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getAssistant_id() {
		return assistant_id;
	}
	public void setAssistant_id(String assistant_id) {
		this.assistant_id = assistant_id;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getChatName() {
		return this.chatName;
	}
}
