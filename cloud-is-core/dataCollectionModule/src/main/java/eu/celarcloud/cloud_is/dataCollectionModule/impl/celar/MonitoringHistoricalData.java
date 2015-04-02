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

import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.MetricValue;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering;
import gr.ntua.cslab.celar.server.beans.structured.ApplicationInfo;
import gr.ntua.cslab.celar.server.beans.structured.REList;

// TODO: Auto-generated Javadoc
/**
 * The Class MonitoringHistoricalData.
 */
public class MonitoringHistoricalData implements IMetering {
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MonitoringHistoricalData.class.getName());	
	
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
	public List<MetricValue> getMetricValues(String deplId, String metric_id, long sTime, long eTime) {		
		String response = this.cmClient.getMetricValue(deplId, metric_id, sTime, eTime);	
		
		List<MetricValue> applications = new ArrayList<MetricValue>();
		if(response == null || response.isEmpty())	
    		return applications;		
		
		InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
		
		// Parse the result into objects		
		//unmarshal an ApplicationInfo Entity
		REList<gr.ntua.cslab.celar.server.beans.MetricValue> mVals = new REList<gr.ntua.cslab.celar.server.beans.MetricValue>();
        try {
        	mVals.unmarshal(stream);
		} catch (JAXBException e) {
			LOG.warn("Misformatted response [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
        
        //print the entity in a structured manner
        //you can observe all the field names and their values (in this example)
        System.out.println(mVals.toString(true));
		
		// Get and parse application list
        List<gr.ntua.cslab.celar.server.beans.MetricValue> metrics = mVals.getValues();        
		
		if(mVals == null || mVals.isEmpty())	
    		return applications;
		
		for (gr.ntua.cslab.celar.server.beans.MetricValue m : metrics) {
			MetricValue metric = new MetricValue();
				metric.timestamp = m.timestamp.toString();
				metric.value = String.valueOf(m.value);
	    	applications.add(metric);			
		}
		
		return applications;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getDeploymentCost(java.lang.String, java.lang.String, long, long)
	 */
	@Override
	public List<MetricValue> getDeploymentCost(String deplId, String tierId, long sTime, long eTime) {
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getAvailableMetrics(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getAvailableMetrics(String deplId, String compId) {
		String response = this.cmClient.getComponentMetrics(compId);
		
		List<String> metrics = new ArrayList<String>();
		if(response == null || response.isEmpty())	
    		return metrics;		
		
		InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
		
		// Parse the result into objects		
		//unmarshal an ApplicationInfo Entity
		REList<gr.ntua.cslab.celar.server.beans.Metric> mVals = new REList<gr.ntua.cslab.celar.server.beans.Metric>();
        try {
        	mVals.unmarshal(stream);
		} catch (JAXBException e) {
			LOG.warn("Misformatted response [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
        
        //print the entity in a structured manner
        //you can observe all the field names and their values (in this example)
        System.out.println(mVals.toString(true));
		
		// Get and parse application list
        List<gr.ntua.cslab.celar.server.beans.Metric> metricsList = mVals.getValues();        
		
		if(mVals == null || mVals.isEmpty())	
    		return metrics;
		
		for (gr.ntua.cslab.celar.server.beans.Metric m : metricsList) {			
	    	metrics.add(m.name);			
		}
		
		return metrics;
	}

}
