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


// TODO: Auto-generated Javadoc
/**
 * The Interface IMonitoring.
 */
public interface IMonitoring extends IDataSource{
	
	/**
	 * Inits the.
	 *
	 * @param restApiUri
	 *            the rest api uri
	 */
	public void init(String restApiUri);
	
	/**
	 * Gets the agents.
	 *
	 * @param string
	 *            the string
	 * @param string2
	 *            the string2
	 * @return the agents
	 */
	public String getAgents(String string, String string2);
	
	/**
	 * Gets the agent metrics.
	 *
	 * @param agentId
	 *            the agent id
	 * @return the agent metrics
	 */
	public String getAgentMetrics(String agentId);
	
	/**
	 * Gets the values for time range.
	 *
	 * @param string
	 *            the string
	 * @param string2
	 *            the string2
	 * @param object
	 *            the object
	 * @param object2
	 *            the object2
	 * @return the values for time range
	 */
	public String getValuesForTimeRange(String string, String string2,String object, String object2);
}
