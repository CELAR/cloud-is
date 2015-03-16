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

import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISourceLoader.
 */
public interface ISourceLoader {
	
	/**
	 * Gets the dt collector instance.
	 *
	 * @param type
	 *            the type
	 * @return the dt collector instance
	 */
	public IDataSource getDtCollectorInstance(DataSourceType type);
	
	/**
	 * Inits the.
	 */
	public void init();
	
	/**
	 * Inits the.
	 *
	 * @param props
	 *            the props
	 */
	public void init(Properties props);
	
	/**
	 * Inits the.
	 *
	 * @param configPath
	 *            the config path
	 */
	public void init(String configPath);
	
	/**
	 * Inits the.
	 *
	 * @param configPath
	 *            the config path
	 * @param dataPath
	 *            the data path
	 */
	public void init(String configPath, String dataPath);
	
	/**
	 * Gets the config path.
	 *
	 * @return the config path
	 */
	public String getConfigPath();
	
	/**
	 * Gets the data path.
	 *
	 * @return the data path
	 */
	public String getDataPath();
	
	
	/**
	 * Load app meta interface.
	 *
	 * @return The DataSource Object
	 */
	public IDataSource loadAppMetaInterface();
	
	/**
	 * Load depl meta interface.
	 *
	 * @return The DataSource Object
	 */
	public IDataSource loadDeplMetaInterface();
	
	/**
	 * Load metering interface.
	 *
	 * @return The DataSource Object
	 */
	public IDataSource loadMeteringInterface();
	
	/**
	 * Load metering history interface.
	 *
	 * @return The DataSource Object
	 */
	public IDataSource loadMeteringHistoryInterface();
	
	/**
	 * Load topology interface.
	 *
	 * @return The DataSource Object
	 */
	public IDataSource loadTopologyInterface();
	
	
	public IDataSource loadElasticityLogInterface();
	// TODO
	// Add getter for each supported type
}
