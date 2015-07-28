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

import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ITopology;
import eu.celarcloud.cloud_is.dataCollectionModule.impl.celar.exception.MisformattedResponse;
import eu.celarcloud.cloud_is.dataCollectionModule.impl.celar.exception.NullObject;

/**
 * The Class TopologyData.
 */
public class TopologyData extends CelarManagerEndpoint  implements ITopology {	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TopologyData.class.getName());
		
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ITopology#getTopology(java.lang.String)
	 */
	@Override
	public String getTopology(String deplId) throws MisformattedResponse, NullObject {
		//
		DeploymentData deplDataReferer = new DeploymentData();
		deplDataReferer.init(this.cmClient);
		
		// Load the Deployment
		
		
		String appId = deplDataReferer.getDeployment(deplId).applicationId;
		if(appId == null || appId.isEmpty())	
    		return "";
		
		ApplicationData appDataReferer = new ApplicationData();		
		appDataReferer.init(this.cmClient);		
		
		String topology = appDataReferer.getApplicationInfo(appId).topology;
		if(topology == null || topology.isEmpty())	
    		return "";
		
		
		return topology;
	}

}
