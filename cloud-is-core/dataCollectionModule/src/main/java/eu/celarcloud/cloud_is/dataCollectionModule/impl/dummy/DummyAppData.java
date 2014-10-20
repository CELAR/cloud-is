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
package eu.celarcloud.cloud_is.dataCollectionModule.impl.dummy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplication;


// TODO: Auto-generated Javadoc
/**
 * The Class CelarApplication.
 */
public class DummyAppData implements IApplication {
	
	/** The app. */
	private eu.celarcloud.cloud_is.dataCollectionModule.impl.common.clients.CelarManager app;
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#init(java.lang.String)
	 */
	public void init(String restApiUri) {
		this.app = new eu.celarcloud.cloud_is.dataCollectionModule.impl.common.clients.CelarManager(restApiUri);		
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getUserApplications()
	 */
	@Override
	public String getUserApplications() {
		
		// Build report
		JSONArray json = new JSONArray();
	    		
	    for(int i=0; i<3; i++)
	    {
	    	String appId = String.valueOf(i);
	    	JSONObject app = new JSONObject();
	    	app.put("appName", "testName_" + i);
	    	app.put("appId", appId);
	    	
	    	json.put(app);
	    }
	    
	    
	    
		return json.toString();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#appVersions()
	 */
	@Override
	public String appVersions() {
		// Build report
		JSONArray json = new JSONArray();
	    		
	    for(int i=1; i<6; i++)
	    {
	    	String appId = String.valueOf(i);
	    	JSONObject app = new JSONObject();
	    	app.put("versName", "testName_" + i);
	    	app.put("versId", appId);
	    	
	    	json.put(app);
	    }
		return json.toString();
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#versDeployments()
	 */
	@Override
	public String versDeployments() {
		// Build report
			JSONArray json = new JSONArray();
		    		
		    for(int i=0; i<3; i++)
		    {
		    	String appId = String.valueOf(i);
		    	JSONObject app = new JSONObject();
		    	app.put("versName", "testName_" + i);
		    	app.put("versId", appId);
		    	
		    	json.put(app);
		    }
			return json.toString();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getVersionTopology(java.lang.String)
	 */
	@Override
	public String getVersionTopology(String versId) {
		// Build report	
    	JSONObject json = new JSONObject();
    	String desc ="";
	    
    	// TODO
    	// Dummy data
    	switch(versId)
    	{
    		case "1":
    			desc = "<description>" +
						    "<modules>" +
						        "<module name=\"appServer\">" +
						            "<image>apache6</image>" +
						        "</module>" +
						        "<module name=\"dbServer\">" +
						           " <image>cassndra</image>" +
						        "</module>" +
						   " </modules>" +
						"</description>";
    			break;
    		case "2":
    			desc = "<description>" +
			    		    "<modules>" +
			    		        "<module name=\"loadBalancer\">" +
			    		            "<image>apache</image>" +
			    		        "</module>" +
			    		        "<module name=\"appServer\">" +
			    		            "<image>apache6</image>" +
			    		        "</module>" +
			    		        "<module name=\"dbServer\">" +
			    		            "<image>cassndra</image>" +
			    		        "</module>" +
			    		    "</modules>" +
			    		"</description>";
    			break;
    		case "3":
    			desc = "<description>" +
			    		    "<modules>" +
			    		       "<module name=\"loadBalancer\">" +
			    		            "<image>haproxy</image>" +
			    		        "</module>" +
			    		        "<module name=\"appServer\">" +
			    		            "<image>apache6</image>" +
			    		        "</module>" +
			    		        "<module name=\"dbServer\">" +
			    		            "<image>cassndra</image>" +
			    		        "</module>" +
			    		    "</modules>" +
			    		"</description>";
    			break;
    		case "4":
    			desc = "<description>" +
			    		    "<modules>" +
			    		        "<module name=\"loadBalancer\">" +
			    		            "<image>apache</image>" +
			    		        "</module>" +
			    		        "<module name=\"appServer\">" +
			    		            "<image>apache7</image>" +
			    		        "</module>" +
			    		       " <module name=\"dbServer\">" +
			    		            "<image>cassndra</image>" +
			    		        "</module>" +
			    		    "</modules>" +
			    		"</description>";
    			break;
    		case "5":
    			desc = "<description>" +
			    		    "<modules>" +
			    		        "<module name=\"loadBalancer\">" +
			    		            "<image>haproxy</image>" +
			    		        "</module>" +
			    		        "<module name=\"appServer\">" +
			    		            "<image>apache7</image>" +
			    		        "</module>" +
			    		        "<module name=\"dbServer\">" +
			    		            "<image>cassndra</image>" +
			    		        "</module>" +
			    		    "</modules>" +
			    		"</description>";
    			break;
    	}
    	
    	System.out.println(versId);
    	json.put("versTopology", desc);
	    return json.toString();
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getApplicationInfo()
	 */
	@Override
	public String getApplicationInfo() {
		return this.app.getApplicationInfo("0");
	}
	
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#searchApplications()
	 */
	@Override
	public List<Application> searchApplications() {			    
		String temp = this.app.searchApplicationsByProperty(0, 0, "", 0, "", "", "");
		
		
		// Parse response to List<Deployment>
    	List<Application> applications = null;
    	
	    return applications;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getRecentDeployments(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Deployment> getRecentDeployments(String limit, String status) {
		List<Deployment> deployments = new ArrayList<Deployment>();
		Deployment deployment;
    	
		/*
		// Dummy data
		//
		int count = 100;
		// dummy calculations
		// Find the start and the end of the deployment
		BigInteger tStart = new BigInteger("1413290766468");
		int sRate = 500 * 1000; // to ms
		BigInteger durration = new BigInteger(String.valueOf((sRate * count)));
		BigInteger tEnd = tStart.add(durration);
		//	
		*/
		
    	deployment = new Deployment();
    		deployment.id = "9768";
			deployment.applicationId = "67890";
			deployment.status = "online";
			deployment.startTime = "1413290766468";
			deployment.endTime = null;
		deployments.add(deployment);
    	
		deployment = new Deployment();
	    	deployment.id = "5678";
	    	deployment.applicationId = "10293";
			deployment.status = "offline";
			deployment.startTime = "1413290766468";
			deployment.endTime = "1413298766468";
		deployments.add(deployment);
    	
	    return deployments;
	}

	@Override
	public Deployment getDeployment(String deplId) {
		Deployment deployment = new Deployment();
			deployment.id = deplId;
			deployment.applicationId = "";
			deployment.status = "offline";
			deployment.startTime = "1413290766468";
			deployment.endTime = "1413299766468";
	    return deployment;
	}

}
