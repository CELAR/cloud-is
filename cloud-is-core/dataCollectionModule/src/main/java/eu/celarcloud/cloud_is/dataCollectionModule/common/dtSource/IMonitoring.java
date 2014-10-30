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
public interface IMonitoring extends IDataSource{	
	/**
	 * Gets the agents.
	 *
	 * @param string
	 *            the string
	 * @param string2
	 *            the string2
	 * @return the agents
	 */
	public String getAgents(String deplId, String agentStatus);
	
	/**
	 * Gets the agent metrics.
	 *
	 * @param agentId
	 *            the agent id
	 * @return the agent metrics
	 */
	public String getAgentMetrics(String deplId, String agentId);
	
	public List<Metric> getMetricValues(String deplId, String name, String sTime, String eTime);
}
