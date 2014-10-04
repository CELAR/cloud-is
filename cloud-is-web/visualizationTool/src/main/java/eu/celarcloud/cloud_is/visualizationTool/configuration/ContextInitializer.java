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
package eu.celarcloud.cloud_is.visualizationTool.configuration;

import java.io.File;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// TODO: Auto-generated Javadoc
/**
 * The Class ContextInitializer.
 */
public class ContextInitializer implements ServletContextListener{
	
	private ServletContext appContext = null;
	 
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("cloud-is-web: Context destroyed");
		this.appContext = null;
	}
 
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("cloud-is-web: Context started");
		this.appContext = sce.getServletContext();
		
		String configPath = "config"+File.separator+"init.properties";		
		String path = this.appContext.getRealPath(configPath);
		
		//
		readConfigurationProps(path);
		
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
	
	private void readConfigurationProps(String path)
	{		
		Config sysCnf = new Config(path);
		String prop;
		// Set Information System address to Servlet Context Properties		
		prop = sysCnf.getProperty("isserver.ip") + sysCnf.getProperty("isserver.context");
		this.appContext.setAttribute("isserver", prop);
		
		// Set Visualization Tool path to Servlet Context Properties for name resolving issues
		// !Warning The following code gives the name of the deployed war file in some cases this may not represent the path shown in url
		prop = new File(this.appContext.getRealPath("/")).getName();
		this.appContext.setAttribute("vspath", prop);
	
	}
}
