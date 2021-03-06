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
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.MetricValue;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata;


// TODO: Auto-generated Javadoc
/**
 * The Class CelarApplication.
 */
public class DummyAppData implements IApplicationMetadata {
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getUserApplications()
	 */
	@Override
	public List<Application> getUserApplications() {
		Application application;
		List<Application> applications = new ArrayList<Application>();
    	
		application = new Application();
			application.id = "0000000013.000.000";
			application.uid = "13";
			application.vMajor = "0";
			application.vMinor = "0";
			application.description = "test_application_1";
			application.topology = "";
			application.submitted = "1413290766468";
		applications.add(application);
		
		application = new Application();
			application.id = "0000000014.000.000";
			application.uid = "14";
			application.vMajor = "0";
			application.vMinor = "0";
			application.description = "test_application_2";
			application.topology = "";
			application.submitted = "1413298776468";
		applications.add(application);
		
		application = new Application();
			application.id = "0000000013.001.000";
			application.uid = "13";
			application.vMajor = "1";
			application.vMinor = "0";
			application.description = "test_application_1";
			application.topology = "";
			application.submitted = "1413290767838";
		applications.add(application);
		
	    return applications;
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
	public Application getApplicationInfo(String appId) {
		Application application;    	
		application = new Application();
			application.id = appId;
			application.description = "";
			application.topology = "online";
			application.submitted = "1413290766468";
		
		return application;
	}
	
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#searchApplications()
	 */
	@Override
	public List<Application> searchApplications(long submitted_start, long submitted_end, String description, String module_name, String component_description, String provided_resource_id) {			    
		Application application;
		List<Application> applications = new ArrayList<Application>();
    	
		application = new Application();
			application.id = "0000000013.000.000";
			application.uid = "13";
			application.vMajor = "0";
			application.vMinor = "0";
			application.description = "test_application_1";
			application.topology = "";
			application.submitted = "1413290766468";
		applications.add(application);
		
		application = new Application();
			application.id = "0000000014.000.000";
			application.uid = "14";
			application.vMajor = "0";
			application.vMinor = "0";
			application.description = "test_application_2";
			application.topology = "";
			application.submitted = "1413298776468";
		applications.add(application);
		
		application = new Application();
			application.id = "0000000013.001.000";
			application.uid = "13";
			application.vMajor = "1";
			application.vMinor = "0";
			application.description = "test_application_1";
			application.topology = "";
			application.submitted = "1413290767838";
		applications.add(application);
		
	    return applications;
	}	

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata#getApplicationDeployments(java.lang.String)
	 */
	@Override
	public List<Deployment> getApplicationDeployments(String appId) {
		List<Deployment> deployments = new ArrayList<Deployment>();
		Deployment deployment;
    			
		Random rand = new Random();		
		
    	deployment = new Deployment();
    		deployment.id = String.valueOf(rand.nextInt((9999 - 1000) + 1) + 1000); 
			deployment.applicationId = appId;
			deployment.status = "online";
			deployment.startTime = "1413290766468";
			deployment.endTime = null;
		deployments.add(deployment);
    	
		deployment = new Deployment();
	    	deployment.id = String.valueOf(rand.nextInt((9999 - 1000) + 1) + 1000) + "1"; // need to end with 1
	    	deployment.applicationId = appId;
			deployment.status = "offline";
			deployment.startTime = "1413290766468";
			deployment.endTime = "1413298766468";
		deployments.add(deployment);
		
		deployment = new Deployment();
	    	deployment.id = String.valueOf(rand.nextInt((9999 - 1000) + 1) + 1000) + "2"; // need to end with 2
	    	deployment.applicationId = appId;
			deployment.status = "offline";
			deployment.startTime = "1413290766468";
			deployment.endTime = "1413298766468";
		deployments.add(deployment);
    	
	    return deployments;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata#searchDeployments(java.lang.String, long, long, java.lang.String)
	 */
	@Override
	public List<Deployment> searchDeployments(String appId, long tStart, long tEnd, String status) {
		List<Deployment> deployments = new ArrayList<Deployment>();
		Deployment deployment;
    			
		Random rand = new Random();
		
    	deployment = new Deployment();
    		deployment.id = String.valueOf(rand.nextInt((9999 - 1000) + 1) + 1000);
			deployment.applicationId = appId;
			deployment.status = "online";
			deployment.startTime = "1413290766468";
			deployment.endTime = null;
		deployments.add(deployment);
    	
		deployment = new Deployment();
	    	deployment.id = String.valueOf(rand.nextInt((9999 - 1000) + 1) + 1000);
	    	deployment.applicationId = appId;
			deployment.status = "offline";
			deployment.startTime = "1413290766468";
			deployment.endTime = "1413298766468";
		deployments.add(deployment);
    	
	    return deployments;
	}
}
