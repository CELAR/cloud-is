package eu.celarcloud.cloud_is.dataCollectionModule.common.beans;

import java.util.Date;

import org.json.JSONObject;

public class Application implements IBean{
	public String id, description;
	public String topology;
	public Date submitted;
	 
	 public Application(){
	        
	 }

	@Override
	public JSONObject toJSONObject() {
		JSONObject application;
		application = new JSONObject();
			application.put("id", this.id);
	    	application.put("description", description);
	    	application.put("sTime", this.submitted); 
		return application;
	}	 
}
