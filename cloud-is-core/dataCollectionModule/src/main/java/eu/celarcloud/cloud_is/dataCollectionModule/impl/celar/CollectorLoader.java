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
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader;

// TODO: Auto-generated Javadoc
/**
 * The ISourceLoader Implementation
 * for the CELAR Collector Bundle / Classes
 */
public class CollectorLoader implements ISourceLoader {
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader#getDtCollectorInstance(java.lang.String, java.lang.String)
	 */
	public IDataSource getDtCollectorInstance(String sourceType, String configPath)
	{
		// TODO
		// uri as function parameter is temporary and should be moved to IDataSource Implementations
		
		// Get CELAR Manager uri from configuration
		
		
		//
		String path = configPath + File.separator + "celar";		
		
		
		
		IDataSource DtCollectorInstance = null;		
		if(sourceType.equals(TYPE_MONITORING))
		{
			// Load the CELAR Manager endpoint address (uri) from the configuration
			EndpointConfig applicationEndpoint = new EndpointConfig(path + File.separator + "endpoint.celarmanager.properties");			
			String cmUri = applicationEndpoint.getUri();
			
			// Loaders passes to the IMonitoring implementation
			// the CELAR Manager endpoint address (uri) in order for it to obtain
			// the Monitoring Server endpoint (uri) from CM
			// before contacting the monitoring server for the requested information
						
			//
			System.out.println("This is the uri " + cmUri);
			//
			
			// Initialise the appropriate dataSource Implementation
			MonitoringData monitoringData = new MonitoringData();
			monitoringData.init(cmUri);
			
			// 'Return' the loaded instance
			DtCollectorInstance = monitoringData;			
		}
		else if(sourceType.equals(TYPE_MONITORING_HISTORY))
		{
			MonitoringHistoricalData temp = new MonitoringHistoricalData();
		}
		else if(sourceType.equals(TYPE_RESOURCES)) 
			DtCollectorInstance = null;
		else if(sourceType.equals(TYPE_APPLICATION))
		{
			EndpointConfig applicationEndpoint = new EndpointConfig(path + File.separator + "endpoint.celarmanager.properties");			
			String uri = applicationEndpoint.getUri();		
			System.out.println("This is the uri " + uri);
			
			
			ApplicationData temp = new ApplicationData();
			temp.init(uri);
			DtCollectorInstance = temp;
		}
		else if(sourceType.equals(TYPE_ELASTICITY))
			DtCollectorInstance = null;
		else
			DtCollectorInstance = null;
		
		
		return DtCollectorInstance;
	}

	
}
