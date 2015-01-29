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

import eu.celarcloud.cloud_is.controller.container.tomcat.TomcatEmbeddedRunner;
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
	 * Gets the single instance of Loader.
	 *
	 * @param sourceType
	 *            the source type
	 * @return single instance of Loader
	 */
	public IDataSource getDtCollectorInstance(Integer sourceType)
	{       
        String configPath = context.getRealPath("config"+File.separator);		
		
        // TODO
     	// The following if-then-else does not belong here
     	// This should go to ISourceLoader (implementation) classes ??
		/*
		String path = null;		
		
		if(sourceType.equals(ISourceLoader.TYPE_MONITORING))
			path = this.context.getRealPath((String) context.getAttribute("monitoring"));
		else if(sourceType.equals(ISourceLoader.TYPE_RESOURCES))
			path = null;	
		else if(sourceType.equals(ISourceLoader.TYPE_APPLICATION))
			path = this.context.getRealPath((String) context.getAttribute("application"));
		else if(sourceType.equals(ISourceLoader.TYPE_MONITORING_HISTORY))
			path = this.context.getRealPath((String) context.getAttribute("application"));
		else if(sourceType.equals(ISourceLoader.TYPE_ELASTICITY))
			path = null;	
		else
			path = null;	
		
		String uri = "";		
		if(path != null)
		{
				EndpointConfig applicationEndpoint = new EndpointConfig(path);
				uri = applicationEndpoint.getUri();
		}
		*/
		
        //-
        // Initialize the DataSourceLoader [SourceLoader] with the 
        // config parameters from the
        // Context / Collector Config Folder
        
        //System.out.println(configPath);
        //System.out.println(this.context.getAttribute("gDataPath"));
        
        this.DataSourceLoader.init(configPath, (String) this.context.getAttribute("gDataPath"));        
		return this.DataSourceLoader.getDtCollectorInstance(sourceType);
	}
}
