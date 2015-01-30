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
package eu.celarcloud.cloud_is.dataCollectionModule.impl.dummy;

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDataSource;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.SourceLoader;

public class CollectorLoader extends SourceLoader {

	@Override
	public IDataSource loadAppMetaInterface() {
		return new DummyAppData();
	}

	@Override
	public IDataSource loadDeplMetaInterface() {
		return new DummyDeplData();
	}

	@Override
	public IDataSource loadMeteringInterface() {
		return new DummyMonData();
	}

	/*
	 * Since Historical monitoring data is not supported
	 * from this bundle of collectors, the controller uses the 
	 * 'fall back' and returns the monitoring collector. 
	 */
	@Override
	public IDataSource loadMeteringHistoryInterface() {
		return new DummyMonData();
	}

	@Override
	public IDataSource loadTopologyInterface() {
		return new DummyTopology();
	}
}
