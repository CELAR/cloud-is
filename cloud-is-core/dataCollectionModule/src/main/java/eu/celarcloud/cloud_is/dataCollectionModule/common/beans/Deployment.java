package eu.celarcloud.cloud_is.dataCollectionModule.common.beans;

import java.util.Date;

import org.json.JSONObject;

public class Deployment implements IBean{
	 public String applicationId;
	 public String id;
	 public String status;
	 public Date startTime, endTime;
	 
	 public Deployment(){
	        
	 }

	@Override
	public JSONObject toJSONObject() {
		JSONObject deployment;
    	deployment = new JSONObject();
	    	deployment.put("id", this.id);
	    	deployment.put("version", "123456");
	    	deployment.put("application", this.applicationId);
	    	deployment.put("status", this.status); 
	    	deployment.put("sTime", this.startTime); 
	    	deployment.put("eTime", this.endTime); 
		return deployment;
	}	 
}
