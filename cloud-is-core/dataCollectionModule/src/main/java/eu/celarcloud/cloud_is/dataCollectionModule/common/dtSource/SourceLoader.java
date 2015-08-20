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

import java.util.HashMap;
import java.util.Properties;

import org.slf4j.LoggerFactory;



// TODO: Auto-generated Javadoc
/**
 * The Class SourceLoader.
 */
public abstract class SourceLoader implements ISourceLoader  {

	/** The global props. 
	 * 	Contains:
	 * 		config: The Config Folder Path
	 * 		data:	The Data Folder Path
	 *  
	 */
	protected Properties globalProps = null;
	
	/** The params. */
	protected HashMap<String, String> params= null;
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SourceLoader.class.getName());
	
	
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
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader#getConfigPath()
	 */
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
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader#injectParameters(java.util.HashMap)
	 */
	public void injectParameters(HashMap<String,String> params)
	{
		this.params = params;
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader#getDtCollectorInstance(eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.DataSourceType)
	 */
	public IDataSource getDtCollectorInstance(DataSourceType type)
	{
		IDataSource DtCollectorInstance = null;
		switch(type)
		{
			case MONITORING :
			{
				DtCollectorInstance = loadMeteringInterface();
				break;
			}
			case MONITORING_HISTORY :
			{
				DtCollectorInstance = loadMeteringHistoryInterface();
				break;
			}
			case RESOURCES : 
			{
				//
				//System.out.println("[Warning] Not Implemented Interface");
				LOG.warn("Not Implemented Interface");
				break;
			}
			case APPLICATION :
			{
				DtCollectorInstance = loadAppMetaInterface();
				break;
			}
			case DEPLOYMENT :
			{
				DtCollectorInstance = loadDeplMetaInterface();
				break;
			}
			case ELASTICITY :
			{
				//
				//System.out.println("[Warning] Not Implemented Interface");
				//LOG.warn("Not Implemented Interface");
				DtCollectorInstance = loadElasticityLogInterface();
				break;
			}
			case TOPOLOGY :
			{
				DtCollectorInstance = loadTopologyInterface();
				break;
			}
			default :
			{
				//System.out.println("[Error] Unsupported input");
				LOG.error("Unsupported input");
				break;
			}
		}
		
		//
		if(DtCollectorInstance == null)
		{
			// Connector does not support this type of end point
			// or failed to initialise connector
			// see backTrace for more info
			LOG.warn("Connector does not support this type of end point or failed to initialise connector\n"
					+ "\tsee backTrace for more info");
			//System.out.println("[Warning] Connector does not support this type of end point or failed to initialise connector\n"
			//		+ "\tsee backTrace for more info");
		}		
		return DtCollectorInstance;
		
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader#loadAppMetaInterface()
	 */
	public abstract IDataSource loadAppMetaInterface();
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader#loadDeplMetaInterface()
	 */
	public abstract IDataSource loadDeplMetaInterface();
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader#loadMeteringInterface()
	 */
	public abstract IDataSource loadMeteringInterface();
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader#loadMeteringHistoryInterface()
	 */
	public abstract IDataSource loadMeteringHistoryInterface();
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader#loadTopologyInterface()
	 */
	public abstract IDataSource loadTopologyInterface();
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader#loadElasticityLogInterface()
	 */
	public abstract IDataSource loadElasticityLogInterface();
}
