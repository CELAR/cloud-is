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
 * The Class SourceLoader.
 */
public abstract class SourceLoader {

	/** The global props. 
	 * 	Contains:
	 * 		config: The Config Folder Path
	 * 		data:	The Data Folder Path
	 *  
	 */
	protected Properties globalProps = null;	
	
	/**
	 * Inits the.
	 */
	public void init(){}
	
	/**
	 * Inits the.
	 *
	 * @param props
	 *            the props
	 */
	public void init(Properties props)
	{
		this.globalProps = props;
	}
	
	/**
	 * Inits the.
	 *
	 * @param configPath
	 *            the config path
	 */
	public void init(String configPath)
	{
		this.globalProps = new Properties();
		
		if(configPath == null)
			configPath = "";
		
		this.globalProps.setProperty("configPath", configPath);
	}	
	
	/**
	 * Inits the.
	 *
	 * @param configPath
	 *            the config path
	 * @param dataPath
	 *            the data path
	 */
	public void init(String configPath, String dataPath)
	{
		this.globalProps = new Properties();		 
		
		if(configPath == null)
			configPath = "";
		if(dataPath == null)
			dataPath = "";
		
		this.globalProps.setProperty("configPath", configPath);
		this.globalProps.setProperty("dataPath", dataPath);
	}
	
	public String getConfigPath()
	{
		String path = null;
		if(this.globalProps != null)
			path = this.globalProps.getProperty("configPath");
		return path;		
	}
	
	/**
	 * Gets the data path.
	 *
	 * @return the data path
	 */
	public String getDataPath()
	{
		String path = null;
		if(this.globalProps != null)
			path = this.globalProps.getProperty("dataPath");
		return path;		
	}
}
