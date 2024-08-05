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
		if(ChatQueue.isMessaging(e.getPlayer(),"asst_xvz3t3S3C9icku2WYHcc7aE3")) {
			BukkitTask chatTask = Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
			    ChatQueue.sendMessage(e.getPlayer(),"asst_xvz3t3S3C9icku2WYHcc7aE3", e.getMessage());
			});
		}else if(e.getMessage().toLowerCase().contains("humphrey") || e.getMessage().toLowerCase().contains("mayor")) {
			BukkitTask chatTask = Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
			    ChatQueue.sendMessage(e.getPlayer(),"asst_xvz3t3S3C9icku2WYHcc7aE3", e.getMessage());
			});
		}
		
         	
	}
}