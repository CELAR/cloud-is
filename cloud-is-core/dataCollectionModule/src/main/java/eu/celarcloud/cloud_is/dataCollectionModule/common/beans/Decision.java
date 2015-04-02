package eu.celarcloud.cloud_is.dataCollectionModule.common.beans;

import java.util.HashMap;

import org.json.JSONObject;

public class Decision implements IBean {
	public String timestamp;
	public String type;
	
	 public Decision() {
	        
	 }
	 
	 public Decision(String timestamp, String type) {
	        this.timestamp = timestamp;
	        this.type = type;
	 }
	
	@Override
	public JSONObject toJSONObject() {
		JSONObject decision;
		decision = new JSONObject();
			decision.put("timestamp", this.timestamp);
			decision.put("type", this.type);
		return decision;
	}
	
	public static HashMap<String, String> toHashMap()
	{
		return null;		
	}
}
