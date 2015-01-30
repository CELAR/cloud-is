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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	@Path("/getAgents")
	public Response getMonitoringAgents() 
	{		
		Loader ld = new Loader(context);
		IMetering monitor = (IMetering) ld.getDtCollectorInstance(DataSourceType.MONITORING);
		
		String response = monitor.getAgents("","UP");
		
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Say plain text.
	 *
	 * @return the string
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAgentMetrics/")
	public String getAgentMetrics() 
	{
		Loader ld = new Loader(context);
		IMetering monitor = (IMetering) ld.getDtCollectorInstance(DataSourceType.MONITORING);
		
		monitor.getAgentMetrics("", "579c910e305d4119aeb410b3ef82e400");
		
		return "Compare!!!";
	}
	
	/**
	 * Gets the desision counts.
	 *
	 * @return the desision counts
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test/getSampleMetrics")
	public String getSampleMetrics() 
	{
		Loader ld = new Loader(context);
		IMetering monitor = (IMetering) ld.getDtCollectorInstance(DataSourceType.MONITORING);
		
		//monitor.getValuesForTimeRange("579c910e305d4119aeb410b3ef82e400:netBytesIN", "2000", null, null);
		
		return "Compare!!!";
	}
}
