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
package eu.celarcloud.cloud_is.dataCollectionModule.impl.standalone;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.MetricValue;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering;
import eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.Jcatascopia;

// TODO: Auto-generated Javadoc
/**
 * The Class Jcatascopia.
 */
public class MonitoringData implements IMetering {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MonitoringData.class.getName());
	/* The JCatascopia Monitoring Client Class. */
	private eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.Jcatascopia jcClient;	
	
	/**
	 * Initializes the JCatascopia Rest Client.
	 *
	 * @param restApiUri
	 *            the rest api uri
	 */
	public void init(String restApiUri) {
		this.jcClient = new eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.Jcatascopia(restApiUri);		
	}
	
	
	@Override
	public String getAgents(String deplId, String string) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getAgentMetrics(String deplId, String agentId) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public List<MetricValue> getMetricValues(String deplId, String metric_id, long sTime, long eTime) {
		// Get starting time
		long startTime = System.nanoTime();
				
		List<MetricValue> list = new ArrayList<MetricValue>();
		
		String interval = "9500";
		
		JSONObject response = null;
		JSONArray values = null;
		try {
			response = new JSONObject(this.jcClient.getValuesForTimeRange(metric_id, interval, sTime, eTime));
			values = response.getJSONArray("values");
			response = null;
    	} 
    	catch (JSONException e) {
    		// Missformated request
    		LOG.warn("Missformated Response: ", e);
    		return list;
    		
    	}
    	catch(NullPointerException e) {
    		// error
    		LOG.warn("Error on getting data from JCatascopia: ", e);
    		return list;
    	}
		
		// Print completion time		
		System.out.println("Getting Metrics from JC: " + (System.nanoTime() - startTime) + " ns");
		
		startTime = System.nanoTime();
		
		JSONObject value;
		for(int i = 0; i < values.length(); i++)
		{
			value = values.getJSONObject(i);
			
			// Since JCatascopia timestamp are NOT in unix time stamp formart
			// we need to convert them
			String timestamp = "";
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			try {
			  Date dt = formatter.parse(value.getString("timestamp"));
			  timestamp = String.valueOf(dt.getTime());
			} catch (ParseException e) {
			  // This can happen if you are trying to parse an invalid date, e.g., 25:19:12.
			  // Here, you should log the error and decide what to do next
			  e.printStackTrace();
			  timestamp = value.getString("timestamp");
			}
			
			MetricValue m = new MetricValue(timestamp, value.getString("value"));
			list.add(m);
		}
		
		// Print completion time		
		System.out.println("Parsing Metrics to internal structures " + (System.nanoTime() - startTime) + " ns");		
		
		return list;
	}

	@Override
	public List<MetricValue> getDeploymentCost(String deplId, String tierId, long sTime, long eTime) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public List<String> getAvailableMetrics(String deplId, String compId) {
		throw new java.lang.UnsupportedOperationException();
	}

	
}
