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

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.MetricValue;

// TODO: Auto-generated Javadoc
/**
 * The Interface IDeploymentMetadata.
 */
public interface IDeploymentMetadata extends IDataSource{	
	/**
	 * Gets the recent deployments.
	 *
	 * @param limit
	 *            the limit
	 * @param status
	 *            the status
	 * @return the recent deployments
	 */
	public List<Deployment> getRecentDeployments(String limit, String status);
	
	/**
	 * Gets the user deployments.
	 *
	 * @return the user deployments
	 */
	public List<Deployment> getUserDeployments();
	
	/**
	 * Gets the deployment.
	 *
	 * @param deplId
	 *            the depl id
	 * @return the deployment
	 */
	public Deployment getDeployment(String deplId);
	
	/**
	 * Gets the deployment instances.
	 *
	 * @param deplId
	 *            the depl id
	 * @param tierId
	 *            the tier id
	 * @param sTime
	 *            the s time
	 * @param eTime
	 *            the e time
	 * @return the deployment instances
	 */
	public List<MetricValue> getDeploymentInstances(String deplId, String tierId, long sTime, long eTime);
	
	/**
	 * Search deployments.
	 *
	 * @param start_time
	 *            the start_time
	 * @param end_time
	 *            the end_time
	 * @param status
	 *            the status
	 * @return the list
	 */
	public List<Deployment> searchDeployments(long start_time, long end_time, String  status);
}
