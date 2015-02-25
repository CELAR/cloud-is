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
import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata;
import eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager;
import gr.ntua.cslab.celar.server.beans.structured.ApplicationInfo;
import gr.ntua.cslab.celar.server.beans.structured.ApplicationList;
//import gr.ntua.cslab.celar.server.beans.structured.ApplicationList;
import gr.ntua.cslab.celar.server.beans.structured.ModuleInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class CelarApplication.
 */
public class ApplicationData implements IApplicationMetadata {
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ApplicationData.class.getName());
	
	/** The app. */
	private eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager cmClient;
	
	/**
	 * Initializes the CELAR Manager Rest Client.
	 *
	 * @param restApiUri
	 *            the rest api uri
	 */
	public void init(String restApiUri) {
		this.cmClient = new eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager(restApiUri);		
	}
	
	/**
	 * Inits the.
	 *
	 * @param cm
	 *            the cm
	 */
	public void init(eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager cm) {
		this.cmClient = cm;		
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getUserApplications()
	 */
	@Override
	public String getUserApplications() {
		// TODO
		throw new java.lang.UnsupportedOperationException();
		
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#appVersions()
	 */
	@Override
	public String appVersions() {
		// TODO
		throw new java.lang.UnsupportedOperationException();
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#versDeployments()
	 */
	@Override
	public String versDeployments() {
		// TODO
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getVersionTopology(java.lang.String)
	 */
	@Override
	public String getVersionTopology(String versId) {
		throw new java.lang.UnsupportedOperationException();
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getApplicationInfo()
	 */
	@Override
	public Application getApplicationInfo(String appId) {
		String response = this.cmClient.getApplicationInfo(appId);
		Application app = new Application();
		if(response == null || response.isEmpty())	
    		return app;		
		
		InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));

		 //unmarshal an ApplicationInfo Entity
        ApplicationInfo inai = new ApplicationInfo();
        try {
			inai.unmarshal(stream);
		} catch (JAXBException e) {
			LOG.warn("Misformatted response [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
        
        //print the entity in a structured manner
        //you can observe all the field names and their values (in this example)
        System.out.println(inai.toString(true));
        
        //gets the first module of an application and prints its name
        //ModuleInfo mi = inai.modules.get(0);
        //System.out.println("Module name:"+mi.name);
		
        
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
		// TODO
		// adhoc userID
		int userID = 1;
		String response = this.cmClient.searchApplicationsByProperty(submitted_start, submitted_end, description, userID, module_name, component_description, provided_resource_id);
		List<Application> applications = new ArrayList<Application>();
		if(response == null || response.isEmpty())	
    		return applications;		
		
		InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
		
		// Parse the result into objects
		
		//unmarshal an ApplicationInfo Entity
		ApplicationList al = new ApplicationList();
        try {
        	al.unmarshal(stream);
		} catch (JAXBException e) {
			LOG.warn("Misformatted response [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
        
        //print the entity in a structured manner
        //you can observe all the field names and their values (in this example)
        System.out.println(al.toString(true));
		
		// Get and parse application list
        List<gr.ntua.cslab.celar.server.beans.Application> apps = al.getApplications();        
		
		if(apps == null || apps.isEmpty())	
    		return applications;
		
		for (gr.ntua.cslab.celar.server.beans.Application a : apps) {
			Application appl = new Application();
				appl.id = a.id;
	    	    appl.description = a.description;
	    	    appl.submitted = a.submitted.toString();
	    	applications.add(appl);			
		}
		
		return applications;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata#getApplicationDeployments(java.lang.String)
	 */
	@Override
	public List<Deployment> getApplicationDeployments(String appId) {
		String response = this.cmClient.getApplicationDeployments(appId);
		List<Deployment> deployments = new ArrayList<Deployment>();
		if(response == null || response.isEmpty())	
    		return deployments;
		
		InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));

		
		
		// TODO :=> Uncomment when 		
    	// Parse the result into objects celarBeans -> DeploymentList is implemented
		/*
		DeploymentList dl = new DeploymentList();
        try {
        	dl.unmarshal(stream);
		} catch (JAXBException e) {
			LOG.warn("Misformatted response [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
        
        //print the entity in a structured manner
        //you can observe all the field names and their values (in this example)
        System.out.println(dl.toString(true));
		
		// Get and parse application list
        List<gr.ntua.cslab.celar.server.beans.Application> depls = dl.getDeployments();        
		
		if(apps == null || apps.isEmpty())	
    		return applications;
		
		for (gr.ntua.cslab.celar.server.beans.Deployment d : depls) {
			Deployment depl = new Deployment();
	    	    depl.id = d.id;
				depl.status = d.getState();
				depl.startTime = d.start_Time.toString();
				depl.endTime = d.end_Time.toString();
    	    deployments.add(depl);			
		}
		
		return deployments;
    	*/
		
		        	    	
    	// Parse response to List<Deployment>
    	JSONArray json = new JSONArray(response);
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
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata#searchDeployments(java.lang.String, long, long, java.lang.String)
	 */
	@Override
	public List<Deployment> searchDeployments(String application_id, long start_time, long end_time, String status) {
		String response = this.cmClient.searchDeploymentsByProperty(application_id, start_time, end_time, status);
		List<Deployment> deployments = new ArrayList<Deployment>();
		if(response == null || response.isEmpty())	
    		return deployments;
        	
    	InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
		
		
		
		// TODO :=> Uncomment when 		
    	// Parse the result into objects celarBeans -> DeploymentList is implemented
		/*
		DeploymentList dl = new DeploymentList();
        try {
        	dl.unmarshal(stream);
		} catch (JAXBException e) {
			LOG.warn("Misformatted response [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
        
        //print the entity in a structured manner
        //you can observe all the field names and their values (in this example)
        System.out.println(dl.toString(true));
		
		// Get and parse application list
        List<gr.ntua.cslab.celar.server.beans.Application> depls = dl.getDeployments();        
		
		if(apps == null || apps.isEmpty())	
    		return applications;
		
		for (gr.ntua.cslab.celar.server.beans.Deployment d : depls) {
			Deployment depl = new Deployment();
	    	    depl.id = d.id;
				depl.status = d.getState();
				depl.startTime = d.start_Time.toString();
				depl.endTime = d.end_Time.toString();
    	    deployments.add(depl);			
		}
		
		return deployments;
    	*/
    	
    
    	
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
    	JSONArray json = new JSONArray(response);
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
