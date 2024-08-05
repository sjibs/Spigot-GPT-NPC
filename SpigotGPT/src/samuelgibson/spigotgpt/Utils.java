package samuelgibson.spigotgpt;

import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class Utils {
	public static JsonObject objectToJSONObject(String object){
		Gson gson = new Gson();
		return gson.fromJson(object, JsonObject.class);
	}

	public static JsonArray objectToJSONArray(String object){
		Gson gson = new Gson();
		return gson.fromJson(object, JsonArray.class);
	}
	
	
	//TODO: remove for public plugin
	public static String realNameResolver(Player p) {
		switch(p.getUniqueId().toString()) {
		case "d08a63b7-ec06-4808-805d-ba187837bc15":
			return "Michael";
		case "c721ebd8-7292-4fdb-8e6e-48f35021a590":
			return "Sam";
		case "15a199b1-3aab-4284-a923-416fcf6a6fc4":
			return "James";
		case "893e7746-f66a-43f7-ac75-64364b3cbcc1":
			return "Harry";
		case "ffc6879d-713e-45bc-981c-da6fe044781e":
			return "Alexa";
		}
		return "A suspicious man of unknown origins who clearly doesn't belong";
	}
	
	//TODO: remove for public plugin
		public static String nameUUIDResolver(Player p) {
			switch(p.getUniqueId().toString()) {
			case "Michael":
				return "d08a63b7-ec06-4808-805d-ba187837bc15";
			case "Sam":
				return "c721ebd8-7292-4fdb-8e6e-48f35021a590";
			case "James":
				return "15a199b1-3aab-4284-a923-416fcf6a6fc4";
			case "Harry":
				return "893e7746-f66a-43f7-ac75-64364b3cbcc1";
			case "Alexa":
				return "ffc6879d-713e-45bc-981c-da6fe044781e";
			}
			return "15a199b1-3aab-4284-a923-416fcf6a6fc4";
		}

}
