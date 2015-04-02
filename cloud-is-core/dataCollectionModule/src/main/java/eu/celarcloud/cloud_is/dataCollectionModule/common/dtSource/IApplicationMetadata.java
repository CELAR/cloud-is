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

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.MetricValue;


/**
 * The Interface IApplication.
 */
public interface IApplicationMetadata extends IDataSource{	
	/**
	 * Gets the user applications.
	 *
	 * @return the user applications
	 */
	public List<Application> getUserApplications();
	
	/**
	 * App versions.
	 *
	 * @return the string
	 */
	public String appVersions();
	
	/**
	 * Vers deployments.
	 *
	 * @return the string
	 */
	public String versDeployments();
	
	/**
	 * Gets the version topology.
	 *
	 * @param versId
	 *            the vers id
	 * @return the version topology
	 */
	public String getVersionTopology(String versId);
	
	/**
	 * Search applications.
	 *
	 * @param submitted_start
	 *            the submitted_start
	 * @param submitted_end
	 *            the submitted_end
	 * @param description
	 *            the description
	 * @param module_name
	 *            the module_name
	 * @param component_description
	 *            the component_description
	 * @param provided_resource_id
	 *            the provided_resource_id
	 * @return the string
	 */
	public List<Application> searchApplications(long submitted_start, long submitted_end, String description, String module_name, String component_description, String provided_resource_id);
	
	/**
	 * Search deployments.
	 *
	 * @param application_id
	 *            the application_id
	 * @param start_time
	 *            the start_time
	 * @param end_time
	 *            the end_time
	 * @param status
	 *            the status
	 * @return the list
	 */
	public List<Deployment> searchDeployments(String application_id, long start_time, long end_time, String  status);
	
	/**
	 * Gets the application info.
	 *
	 * @param appId
	 *            the app id
	 * @return the application info
	 */
	public Application getApplicationInfo(String appId);
	
	
	
	/**
	 * Gets the application deployments.
	 *
	 * @param appId
	 *            the app id
	 * @return the application deployments
	 */
	public List<Deployment> getApplicationDeployments(String appId);
	
	
}
