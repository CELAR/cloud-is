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

import java.net.URI;

import javax.servlet.ServletException;

import org.apache.http.client.utils.URIUtils;


// TODO: Auto-generated Javadoc
/**
 * A proxy servlet in which the target URI is templated from configuration files using the
 * CELAR Information System configuration context.
 * This implementation extends httpproxy class(by David Smiley dsmiley@mitre.org),
 */
public class MyProxyServlet extends ProxyServlet {
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.controllerModule.httpproxy.ProxyServlet#initTarget()
	 */
	@Override
	protected void initTarget() throws ServletException {
	    targetUri = "http://83.212.86.244:8080/JCatascopia-Web";//getServletConfig().getInitParameter(P_TARGET_URI);
	    //if (targetUri == null)
	      //throw new ServletException(P_TARGET_URI+" is required.");
	    //test it's valid
	    try {
	      targetUriObj = new URI(targetUri);
	    } catch (Exception e) {
	      throw new ServletException("Trying to process targetUri init parameter: "+e,e);
	    }
	    targetHost = URIUtils.extractHost(targetUriObj);
	  }
}
