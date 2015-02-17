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

import java.util.ArrayList;
import java.util.List;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata;


/**
 * The Class CelarApplication.
 */
public class ApplicationData implements IApplicationMetadata {
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getUserApplications()
	 */
	@Override
	public String getUserApplications() {
		throw new java.lang.UnsupportedOperationException();		
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#appVersions()
	 */
	@Override
	public String appVersions() {
		throw new java.lang.UnsupportedOperationException();
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#versDeployments()
	 */
	@Override
	public String versDeployments() {
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getVersionTopology(java.lang.String)
	 */
	@Override
	public String getVersionTopology(String versId) {
		throw new java.lang.UnsupportedOperationException();
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getApplicationInfo()
	 */
	@Override
	public Application getApplicationInfo(String appId) {
		throw new java.lang.UnsupportedOperationException();
	}
	
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#searchApplications()
	 */
	@Override
	public List<Application> searchApplications(long submitted_start, long submitted_end, String description, String module_name, String component_description, String provided_resource_id) {			    
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getRecentDeployments(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Deployment> getApplicationDeployments(String appId) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public List<Deployment> searchDeployments(String appId, long tStart, long tEnd, String status) {
		throw new java.lang.UnsupportedOperationException();
	}
}
