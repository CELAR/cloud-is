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

import javax.servlet.ServletContext;

import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.DataSourceType;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDataSource;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader;


/**
 * The Class Loader.
 */
public class Loader {
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Loader.class.getName());
	
	/** The Data source loader. */
	private ISourceLoader DataSourceLoader;
	
	/** The context. */
	private ServletContext context;
	
	/**
	 * Instantiates a new loader.
	 *
	 * @param context
	 *            the context
	 */
	public Loader(ServletContext context)
	{		
		Object classInstance = null;
		
		// Store context for future use
		this.context = context;
		
		// Get Collector's class Name
		String cName = (String) context.getAttribute("collectorName");
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
	 * Gets the dt collector instance.
	 *
	 * @param DataSourceType type
	 *            the collection interface type
	 * @return the appropriate data collector instance
	 */
	public IDataSource getDtCollectorInstance(DataSourceType type)
	{       
        String configPath = context.getRealPath("config"+File.separator);
        
        this.DataSourceLoader.init(configPath, (String) this.context.getAttribute("gDataPath"));        
		return this.DataSourceLoader.getDtCollectorInstance(type);
	}
}
