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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//import org.apache.commons.collections.CollectionUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.celarcloud.cloud_is.controller.collectorLoader.Loader;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.DataSourceType;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class AppCompare.
 */
@Path("/compare")
public class AppCompare 
{
	
	/** The http request. */
	@Context HttpServletRequest httpRequest;
	
	/** The http response. */
	@Context HttpServletResponse httpResponse;
	
	/** The context. */
	@Context ServletContext context;

	// This method is called if TEXT_PLAIN is request
	/**
	 * Say plain text hello.
	 *
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/commonMetrics")
	public Response getCommonMetrics(@QueryParam("data") String data) 
	{		
		Loader ld = new Loader(context);
		IMetering mon = (IMetering) ld.getDtCollectorInstance(DataSourceType.MONITORING_HISTORY);
		
		JSONArray json = new JSONArray();
		JSONObject deploymentArray = new JSONObject(data); 		
		Iterator<?> dkeys = deploymentArray.keys();

		boolean flag = true;
		List<String> metricNames = null;
		while( dkeys.hasNext() ) {
		    String dkey = (String)dkeys.next();
		    if ( deploymentArray.get(dkey) instanceof JSONObject ) {
		    	JSONObject component = (JSONObject) deploymentArray.get(dkey);		
				Iterator<?> ckeys = component.keys();
				while( ckeys.hasNext() ) {
					String ckey = (String)ckeys.next();				    
				    if(flag) {
				    	metricNames = mon.getAvailableMetrics(dkey, ckey);
				    	flag = false;
				    }
				    else
				    	metricNames.retainAll(mon.getAvailableMetrics(dkey, ckey));
				}
		    }
		}
		
		for (String  name: metricNames)
			json.put(name);		
	    
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
}
