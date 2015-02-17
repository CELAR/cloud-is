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
package eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource;

import java.util.List;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;


// TODO: Auto-generated Javadoc
/**
 * The Interface IMonitoring.
 */
public interface IMetering extends IDataSource{	
	
	/**
	 * Gets the agents.
	 *
	 * @param deplId
	 *            the depl id
	 * @param agentStatus
	 *            the agent status
	 * @return the agents
	 */
	public String getAgents(String deplId, String agentStatus);
	
	/**
	 * Gets the agent metrics.
	 *
	 * @param deplId
	 *            the depl id
	 * @param agentId
	 *            the agent id
	 * @return the agent metrics
	 */
	public String getAgentMetrics(String deplId, String agentId);
	
	
	/**
	 * Gets the available metrics.
	 *
	 * @param deplId
	 *            the depl id
	 * @param compId
	 *            the comp id
	 * @return the available metrics
	 */
	public List<String> getAvailableMetrics(String deplId, String compId);
	
	/**
	 * Gets the metric values.
	 *
	 * @param deplId
	 *            the depl id
	 * @param name
	 *            the name
	 * @param sTime
	 *            the s time
	 * @param eTime
	 *            the e time
	 * @return the metric values
	 */
	public List<Metric> getMetricValues(String deplId, String name, String sTime, String eTime);

	/**
	 * Gets the deployment cost.
	 *
	 * @param deplId
	 *            the depl id
	 * @param tierId
	 *            the tier id
	 * @param sTime
	 *            the s time
	 * @param eTime
	 *            the e time
	 * @return the deployment cost
	 */
	public List<Metric> getDeploymentCost(String deplId, String tierId, long sTime, long eTime);
}
