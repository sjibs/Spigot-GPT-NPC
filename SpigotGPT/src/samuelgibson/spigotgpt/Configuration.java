package samuelgibson.spigotgpt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {
	
	private final String API_KEY;
	private final String PROJECT_KEY;

    public Configuration(FileConfiguration cf) {
		this.API_KEY  = cf.getString("API_KEY");
		this.PROJECT_KEY  = cf.getString("PROJECT_KEY");
		
		ConfigurationSection sec = cf.getConfigurationSection("Characters");
        for(String key : sec.getKeys(false)){
            String name = cf.getString("Characters." + key + ".name");
        }
	}

	public String getAPIKey() {
		return API_KEY;
	}
	
	public String getProjectKey() {
		return PROJECT_KEY;
	}
    
    
    
}
