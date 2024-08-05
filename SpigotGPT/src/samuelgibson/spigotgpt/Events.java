package samuelgibson.spigotgpt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Events implements Listener {
	@EventHandler
	void onPlayerMove(PlayerMoveEvent e) {
        
	}
	
	@EventHandler
	void onPlayerChat( AsyncPlayerChatEvent e) {
		e.getMessage();
		BukkitTask chatTask = Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
		    ChatQueue.sendMessage(e.getPlayer(), e.getMessage());
		});
         	
	}
}