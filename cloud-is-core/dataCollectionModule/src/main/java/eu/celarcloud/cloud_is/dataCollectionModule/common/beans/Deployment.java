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

import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class Deployment.
 */
public class Deployment implements IBean{
	 
 	/** The application id. */
 	public String applicationId;
	 
 	/** The id. */
 	public String id;
	 
 	/** The status. */
 	public String status;
	// Time in unix time stamp format (milliseconds since Jan 1, 1970)
	 /** The end time. */
	public String startTime, endTime;
	 
	 /**
	 * Instantiates a new deployment.
	 */
 	public Deployment(){
	        
	 }

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.beans.IBean#toJSONObject()
	 */
	@Override
	public JSONObject toJSONObject() {
		JSONObject deployment;
    	deployment = new JSONObject();
	    	deployment.put("id", this.id);
	    	deployment.put("application", this.applicationId);
	    	deployment.put("status", this.status); 
	    	deployment.put("sTime", this.startTime); 
	    	deployment.put("eTime", this.endTime); 
		return deployment;
	}	 
}
