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
		BukkitTask chatTask = Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
		for(Character c : Main.characters){
			if(ChatQueue.isMessaging(e.getPlayer(),c.getAssistant_id())) {
				if(e.getPlayer().getLocation().distance(c.getLocation())<5) {
					ChatQueue.sendMessage(e.getPlayer(),c, e.getMessage());
				}else {
					ChatQueue.conversations.remove(e.getPlayer().getUniqueId().toString()+c.getAssistant_id());
				}
			}else if(e.getMessage().toLowerCase().contains(c.getTriggerName())) {
				if(e.getPlayer().getLocation().distance(c.getLocation())<5) {
				    ChatQueue.sendMessage(e.getPlayer(),c, e.getMessage());
				}
			}
		}
		});
	}
	@EventHandler
	void onPlayerChat( PlayerMoveEvent e) {
		
	}
	
}