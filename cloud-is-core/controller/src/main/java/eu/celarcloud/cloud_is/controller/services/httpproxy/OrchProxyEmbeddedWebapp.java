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
package eu.celarcloud.cloud_is.controller.services.httpproxy;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import eu.celarcloud.cloud_is.controller.container.tomcat.ITomcatWebapp;
import eu.celarcloud.cloud_is.controller.container.tomcat.TomcatEmbeddedRunner;

public class OrchProxyEmbeddedWebapp implements ITomcatWebapp{
	
	/*
	 * Class path to the package where
	 * REST Call Handlers (jersey annotated) are implemented
	 * jersey resource loader class, will load them automatically
	 */
	private final String proxyImplClassPath = "eu.celarcloud.cloud_is.controller.services.httpproxy.MyProxyServlet";
	
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
			contextPath = "/monitoringProxy";
		// Check for leading slash
		if (!contextPath.startsWith("/")) {
			contextPath = "/" + contextPath;
		}
		
		Context rootCtx = embeddedRunner.getServer().addContext(contextPath, embeddedRunner.getServerBase().getAbsolutePath());				
		
		/* We are instruction tomcat to load all the classes / methods
		 * for handling the REST API calls, 
		 * the following lines of (java) code are equivalent to:		   
		 *  <servlet>
		 *  	<servlet-name>monitoringProxy</servlet-name>
		 *  	<servlet-class>eu.celarcloud.cloud_is.controllerModule.httpproxy.MyProxyServlet</servlet-class>
		 *  	<init-param>
		 *  		<param-name>log</param-name>
		 *  	    <param-value>false</param-value>
		 *  	</init-param>
		 *  </servlet>
		 *  <servlet-mapping>
		 *  	<servlet-name>monitoringProxy</servlet-name>
		 *  	<url-pattern>/monitoringProxy/*</url-pattern>
		 *  </servlet-mapping>
		 */
		Wrapper a = Tomcat.addServlet(rootCtx, "monitoringProxy", this.proxyImplClassPath);
		a.addInitParameter("log", "true");		
		rootCtx.addServletMapping("/*", "monitoringProxy");
	}

}
