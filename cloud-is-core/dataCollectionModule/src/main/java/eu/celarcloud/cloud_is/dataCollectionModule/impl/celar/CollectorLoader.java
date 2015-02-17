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

import java.io.File;

import eu.celarcloud.cloud_is.dataCollectionModule.common.EndpointConfig;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDataSource;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.SourceLoader;

// TODO: Auto-generated Javadoc
/**
 * The ISourceLoader Implementation for the CELAR Collector Bundle / Classes.
 */
public class CollectorLoader extends SourceLoader {
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.SourceLoader#loadAppMetaInterface()
	 */
	@Override
	public IDataSource loadAppMetaInterface() {
		String path = this.getConfigPath() + File.separator + "celar";
		
		// Load the CELAR Manager endpoint address (uri) from the configuration
		EndpointConfig applicationEndpoint = new EndpointConfig(path + File.separator + "endpoint.celarmanager.properties");			
		String uri = applicationEndpoint.getUri();		
		System.out.println("CELAR Manager configured Uri is: " + uri);			
		
		ApplicationData temp = new ApplicationData();
		temp.init(uri);
		
		// 'Return' the loaded instance
		return temp;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.SourceLoader#loadDeplMetaInterface()
	 */
	@Override
	public IDataSource loadDeplMetaInterface() {
		String path = this.getConfigPath() + File.separator + "celar";
		
		// Load the CELAR Manager endpoint address (uri) from the configuration
		EndpointConfig applicationEndpoint = new EndpointConfig(path + File.separator + "endpoint.celarmanager.properties");			
		String uri = applicationEndpoint.getUri();		
		System.out.println("CELAR Manager configured Uri is: " + uri);			
		
		DeploymentData temp = new DeploymentData();
		temp.init(uri);
		
		// 'Return' the loaded instance
		return temp;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.SourceLoader#loadMeteringInterface()
	 */
	@Override
	public IDataSource loadMeteringInterface() {
		String path = this.getConfigPath() + File.separator + "celar";
		
		// Load the CELAR Manager endpoint address (uri) from the configuration
		EndpointConfig applicationEndpoint = new EndpointConfig(path + File.separator + "endpoint.celarmanager.properties");			
		String cmUri = applicationEndpoint.getUri();
		
		// Loaders passes to the IMonitoring implementation
		// the CELAR Manager endpoint address (uri) in order for it to obtain
		// the Monitoring Server endpoint (uri) from CM
		// before contacting the monitoring server for the requested information
					
		//
		System.out.println("CELAR Manager configured Uri is: " + cmUri);
		//
		
		// Initialise the appropriate dataSource Implementation
		MonitoringData monitoringData = new MonitoringData();
		monitoringData.init(cmUri);
		
		// 'Return' the loaded instance
		return monitoringData;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.SourceLoader#loadMeteringHistoryInterface()
	 */
	@Override
	public IDataSource loadMeteringHistoryInterface() {
		return new MonitoringHistoricalData();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.SourceLoader#loadTopologyInterface()
	 */
	@Override
	public IDataSource loadTopologyInterface() {
		return new TopologyData();
	}

	
}
