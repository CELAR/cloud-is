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
import java.util.List;

import javax.xml.bind.JAXBException;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering;
import gr.ntua.cslab.celar.server.beans.structured.ApplicationInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class MonitoringHistoricalData.
 */
public class MonitoringHistoricalData implements IMetering {
	
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
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getAgents(java.lang.String, java.lang.String)
	 */
	@Override
	public String getAgents(String deplId, String string2) {
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getAgentMetrics(java.lang.String, java.lang.String)
	 */
	@Override
	public String getAgentMetrics(String deplId, String agentId) {
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getMetricValues(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Metric> getMetricValues(String deplId, String name, String sTime, String eTime) {
		String compId = deplId;
		String metricId = name;
		
		String temp = this.cmClient.getMetrics(compId, metricId);		
		
		// TODO
		// TODO
		
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
        
        
		
		
		
		
		
		
		
		
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getDeploymentCost(java.lang.String, java.lang.String, long, long)
	 */
	@Override
	public List<Metric> getDeploymentCost(String deplId, String tierId, long sTime, long eTime) {
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getAvailableMetrics(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getAvailableMetrics(String deplId, String compId) {
		throw new java.lang.UnsupportedOperationException();
	}

}
