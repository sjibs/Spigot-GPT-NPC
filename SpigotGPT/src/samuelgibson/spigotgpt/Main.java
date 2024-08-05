package samuelgibson.spigotgpt;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
    // Fired when plugin is first enabled
	public static JavaPlugin plugin;
	public static Configuration config;
	public static ArrayList<Character> characters = new ArrayList<Character>();

    @Override
    public void onEnable() {
    	plugin = this;
    	
    	characters.add(new Character(new Location(Bukkit.getWorld("world"), -359, 73, -270), "Mayor Humphrey","asst_xvz3t3S3C9icku2WYHcc7aE3","humphrey", (ChatColor.YELLOW + "Humphrey")));
    	characters.add(new Character(new Location(Bukkit.getWorld("world"), -477, 67, -264), "King Martin II","asst_xtR0u1L4lXLlfvzDJflAnRfv","martin",(ChatColor.GREEN + "Martin III")));
    	characters.add(new Character(new Location(Bukkit.getWorld("world"), -505, 67, -145), "Mathilde Moon","asst_vguS6q1Rb2ovD8ivKiUzSCuh", "mathilde",ChatColor.LIGHT_PURPLE + "Mathilde"));
    	
    	
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
