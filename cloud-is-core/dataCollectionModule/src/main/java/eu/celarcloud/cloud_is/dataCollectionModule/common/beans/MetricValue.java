package eu.celarcloud.cloud_is.dataCollectionModule.common.beans;

import java.util.HashMap;

import org.json.JSONObject;

public class MetricValue implements IBean {
	public String timestamp;
	public String value;
	
	 public MetricValue() {
	        
	 }
	 
	 public MetricValue(String timestamp, String value) {
	        this.timestamp = timestamp;
	        this.value = value;
	 }
	
	@Override
	public JSONObject toJSONObject() {
		JSONObject metric;
		metric = new JSONObject();
			metric.put("timestamp", this.timestamp);
	    	metric.put("value", this.value);
		return metric;
	}
	
	public static HashMap<String, String> toHashMap()
	{
		return null;		
	}
}
