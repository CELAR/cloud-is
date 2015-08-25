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
package eu.celarcloud.cloud_is.controller.services.restful.handlers;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import eu.celarcloud.cloud_is.controller.collectorLoader.Loader;

public class RestHandler {
	/** The http request. */
	@Context
	protected HttpServletRequest httpRequest;
	
	/** The http response. */
	@Context
	protected HttpServletResponse httpResponse;
	
	/** The context. */
	@Context
	protected ServletContext context;
	
	@Context
	protected UriInfo uriInfo;
	
	/**
	 * Gets the loader.
	 *
	 * @return the loader
	 */
	public Loader getLoader() {
		return this.getLoader(context);
	}
	
	/**
	 * Gets the loader.
	 *
	 * @param context
	 *            The Servlet Context. Servlet Context is used to 'calculate' real path and 
	 *            access the variables stored during the initialisation of the service.
	 * @return the loader
	 */
	public Loader getLoader(ServletContext context) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(Loader.getParameterName(Loader.parameters.COLLECTOR_NAME), (String) context.getAttribute("collectorName"));
		
		//String configPath = context.getRealPath("config"+File.separator);  
		params.put(Loader.getParameterName(Loader.parameters.ROOT_PATH), context.getRealPath("/"));
		//params.put(Loader.getParameterName(Loader.parameters.ROOT_PATH), context.getRealPath("config"+File.separator));
		//this.DataSourceLoader.init(configPath, (String) this.context.getAttribute("gDataPath"));
		params.put(Loader.getParameterName(Loader.parameters.TEMP_DATA_PATH), (String) context.getAttribute("gDataPath"));		
		
		// Parse the request URI and
		// Pass the token Value to Loader Class as parameter
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
		String token = queryParams.getFirst("token");
		params.put(Loader.getParameterName(Loader.parameters.CLIENT_AUTH_TOKEN), token);
		
		
		return new Loader(params);		
	}
}
