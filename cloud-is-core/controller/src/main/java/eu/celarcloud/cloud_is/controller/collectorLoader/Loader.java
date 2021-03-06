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
package eu.celarcloud.cloud_is.controller.collectorLoader;

import java.io.File;
import java.util.HashMap;

import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.DataSourceType;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDataSource;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader;


// TODO: Auto-generated Javadoc
/**
 * The Class Loader.
 */
public class Loader {
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Loader.class.getName());
	
	/**
	 * An enumerator of all acceptable parameters
	 * that loader class can get / read at the initialisation
	 */
	public static enum parameters {
		
		/** The collector name. */
		COLLECTOR_NAME,
		
		/** The root path. */
		ROOT_PATH,
		
		/** The temp data path. */
		TEMP_DATA_PATH,
		
		/** The client auth token. */
		CLIENT_AUTH_TOKEN
	}
	
	/** The Data source loader. */
	private ISourceLoader DataSourceLoader;
	
	/** Parameters in a <Key, Value> format. */
	private HashMap<String, String> params;	
	
	/**
	 * Instantiates a new loader.
	 *
	 * @param params
	 *            Initialisation parameter in a <Key, Value> format            
	 */
	public Loader(HashMap<String, String> params)
	{
		this.params = params;
		
		Object classInstance = null;
		
		// Get Collector's class Name
		String cName = (String) this.params.get(Loader.getParameterName(Loader.parameters.COLLECTOR_NAME));
		// Check is collectorName - Possible error on configuration file
		if(cName == null || cName.isEmpty())
		{
			LOG.error("Collector Name is empty. Check your configuration file for errors.");
			return;
		}
			
		// Define a class to be loaded.
		String classNameToBeLoaded = "eu.celarcloud.cloud_is.dataCollectionModule.impl." + cName + ".CollectorLoader";
		
		/*
		Class myClass;
		// Init Class Loader		
		ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();        
		try {
			// Load the class
			myClass = myClassLoader.loadClass(classNameToBeLoaded);
			// create a new instance of that class	        
			whatInstance = myClass.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		*/
		
        try {
        	LOG.info("Loading Data Collection Classes from " + cName + "(" + classNameToBeLoaded + ")");
        	classInstance = Class.forName(classNameToBeLoaded).newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	LOG.info("Not Found " + cName + "(" + classNameToBeLoaded + ")");
            e.printStackTrace();
        }
		
        this.DataSourceLoader = (ISourceLoader) classInstance;
	}	
		
	/**
	 * Gets the Data Collector instance.
	 *
	 * @param type
	 *            the type
	 * @return the appropriate data collector instance
	 */
	public IDataSource getDtCollectorInstance(DataSourceType type)
	{ 
		String configPath = this.params.get(Loader.getParameterName(Loader.parameters.ROOT_PATH)) + "config" + File.separator;
		this.DataSourceLoader.init(configPath, this.params.get(Loader.getParameterName(Loader.parameters.TEMP_DATA_PATH)));
        
        HashMap<String, String> dataSourceParams = new HashMap<String, String>();
        dataSourceParams.put("token", this.params.get(Loader.getParameterName(Loader.parameters.CLIENT_AUTH_TOKEN)));
        
        this.DataSourceLoader.injectParameters(dataSourceParams);		
        return this.DataSourceLoader.getDtCollectorInstance(type);
	}
	
	/**
	 * Resolve the Parameter String name
	 * based on the parameters enumerator value.
	 *
	 * @param p
	 *            A parameter enumerator value to be resolved
	 * @return the parameter name
	 */
	public static String getParameterName(parameters p) {		
		String name = null;
		switch(p)
		{
			case COLLECTOR_NAME :
			{
				name = "collectorName";
				break;
			}
			case ROOT_PATH :
			{
				name = "ROOT_PATH";
				break;
			}
			case TEMP_DATA_PATH :
			{
				name = "TEMP_DATA_PATH";
				break;
			}
			case CLIENT_AUTH_TOKEN :
			{
				name = "CLIENT_AUTH_TOKEN";
				break;
			}
			default :
			{
				LOG.error("Unsupported input");
				break;
			}
		}
		return name;		
	}
}
