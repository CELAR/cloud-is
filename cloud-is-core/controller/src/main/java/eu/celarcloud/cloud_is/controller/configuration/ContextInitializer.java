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
package eu.celarcloud.cloud_is.controller.configuration;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import eu.celarcloud.cloud_is.controller.configuration.Config;
import eu.celarcloud.cloud_is.controller.configuration.FileWatcher;

// TODO: Auto-generated Javadoc
/**
 * The Class ContextInitializer.
 */
public class ContextInitializer implements ServletContextListener{
	
	/** The app context. */
	private ServletContext appContext = null;
	
	/** The timer. */
	private Timer timer = null;
	 
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("cloud-is-core: Context destroyed");
		this.appContext = null;
		
		if(this.timer!= null)
		{
			timer.cancel();
			timer.purge();
			this.timer = null;
		}
		
	}
 
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("cloud-is-core: Context started");
		this.appContext = sce.getServletContext();
		
		// Read the ad-hoc properties files
		String configFilePath = this.appContext.getRealPath("config"+File.separator+"server.properties");
		// Set configuration file path to context for later use
		this.appContext.setAttribute("configFilePath", configFilePath);
		
		
		
		//
		readConfigurationProps(configFilePath);
		
		//-
		// Create a folder to store temporary data
		// These tree folders will be created at the first run, and possibly stay unchanged on later update
		// Those folder are used from the controller module or the collectors (connectors)
		// to store temporary data or data files
		String rootPath = this.appContext.getRealPath("/");
		//String warName = new File(this.appContext.getRealPath("/")).getName();
		String dataFolder = rootPath +File.separator+ ".data";
				
		// Create top Level Folder
		File f = new File(dataFolder);
		try	{
			if(f.mkdir()) {
				System.out.println("Directory Created");
		    } else {
		    	System.out.println("Directory is not created");
		    }
			// Assuming that the folder is created or already exists
			// Save folder path to a variable
			this.appContext.setAttribute("gDataPath", dataFolder);
		} catch(Exception e){
		    e.printStackTrace();
		}	
		
		//-
		// monitor a single file
	    TimerTask task = new FileWatcher( new File(configFilePath) ) {
	      protected void onChange( File file ) {
	        // here we code the action on a change
	        System.out.println("cloud-is-core: Reconfigure");
	        readConfigurationProps(file.getPath());
	      }
	    };

	    this.timer = new Timer();
	    // repeat the check every second
	    this.timer.schedule( task , new Date(), 1000 );
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
	
	/**
	 * Read configuration props.
	 *
	 * @param path
	 *            the path
	 */
	private void readConfigurationProps(String path)
	{		
		Config sysCnf = new Config(path);
		// Check in what mode the Information System is working
		// in order to further config the dataCollection Module
		
		// Get Collector Class Name / system running mode and
		// Set it as a context attribute		
		String mode = sysCnf.getProperty("common.mode");
		String collectorType = sysCnf.getProperty("common.collector");
		//this.appContext.setAttribute("collectorName", mode);
		this.appContext.setAttribute("collectorName", collectorType);
	}
	
}

