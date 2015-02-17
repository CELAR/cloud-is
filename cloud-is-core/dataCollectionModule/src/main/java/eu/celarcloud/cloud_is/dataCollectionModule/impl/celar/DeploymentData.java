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
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDeploymentMetadata;
import gr.ntua.cslab.celar.server.beans.structured.ApplicationInfo;
//import gr.ntua.cslab.celar.server.beans.structured.ApplicationList;
import gr.ntua.cslab.celar.server.beans.structured.ModuleInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class CelarApplication.
 */
public class DeploymentData implements IDeploymentMetadata {
	
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
		throw new java.lang.UnsupportedOperationException();
	}

	

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDeploymentMetadata#getDeploymentInstances(java.lang.String, java.lang.String, long, long)
	 */
	@Override
	public List<Metric> getDeploymentInstances(String deplId, String tierId, long start_time, long end_time) {
		List<Metric> instances = new ArrayList<Metric>();
		
		return instances;
	}



	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDeploymentMetadata#searchDeployments(long, long, java.lang.String)
	 */
	@Override
	public List<Deployment> searchDeployments(long start_time, long end_time, String status) {
		throw new java.lang.UnsupportedOperationException();
	}

}
