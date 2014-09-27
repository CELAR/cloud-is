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
package eu.celarcloud.cloud_is.dataCollectionModule.impl.common.clients;

import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;

import eu.celarcloud.cloud_is.dataCollectionModule.common.RestClient;

// TODO: Auto-generated Javadoc
/**
 * The Class Jcatascopia.
 */
public class Jcatascopia  {
	
	/** The server ip. */
	private String serverIp;
	
	/** The rest path. */
	private String restPath = "/";	
	
	/**
	 * Instantiates a new jcatascopia.
	 *
	 * @param serverIp
	 *            the server ip
	 */
	public Jcatascopia(String serverIp) 
	{
		this.serverIp = serverIp;
	}
  	
	/**
	 * Gets the agents.
	 *
	 * @param appId
	 *            the app id
	 * @param agentStatus
	 *            the agent status
	 * @return the agents
	 */
	public String getAgents(String appId, String agentStatus)
	{
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "agents/";		
	    builder.setPath(path);
	    
		if(agentStatus != null && !agentStatus.isEmpty())
			builder.setParameter("status", agentStatus);
		builder.setParameter("applicationID", "1"); //TODO
		 
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient();
		
		try {
			response = client.executeGet(builder.build());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	/**
	 * Gets the agent metrics.
	 *
	 * @param agentId
	 *            the agent id
	 * @return the agent metrics
	 */
	public String getAgentMetrics(String agentId)
	{		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "agents/" + agentId + "/availableMetrics";		
	    builder.setPath(path);
		  
	    //
		CloseableHttpResponse response = null;
		RestClient client = new RestClient();
		
		try {
			response = client.executeGet(builder.build());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	/**
	 * Gets the latest metric value.
	 *
	 * @param metricsId
	 *            the metrics id
	 * @return the latest metric value
	 */
	public String getLatestMetricValue(String[] metricsId)
	{
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "metrics/";		
	    builder.setPath(path);
	    
	    builder.setParameter("body", StringUtils.join(metricsId, ','));
		 
	    //
		CloseableHttpResponse response = null;
		RestClient client = new RestClient();
		
		try {
			response = client.executeGet(builder.build());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	//http://109.231.122.22:8080/JCatascopia-Web/restAPI/metrics/35a4d65b776041fb9320b886ef5aa7e5:cpuTotal/?interval=10000&tstrart=32278837562435
	/**
	 * Gets the values for time range.
	 *
	 * @param metricId
	 *            the metric id
	 * @param interval
	 *            the interval
	 * @param tstart
	 *            the tstart
	 * @param tend
	 *            the tend
	 * @return the values for time range
	 */
	public String getValuesForTimeRange(String metricId, String interval, String tstart, String tend)
	{	
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "metrics/" + metricId;		
	    builder.setPath(path);
	    
	    builder.setParameter("interval", interval);
		if(tstart != null && !tstart.isEmpty())
			builder.setParameter("tstart", tstart);
		if(tend != null && !tend.isEmpty())
			builder.setParameter("tend", tend);
		  
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient();
		
		try {
			response = client.executeGet(builder.build());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}

}
