package samuelgibson.spigotgpt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
    // Fired when plugin is first enabled
	public static JavaPlugin plugin;
    @Override
    public void onEnable() {
    	plugin = this;
    	this.getConfig();
    	this.saveDefaultConfig();
    	
    	
    	Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
}
