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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// TODO: Auto-generated Javadoc
/**
 * The Class ContextInitializer.
 */
public class ContextInitializer implements ServletContextListener{
	
	 
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ServletContextListener destroyed");
	}
 
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ServletContextListener started");
		ServletContext context = sce.getServletContext();
		
		// Read the ad-hoc properties files
		String configPath = "config"+File.separator+"config.properties";		
		String path = context.getRealPath(configPath);
		
		Config sysCnf = new Config(path);
		
		// TODO
		// - Temporary code
		
		// Get Collector Class Name and
		// Set it as a context attribute
		
		String cName = "celar";
		context.setAttribute("collectorName", cName);
		
		
		
		// -
		
		
		
		
		
		// Read services properties
		String configPath2 = "config"+File.separator+"config.services.properties";		
		String path2 = context.getRealPath(configPath2);
		
		Config servCnf = new Config(path2);		
		
		String serviceName = null;
		String serviceEndpointConfigPath = null;
		
		serviceName = "monitoring";
		serviceEndpointConfigPath = "config" + File.separator + "config.endpoint." +   servCnf.getProperty("endpoint." + serviceName) + ".properties";
		context.setAttribute(serviceName, serviceEndpointConfigPath);
		
		serviceName = "historical";
		serviceEndpointConfigPath = "config" + File.separator + "config.endpoint." +   servCnf.getProperty("endpoint." + serviceName) + ".properties";
		context.setAttribute(serviceName, serviceEndpointConfigPath);
		
		serviceName = "application";
		serviceEndpointConfigPath = "config" + File.separator + "config.endpoint." +   servCnf.getProperty("endpoint." + serviceName) + ".properties";
		context.setAttribute(serviceName, serviceEndpointConfigPath);
		
		// Create a folder to store data
		String warName = new File(context.getRealPath("/")).getName();
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
	
}

