package samuelgibson.spigotgpt;

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

}
