/*
 * --------------------------------------------------------------------------------------------------------------
 * Copyright 2014, Laboratory of Internet Computing (LInC), Department of Computer Science, University of Cyprus
 * 
 * For any information relevant to Cloud Information System,
 * please contact Athanasios Foudoulis,  afoudo01{at}cs.ucy.ac.cy
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * --------------------------------------------------------------------------------------------------------------
 */
package eu.celarcloud.cloud_is.dataCollectionModule.common.beans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 */
public class Application implements IBean{
	
	/** The description. */
	public String id, description;
	
	/** The v major. */
	public String  uid, vMinor, vMajor;
	
	/** The topology. */
	public String topology;
	
	/**
	 * Time in unix time stamp format (milliseconds since Jan 1, 1970)
	 * The submitted. 
	 *
	 */
	public String submitted;
	 
	 /**
	 * Instantiates a new application.
	 */
 	public Application(){
	        
	 }

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.beans.IBean#toJSONObject()
	 */
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
