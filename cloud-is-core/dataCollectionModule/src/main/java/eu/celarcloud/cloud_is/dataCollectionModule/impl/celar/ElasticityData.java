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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IElasticityLog;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ITopology;

/**
 * The Class TopologyData.
 */
public class ElasticityData implements IElasticityLog {	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ElasticityData.class.getName());
	
	/** The cm client. */
	private eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager cmClient;
	
	/**
	 * Initializes the CELAR Manager Rest Client.
	 *
	 * @param restApiUri
	 *            the rest api uri
	 */
	public void init(String restApiUri) {
		this.cmClient = new eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager(restApiUri);
	}
	
	@Override
	public List<String> getEnforcedActions(String deplId, String name, String sTime, String eTime) {
		DeploymentData deplDataReferer = new DeploymentData();
		deplDataReferer.init(this.cmClient);
		
		String appId = deplDataReferer.getDeployment(deplId).applicationId;
		if(appId == null || appId.isEmpty())	
    		return null;
		
		ApplicationData appDataReferer = new ApplicationData();		
		appDataReferer.init(this.cmClient);		
		
		/*
		String topology = appDataReferer.getApplicationInfo(appId).topology;
		if(topology == null || topology.isEmpty())	
    		return "";
		*/
		
		return new ArrayList<String>();
	}

	@Override
	public List<String> getEnforcedActions(String deplId, String compId, String name, String sTime, String eTime) {
		// TODO Auto-generated method stub
		return null;
	}

}
