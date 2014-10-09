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

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDataSource;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader;

public class CollectorLoader implements ISourceLoader {

	
	public IDataSource getDtCollectorInstance(String sourceType, String uri)
	{
		// TODO
		// uri as function parameter is temporary and should be moved to IDataSource Implementations
		
		// Get CELAR Manager uri from configuration
		
		
		
		
		IDataSource DtCollectorInstance = null;		
		if(sourceType.equals(TYPE_MONITORING))
		{
			MonitoringData temp = new MonitoringData();
			temp.init(uri);
			DtCollectorInstance = temp;			
		}
		else if(sourceType.equals(TYPE_RESOURCES))
			DtCollectorInstance = null;
		else if(sourceType.equals(TYPE_APPLICATION))
		{
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
