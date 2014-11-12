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
package eu.celarcloud.cloud_is.dataCollectionModule.impl.celar;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplication;
import gr.ntua.cslab.celar.server.beans.structured.ApplicationInfo;
import gr.ntua.cslab.celar.server.beans.structured.ModuleInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class CelarApplication.
 */
public class ApplicationData implements IApplication {
	
	/** The app. */
	private eu.celarcloud.cloud_is.dataCollectionModule.impl.common.clients.CelarManager cmClient;
	
	/**
	 * Initializes the CELAR Manager Rest Client
	 *
	 * @param restApiUri
	 *            the rest api uri
	 */
	public void init(String restApiUri) {
		this.cmClient = new eu.celarcloud.cloud_is.dataCollectionModule.impl.common.clients.CelarManager(restApiUri);		
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
	public Application getApplicationInfo(String appId) {
		String temp = this.cmClient.getApplicationInfo(appId);		
		
		InputStream stream = new ByteArrayInputStream(temp.getBytes(StandardCharsets.UTF_8));

		 //unmarshal an ApplicationInfo Entity
        ApplicationInfo inai = new ApplicationInfo();
        try {
			inai.unmarshal(stream);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //print the entity in a structured manner
        //you can observe all the field names and their values (in this example)
        System.out.println(inai.toString(true));
        
        //gets the first module of an application and prints its name
        //ModuleInfo mi = inai.modules.get(0);
        //System.out.println("Module name:"+mi.name);
		
        // Parse response to IS bean
        Application app = new Application();
        
        app.id = inai.id;
        app.description = inai.description;
        app.submitted = inai.submitted.toString();
        
        
		return app;
		//return this.cmClient.getApplicationInfo("0");
	}
	
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#searchApplications()
	 */
	@Override
	public List<Application> searchApplications(long submitted_start, long submitted_end, String description, String module_name, String component_description, String provided_resource_id) {
		String temp = this.cmClient.searchApplicationsByProperty(0, 0, "", 0, "", "", "");
		System.out.println("test: " + temp);
		
		List<Application> applications = new ArrayList<Application>();
		if(temp == null || temp.isEmpty())	
    		return applications;
		
    	// Parse response to List<Deployment>
    	try {
	    	JSONArray json = new JSONArray(temp);
	    	for (int i = 0; i < json.length(); ++i) {
	    	    JSONObject a = json.getJSONObject(i);
	    	    Application appl = new Application();
	    	    	appl.id = a.getString("id");
		    	    appl.description = a.getString("description");
		    	    appl.submitted = a.getString("submitted");
		    	applications.add(appl);
	    	}
    	}
    	catch(JSONException e) {
    		System.out.println("Errot on parsing JSON Response : ");
    		System.out.println("[" + temp + "]");
            e.printStackTrace();    		
    	}
    	
		return applications;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getRecentDeployments(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Deployment> getRecentDeployments(String limit, String status) {
    	String temp = this.cmClient.searchDeploymentsByProperty("", 0, 0, "");
    	System.out.println("test: " + temp);
    	
    	List<Deployment> deployments = new ArrayList<Deployment>();
    	if(temp == null || temp.isEmpty())	
    		return deployments;
        	
    	
    	/*
    	// Date format to parse date
    	Date parsed = new Date();
		try {
		    SimpleDateFormat format =
		        new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		    parsed = format.parse(dateString);
		}
		catch(ParseException pe) {
		    throw new IllegalArgumentException();
		}
    	*/
    	
    	// Parse response to List<Deployment>
    	JSONArray json = new JSONArray(temp);
    	for (int i = 0; i < json.length(); ++i) {
    	    JSONObject d = json.getJSONObject(i);
    	    Deployment depl = new Deployment();
	    	    depl.id = d.getString("id");
	    	    depl.applicationId = d.getString("applicationId");
	    	    depl.status = d.getString("status");
	    	    depl.startTime = d.getString("startTime");
	    	    depl.endTime = d.getString("endTime");
    	    deployments.add(depl);
    	}
	    return deployments;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplication#getDeployment(java.lang.String)
	 */
	@Override
	public Deployment getDeployment(String deplId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Deployment> getApplicationDeployments(String appId) {
		String temp = this.cmClient.getApplicationDeployments(appId);		
		
		InputStream stream = new ByteArrayInputStream(temp.getBytes(StandardCharsets.UTF_8));

		 //unmarshal an ApplicationInfo Entity
		/*
        ApplicationInfo inai = new ApplicationInfo();
        try {
			inai.unmarshal(stream);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //print the entity in a structured manner
        //you can observe all the field names and their values (in this example)
        System.out.println(inai.toString(true));
        
        //gets the first module of an application and prints its name
        //ModuleInfo mi = inai.modules.get(0);
        //System.out.println("Module name:"+mi.name);
		
        // Parse response to IS bean
        Application app = new Application();
        
        app.id = inai.id;
        app.description = inai.description;
        app.submitted = inai.submitted.toString();
        */
        
		//return app;
		return null;
	}

	@Override
	public List<Deployment> searchDeployments(String application_id, long start_time, long end_time, String status) {
		String temp = this.cmClient.searchDeploymentsByProperty(application_id, start_time, end_time, status);
    	System.out.println("test: " + temp);
    	
    	List<Deployment> deployments = new ArrayList<Deployment>();
    	if(temp == null || temp.isEmpty())	
    		return deployments;
        	
    	
    	/*
    	// Date format to parse date
    	Date parsed = new Date();
		try {
		    SimpleDateFormat format =
		        new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		    parsed = format.parse(dateString);
		}
		catch(ParseException pe) {
		    throw new IllegalArgumentException();
		}
    	*/
    	
    	// Parse response to List<Deployment>
    	JSONArray json = new JSONArray(temp);
    	for (int i = 0; i < json.length(); ++i) {
    	    JSONObject d = json.getJSONObject(i);
    	    Deployment depl = new Deployment();
	    	    depl.id = d.getString("id");
	    	    depl.applicationId = d.getString("applicationId");
	    	    depl.status = d.getString("status");
	    	    depl.startTime = d.getString("startTime");
	    	    depl.endTime = d.getString("endTime");
    	    deployments.add(depl);
    	}
	    return deployments;
	}

}
