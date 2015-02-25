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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDeploymentMetadata;
import gr.ntua.cslab.celar.server.beans.*;

// TODO: Auto-generated Javadoc
/**
 * The Class CelarApplication.
 */
public class DeploymentData implements IDeploymentMetadata {
	
	/** The app. */
	private eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager cmClient;
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DeploymentData.class.getName());
	
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
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getRecentDeployments(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Deployment> getRecentDeployments(String limit, String status) {
    	String response = this.cmClient.searchDeploymentsByProperty("", 0, 0, "");    	
    	List<Deployment> deployments = new ArrayList<Deployment>();
    	if(response == null || response.isEmpty())	
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
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplication#getDeployment(java.lang.String)
	 */
	@Override
	public Deployment getDeployment(String deplId) {
		String response = this.cmClient.getDeploymentInfo(deplId);
		Deployment depl = new Deployment();
		if(response == null || response.isEmpty())	
    		return depl;
				
		InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
		
		//unmarshal an ApplicationInfo Entity
		gr.ntua.cslab.celar.server.beans.Deployment inai = new gr.ntua.cslab.celar.server.beans.Deployment();
		try {
			inai.unmarshal(stream);
		} catch (JAXBException e) {
			LOG.warn("Misformatted response [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
		   
		//print the entity in a structured manner
		//you can observe all the field names and their values (in this example)
		System.out.println(inai.toString(true));
		   
		
			depl.id = inai.id;
			depl.status = inai.getState();
			depl.startTime = inai.start_Time.toString();
			depl.endTime = inai.end_Time.toString();
			
		return depl;      
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
		ApplicationData appDataReferer = new ApplicationData();		
		appDataReferer.init(this.cmClient);
		
		return appDataReferer.searchDeployments("", start_time, end_time, status);
	}

}
