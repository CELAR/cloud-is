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
package eu.celarcloud.cloud_is.controllerModule.configuration;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import eu.celarcloud.cloud_is.controllerModule.configuration.Config;
import eu.celarcloud.cloud_is.controllerModule.configuration.FileWatcher;

// TODO: Auto-generated Javadoc
/**
 * The Class ContextInitializer.
 */
public class ContextInitializer implements ServletContextListener{
	private ServletContext appContext = null;
	 
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("cloud-is-core: Context destroyed");
		this.appContext = null;
	}
 
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ServletContextListener started");
		this.appContext = sce.getServletContext();
		
		// Read the ad-hoc properties files
		String configPath = "config"+File.separator+"config.properties";		
		String path = this.appContext.getRealPath(configPath);
		
		//
		readConfigurationProps(path);
		
		// Create a folder to store temporary data
		String warName = new File(this.appContext.getRealPath("/")).getName();
		String fileName = warName + ".data";
		
		File f = new File(fileName);
		try	{
			if(f.mkdir()) {
				System.out.println("Directory Created");
		    } else {
		    	System.out.println("Directory is not created");
		    }
			
		} catch(Exception e){
		    e.printStackTrace();
		} 
				
		
		// monitor a single file
	    TimerTask task = new FileWatcher( new File(path) ) {
	      protected void onChange( File file ) {
	        // here we code the action on a change
	        System.out.println("cloud-is-web: Reconfigure");
	        readConfigurationProps(file.getPath());
	      }
	    };

	    Timer timer = new Timer();
	    // repeat the check every second
	    timer.schedule( task , new Date(), 1000 );
	}
	
	/**
	 * Removes the directory.
	 *
	 * @param dirPath
	 *            the dir path
	 */
	public static void removeDirectory(String dirPath) {
		//String dirPath = "D:/Dir/To/Remove";
		File dir = new File(dirPath);
		removeDirectory(dir);
	}
	
	/**
	 * Removes the directory.
	 *
	 * @param dir
	 *            the dir
	 */
	public static void removeDirectory(File dir) {
	    if (dir.isDirectory()) {
	        File[] files = dir.listFiles();
	        if (files != null && files.length > 0) {
	            for (File aFile : files) {
	                removeDirectory(aFile);
	            }
	        }
	     	//dir.delete();
	        deleteDir(dir);
	    } else {
	    	//dir.delete();
	        deleteDir(dir);
	    }
	}
	
	/**
	 * Delete dir.
	 *
	 * @param dir
	 *            the dir
	 */
	public static void deleteDir(File dir)
	{
		try {
		    boolean deleted = dir.delete();
		    if (deleted) {
		        System.out.println("Directory removed.");
		    } else {
		        System.out.println("Directory could not be removed");
		    }	
		} catch (SecurityException  ex) {
		    System.err.println("Delete is denied.");
		}
	}
	
	private void readConfigurationProps(String path)
	{		
		Config sysCnf = new Config(path);		
		// Check in what mode the Information System is working
		// in order to further config the dataCollection Module
		
		// Get Collector Class Name / system running mode and
		// Set it as a context attribute		
		String mode = sysCnf.getProperty("mode");
		this.appContext.setAttribute("collectorName", mode);
		
		// TODO
		// The code below reading some configuration properties,
		// which are needed from the the dataCollection Module
		// should be moved there
		
		// *hint the data collection module should take as
		// initialization argument the path to the configuration files
		
		// Read services properties
		// TODO Beware add-hoc path
		String configPath2 = "config"+File.separator+"celar"+File.separator+"services.properties";		
		String path2 = this.appContext.getRealPath(configPath2);
		
		Config servCnf = new Config(path2);		
		
		String serviceName = null;
		String serviceEndpointConfigPath = null;
		
		serviceName = "monitoring";
		serviceEndpointConfigPath = "config"+File.separator+"celar" + File.separator + "endpoint." +   servCnf.getProperty("endpoint." + serviceName) + ".properties";
		this.appContext.setAttribute(serviceName, serviceEndpointConfigPath);
		
		serviceName = "historical";
		serviceEndpointConfigPath = "config"+File.separator+"celar" + File.separator + "endpoint." +   servCnf.getProperty("endpoint." + serviceName) + ".properties";
		this.appContext.setAttribute(serviceName, serviceEndpointConfigPath);
		
		serviceName = "application";
		serviceEndpointConfigPath = "config"+File.separator+"celar" + File.separator + "endpoint." +   servCnf.getProperty("endpoint." + serviceName) + ".properties";
		this.appContext.setAttribute(serviceName, serviceEndpointConfigPath);
	
	}
	
}

