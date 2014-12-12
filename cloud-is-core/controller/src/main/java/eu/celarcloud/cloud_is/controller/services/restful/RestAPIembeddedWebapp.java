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
package eu.celarcloud.cloud_is.controller.services.restful;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import eu.celarcloud.cloud_is.controller.container.tomcat.ITomcatWebapp;
import eu.celarcloud.cloud_is.controller.container.tomcat.TomcatEmbeddedRunner;

public class RestAPIembeddedWebapp implements ITomcatWebapp{
	
	/*
	 * Class path to the package where
	 * REST Call Handlers (jersey annotated) are implemented
	 * jersey resource loader class, will load them automatically
	 */
	private final String restHandlersClassPath = "eu.celarcloud.cloud_is.controller.services.restful.handlers";
	
	/*
	 * Class path to the ServletContextListener implementation
	 */
	private final String contextListenerClassPath = "eu.celarcloud.cloud_is.controller.configuration.ContextInitializer";
	
	/*
	 * Class path to ContainerResponseFilter
	 * This jersey specific response filter alter every response
	 * generated from jersey annotated REST Call Handlers
	 * 
	 * Adds CORS (cross domain calls) header to every response
	 */
	private final String corsFilterClassPath = "eu.celarcloud.cloud_is.controller.services.restful.ResponseCorsFilter";
	
	
	public void create(TomcatEmbeddedRunner embeddedRunner) {
		this.create(embeddedRunner, "");
	}
	
	/**
	 * Creates the.
	 *
	 * @param embeddedRunner
	 *            the embedded runner
	 * @param contextPath
	 *            the context path
	 */
	public void create(TomcatEmbeddedRunner embeddedRunner, String contextPath) {
		// Give the default value
		if(contextPath == null || contextPath.isEmpty())
			contextPath = "/rest";
		// Check for leading slash
		if (!contextPath.startsWith("/")) {
			contextPath = "/" + contextPath;
		}
		
		Context rootCtx = embeddedRunner.getServer().addContext(contextPath, embeddedRunner.getServerBase().getAbsolutePath());				
		rootCtx.addApplicationListener(this.contextListenerClassPath);	
		
		
		/* We are instruction tomcat to load all the classes / methods
		 * for handling the REST API calls, 
		 * the following lines of (java) code are equivalent to:		   
		 *  <servlet>
		 *       <servlet-name>Jersey Web Application</servlet-name>
		 *      <servlet-class>com.sun.jersey.spi.container.servlet.ServletContainer</servlet-class>
		 *       <init-param>
		 *           <param-name>com.sun.jersey.config.property.packages</param-name>
		 *           <param-value>cloud_is.controllerModule.servlet</param-value>
		 *       </init-param>
		 *       <load-on-startup>1</load-on-startup>
		 *  </servlet>
		 *  
		 */
		Wrapper a = Tomcat.addServlet(rootCtx, "rest", "com.sun.jersey.spi.container.servlet.ServletContainer");
		a.addInitParameter("com.sun.jersey.config.property.packages", this.restHandlersClassPath);		
		// Support cross domain requests
		a.addInitParameter("com.sun.jersey.spi.container.ContainerResponseFilters", this.corsFilterClassPath); 
		a.setLoadOnStartup(1);
		rootCtx.addServletMapping("/*", "rest");
	}

}
