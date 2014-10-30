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
package eu.celarcloud.cloud_is.controllerModule.httpproxy;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIUtils;

import eu.celarcloud.cloud_is.dataCollectionModule.common.EndpointConfig;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader;
import eu.celarcloud.cloud_is.dataCollectionModule.impl.celar.CollectorLoader;
import eu.celarcloud.cloud_is.dataCollectionModule.impl.common.clients.CelarManager;


// TODO: Auto-generated Javadoc
/**
 * A proxy servlet in which the target URI is templated from configuration files using the
 * CELAR Information System configuration context.
 * This implementation extends httpproxy class(by David Smiley dsmiley@mitre.org),
 */
public class MyProxyServlet extends ProxyServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7432911246743384504L;
		
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deplId = request.getParameter("deplId");
		
		// TODO
		// The following is an ad hoc part of code
		// assuming that the IS runs over CELAR
		// In later version this should be change in a more unified solution
		String path = "config"+File.separator+"celar" + File.separator + "endpoint.celarmanager.properties";		
		String configPath = this.getServletContext().getRealPath(path);
		
		EndpointConfig applicationEndpoint = new EndpointConfig(configPath);			
		String cmUri = applicationEndpoint.getUri();
				
		CelarManager cmClient = new CelarManager(cmUri);
		String targetUri = cmClient.getOrchestationVm(deplId);
		// - End
		
		// Initalize the proxy
	    //targetUri = "http://83.212.86.244:8080/JCatascopia-Web";//getServletConfig().getInitParameter(P_TARGET_URI);
	    if(targetUri == null || targetUri.isEmpty())	
	      throw new ServletException(targetUri +" is required.");
	    
	    //test it's valid
	    try {
	      targetUriObj = new URI(targetUri);
	    } catch (Exception e) {
	      throw new ServletException("Trying to process targetUri init parameter: "+e,e);
	    }
	    targetHost = URIUtils.extractHost(targetUriObj);
		
		// Cann the parent service method
	    try {
	    	super.service(request, response);
	    } catch (Exception e) {
	    	throw new ServletException("Connection to " + targetUri + " failed with " + e,e );	    	
	    }
	}
		
	/* 
	 * Omits the initTarget() function of the parent class
	 * The needed configuration has been moved to service(), 
	 * because it needs user defined parameters (taken from the request)
	 * to initialize the proxy servlet
	 */
	@Override
	protected void initTarget() throws ServletException {}
}
