package samuelgibson.spigotgpt;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

//https://platform.openai.com/docs/api-reference/assistants/createAssistant

public class ChatGPTAssistant {
	public ChatGPTAssistant() {
		
	}
	
	public Builder getDefaultBuilder() {
		return HttpRequest.newBuilder()
				  .header("Content-Type", "application/json")
				  .header("Authorization", "Bearer "+Main.config.getAPIKey())
				  .header("OpenAI-Beta", "assistants=v2");
				  //.header("OpenAI-Project", PROJECT_KEY)
		
	}
	
	
	/** This one is used**/
	public HttpResponse<String> createAndRunThread( String message, String assistantID, String username) throws IOException, URISyntaxException, InterruptedException {
		String url = String.format("https://api.openai.com/v1/threads/runs");
		String body = String.format("{\"assistant_id\": \"%s\",\"stream\": true,\"thread\": {\"messages\": [ {\"role\": \"user\", \"content\": \"%s\"}]},\"metadata\": {\"user_id\": \"%s\"}}", assistantID,message, username);
		
		HttpClient httpClient = HttpClient.newHttpClient();

		HttpRequest request = getDefaultBuilder()
		  .POST(BodyPublishers.ofString(body))
		  .uri(new URI(url)).build();

		return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	}
	
	
	
	/** This one is used**/
	public HttpResponse<String> sendMessage(String threadID ,String message) throws IOException, URISyntaxException, InterruptedException {
		String url = String.format("https://api.openai.com/v1/threads/%s/messages", threadID);
		String body = String.format("{\"role\": \"user\", \"content\": \"%s\"}", message);
		
		HttpClient httpClient = HttpClient.newHttpClient();

		HttpRequest request = getDefaultBuilder()
		  .POST(BodyPublishers.ofString(body))
		  .uri(new URI(url)).build();

		return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	}
	
	/** This one is used**/
	public HttpResponse<String> runThread(String threadID, String assistantID, String username) throws IOException, URISyntaxException, InterruptedException {
		String url = String.format("https://api.openai.com/v1/threads/%s/runs", threadID);
		String body = String.format("{\"assistant_id\": \"%s\",\"stream\": true,\"metadata\": {\"user_id\": \"%s\"}}", assistantID, username);
		
		HttpClient httpClient = HttpClient.newHttpClient();

		HttpRequest request = getDefaultBuilder()
		  .POST(BodyPublishers.ofString(body))
		  .uri(new URI(url)).build();

		return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	}
	
}
