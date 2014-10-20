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


// TODO: Auto-generated Javadoc
/**
 * The Interface IApplication.
 */
public interface IApplication extends IDataSource{	
	/**
	 * Gets the user applications.
	 *
	 * @return the user applications
	 */
	public String getUserApplications();
	
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
	 * @return the string
	 */
	public List<Application> searchApplications();
	
	/**
	 * Gets the application info.
	 *
	 * @return the application info
	 */
	public String getApplicationInfo();
	
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
	
	public Deployment getDeployment(String deplId);
}
