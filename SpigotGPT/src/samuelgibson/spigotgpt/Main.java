package samuelgibson.spigotgpt;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
    // Fired when plugin is first enabled
	public static JavaPlugin plugin;
	public static Configuration config;
    @Override
    public void onEnable() {
    	plugin = this;
    	FileConfiguration f = this.getConfig();
    	if(f==null) {
    		this.saveDefaultConfig();
    	}
    	config = new Configuration(f);
    	Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
}
