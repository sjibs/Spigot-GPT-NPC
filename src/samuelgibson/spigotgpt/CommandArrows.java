package samuelgibson.spigotgpt;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandArrows implements CommandExecutor {
	static HashMap<String,Integer> scores = new HashMap<String,Integer>();
	static HashMap<String, Double> lastLocation = new HashMap<String,Double>();
	static boolean inGame = false;
	static String archeryPrefix = "§l§cArchery §8»§r§7 ";
	public static void addScore(Player p, int score, Location l) {
		if(inGame) {
			
			
			
			if(!lastLocation.containsKey(p.getDisplayName())) {
				lastLocation.put(p.getDisplayName(), l.getZ());
				Bukkit.broadcastMessage(archeryPrefix+"§d"+ p.getDisplayName() + "§7 hit a target and scored §a"+ score+ "§7 point"+ (score>1?"s!":"!"));
				if(scores.containsKey(p.getDisplayName())) {
					scores.put(p.getDisplayName(), score + scores.get(p.getDisplayName()));
				}else {
					scores.put(p.getDisplayName(),score);
				}
			}else {
				if(lastLocation.get(p.getDisplayName()).equals(l.getZ())) {
					p.sendMessage(archeryPrefix+"You cannot hit the same target twice in a row!");
				}else {
					Bukkit.broadcastMessage(archeryPrefix+"§d"+ p.getDisplayName() + "§7 hit a target and scored §a"+ score+ "§7 point"+ (score>1?"s!":"!"));
					if(scores.containsKey(p.getDisplayName())) {
						scores.put(p.getDisplayName(), score + scores.get(p.getDisplayName()));
					}else {
						scores.put(p.getDisplayName(),score);
					}
				}
				lastLocation.put(p.getDisplayName(),l.getZ());
			}
		}
	}
	
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length==0) {return false;}
       switch(args[0]) {
       case "restart":
    	   Bukkit.broadcastMessage(archeryPrefix+"The game has been restarted!");
    	   scores = new HashMap<String,Integer>();
    	   lastLocation = new HashMap<String,Double>();
    	   break;
       case "start":
    	   if(!inGame) {
    		   Bukkit.broadcastMessage(archeryPrefix+"An archery game has been started!");
    	   		inGame= true;
    	   }else {
        	   Bukkit.broadcastMessage(archeryPrefix+"There is already a game in progress!");
    	   }
    	   break;
       case "stop":
    	   if(inGame) {
    		   inGame= false;
        	   Bukkit.broadcastMessage(archeryPrefix+"The game has concluded!");
        	   displayScoreboard();
        	   scores = new HashMap<String,Integer>();
        	   lastLocation = new HashMap<String,Double>();
    	   }else {
    		   sender.sendMessage(archeryPrefix + "There is no game active.");
    	   }
    	   
    	   break;
       case "scoreboard":
    	   displayScoreboard();
    	   break;
	   case "jd":
    	   Player scy = Bukkit.getServer().getPlayer(args[1]);
    	   scy.dropItem(true);
    	default: return false; 
    	   

       }
       return true;
    }


	private void displayScoreboard() {

		int i = scores.size();
		for(String k : scores.keySet()) {
			Bukkit.broadcastMessage(archeryPrefix+"§d"+k + "§7 scored §a"+ scores.get(k)+ "§7 point"+ (scores.get(k)>1?"s!":"!"));
		}
		
	}
}
