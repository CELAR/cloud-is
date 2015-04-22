package eu.celarcloud.cloud_is.dataCollectionModule.common.beans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Application implements IBean{
	public String id, description;
	public String  uid, vMinor, vMajor;
	public String topology;
	// Time in unix time stamp format (milliseconds since Jan 1, 1970)
	public String submitted;
	 
	 public Application(){
	        
	 }

	@Override
	public JSONObject toJSONObject() {
		JSONObject application;
		application = new JSONObject();
			application.put("id", this.id);
			application.put("uid", this.uid);
			application.put("vMinor", this.vMinor);
			application.put("vMajor", this.vMajor);
	    	application.put("description", description);
	    	application.put("sTime", this.submitted); 
	    	
	    	try {
	    		JSONArray t = new JSONArray(this.topology);
	    		application.put("topology", t); 
	    	} 
	    	catch (JSONException e) {
	    		application.put("topology", this.topology); 
	    	}
	    	catch(NullPointerException e) {
	    		// Check the case that topology is not present
	    		application.put("topology", ""); 
	    	}
	    	
		return application;
	}	 
}
