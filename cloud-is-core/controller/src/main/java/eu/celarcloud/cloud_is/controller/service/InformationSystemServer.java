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
package eu.celarcloud.cloud_is.controller.service;

import java.io.File;

import org.apache.catalina.LifecycleException;

import eu.celarcloud.cloud_is.controller.configuration.Config;
import eu.celarcloud.cloud_is.controller.container.tomcat.TomcatEmbeddedRunner;
import eu.celarcloud.cloud_is.controller.services.httpproxy.OrchProxyEmbeddedWebapp;
import eu.celarcloud.cloud_is.controller.services.restful.RestAPIembeddedWebapp;

/**
 * The Class InformationSystemServer.
 */
public class InformationSystemServer {
	/** path to configuration file */
	private static final String CONFIG_PATH = File.separator + "config" + File.separator + "server.properties";

	private static final String EMBEDDED_SERVER_PATH = File.separator + "resources";
	
	/** The server home. */
	private String serverHome;
	
	/** The server. */
	public static TomcatEmbeddedRunner server;

	/**
	 * Instantiates a new information system server.
	 *
	 * @param serverDir
	 *            the server dir
	 * @param s2
	 *            the s2
	 * @throws LifecycleException 
	 *             org.apache.catalina.LifecycleException - In case that the tomcat container fail to start
	 */
	public InformationSystemServer(String serverDir, String s2)	throws LifecycleException {
		this.serverHome = serverDir;		
		
		// load config properties file
		Config properties = new Config(this.serverHome  + EMBEDDED_SERVER_PATH + CONFIG_PATH);
		
		//-
		// Test, properties are accessible
		System.out.println(properties.getProperty("test.test_prop", "Can not find property"));
		//
		
		// Get tomcat running port from properties
		int port = Integer.valueOf(properties.getProperty("srv.port", "0")).intValue();
		
		System.out.println("Starting Tomcat ..");
		InformationSystemServer.server = new TomcatEmbeddedRunner(this.serverHome  + EMBEDDED_SERVER_PATH, port);
				
		// Set WebApp to load on server container
		/*  Set the REST API webApp
		 *  Context path equal to '/' meaning that the webApp
		 *  will be 'installed' under the root path
		 */
		RestAPIembeddedWebapp restApi = new RestAPIembeddedWebapp();
		restApi.create(InformationSystemServer.server, "rest");
		// Print newly 'installed' webapp access url
		System.out.println("Server on " + InformationSystemServer.server.getApplicationUrl("rest"));
		/*
		 * 
		 */
		OrchProxyEmbeddedWebapp proxy = new OrchProxyEmbeddedWebapp();
		proxy.create(InformationSystemServer.server, "monitoringProxy");
		// Print newly 'installed' webApp access url
		System.out.println("Server on " + InformationSystemServer.server.getApplicationUrl("monitoringProxy"));
		// Start the server
		// WARNING - The Script will block after this line
		InformationSystemServer.server.start();
	}

}
