package samuelgibson.spigotgpt;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RunReturnObject {
	
	private String functionName;
	private String functionParameters;
	private String text;
	private String threadID;
	private String runID;
	private boolean requiresAction=false;
	
	RunReturnObject(String run)  {
		String returnString = "";
		String functionString = "";
		boolean deltaNextLine = false;
		boolean functionNextLine = false;

		for (String line : run.split("\n")) {
			if (deltaNextLine) {
				deltaNextLine = false;
				line = line.replace("data: ", "");
				JsonObject json = Utils.objectToJSONObject(line);
				if (functionNextLine==false) {
					setThreadID(json.get("thread_id").getAsString());
					setRunID(json.get("run_id").getAsString());
					JsonArray content = json.get("content").getAsJsonArray();
					for (int j = 0; j < content.size(); j++) {
						JsonObject c = content.get(j).getAsJsonObject();
						returnString += c.get("text").getAsJsonObject().get("value").getAsString();
					}
				}else {
					JsonArray content = json.get("required_action").getAsJsonObject().get("submit_tool_outputs").getAsJsonObject().get("tool_calls").getAsJsonArray();
					for (int j = 0; j < content.size(); j++) {
						if(j>0 ) functionString += ", ";
						JsonObject f = content.get(j).getAsJsonObject().get("function").getAsJsonObject();
						functionString += "{" +f.get("name").getAsString() + ":";
						if(f.has("arguments")) {
							functionString +=f.get("arguments").getAsString()+ "}";
						}else {
							functionString +="{}}";
						}
					}
				}
			} else if (line.equals("event: thread.message.completed")) {
				deltaNextLine = true;
				functionNextLine = false;
			} else if (line.equals("event: thread.run.requires_action")) {
				deltaNextLine = true;
				functionNextLine = true;
				this.setRequiresAction(true);
			}
		}
		
		functionString = "["+ functionString +"]";
		
		this.setFunctionParameters(functionString);
		this.setText(returnString);
	}
	
	public void resolveFunctions() {
		JsonArray json = Utils.objectToJSONArray(this.getFunctionParameters());
		for (int j = 0; j < json.size(); j++) {
			//resolve each function
		}
	}

	public String getFunctionParameters() {
		return functionParameters;
	}

	public void setFunctionParameters(String functionParameters) {
		this.functionParameters = functionParameters;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRequiresAction() {
		return requiresAction;
	}

	public void setRequiresAction(boolean requiresAction) {
		this.requiresAction = requiresAction;
	}

	public String getThreadID() {
		return threadID;
	}

	public void setThreadID(String threadID) {
		this.threadID = threadID;
	}

	public String getRunID() {
		return runID;
	}

	public void setRunID(String runID) {
		this.runID = runID;
	}
}
