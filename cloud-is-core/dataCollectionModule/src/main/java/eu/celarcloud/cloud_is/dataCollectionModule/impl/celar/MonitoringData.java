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

import java.util.List;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMonitoring;

// TODO: Auto-generated Javadoc
/**
 * The Class Jcatascopia.
 */
public class MonitoringData implements IMonitoring {

	/* The JCatascopia Monitoring Client Class. */
	private eu.celarcloud.cloud_is.dataCollectionModule.impl.common.clients.Jcatascopia jcClient;
	/* The CELAR Manager Client Class. */
	private eu.celarcloud.cloud_is.dataCollectionModule.impl.common.clients.CelarManager cmClient;
	
	
	/**
	 * Initializes the JCatascopia Rest Client
	 *
	 * @param restApiUri
	 *            the rest api uri
	 */
	public void init(String restApiUri) {
		this.jcClient = new eu.celarcloud.cloud_is.dataCollectionModule.impl.common.clients.Jcatascopia(restApiUri);		
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.monitoring.IMonitoring#getAgents(java.lang.String, java.lang.String)
	 */
	public String getAgents(String appId, String agentStatus)
	{
		return this.jcClient.getAgents(appId, agentStatus);
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.monitoring.IMonitoring#getAgentMetrics(java.lang.String)
	 */
	public String getAgentMetrics(String agentId)
	{
		return this.jcClient.getAgentMetrics(agentId);
	}
	
	public List<Metric> getMetricValues(String name, String sTime, String eTime) {
		// TODO Auto-generated method stub
		return null;
	}

}
