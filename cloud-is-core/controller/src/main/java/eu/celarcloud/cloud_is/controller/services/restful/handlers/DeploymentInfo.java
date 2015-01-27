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

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import resourceObjects.myHelloObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import eu.celarcloud.cloud_is.controller.collectorLoader.Loader;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplication;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMonitoring;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader;


// TODO: Auto-generated Javadoc
/**
 * The Class Resources.
 */
@Path("/deployment")
public class DeploymentInfo 
{	
	
	/** The http request. */
	@Context HttpServletRequest httpRequest;
	
	/** The http response. */
	@Context HttpServletResponse httpResponse;
	
	/** The context. */
	@Context ServletContext context;
	
	/**
	 * Gets the user applications.
	 *
	 * @return A list of 'user' deployments
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response getUserDeployments() 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		String response;
		response = app.getUserApplications();
		
		//return response;
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Search deployments.
	 *
	 * @param start_time
	 *            the start_time
	 * @param end_time
	 *            the end_time
	 * @param status
	 *            the status
	 * @param application_id
	 *            the application_id
	 * @return A list of 'user' deployments matching the search criteria
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public Response searchDeployments(@QueryParam("start_time") long start_time, @QueryParam("end_time") long end_time, 
									@QueryParam("status") String status, @QueryParam("application_id") String application_id) 
	{
		/*
		Date date = new Date(submitted_start*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
		//sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
		String formattedDate = sdf.format(date);
		System.out.println(formattedDate);
		*/
		
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		List<Deployment> deployments = app.searchDeployments(application_id, start_time, end_time, status);
		// Convert to json
		JSONArray response = new JSONArray();
		for (Deployment depl : deployments) {
			response.put(depl.toJSONObject());
		}
		
		//return response;
		return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Recent deployments.
	 *
	 * @param limit
	 *            the limit
	 * @param status
	 *            the status
	 * @return A list of 'user' deployments
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/recent")
	public Response recentDeployments(@QueryParam("limit") String limit, @QueryParam("status") String status) 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		List<Deployment> deployments = app.getRecentDeployments(limit, status);
		// Convert to json
		JSONArray response = new JSONArray();
		for (Deployment depl : deployments) {
			response.put(depl.toJSONObject());
		}
		
		//return response;
		return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the deployment info.
	 *
	 * @param deplId
	 *            the depl id
	 * @return the deployment info
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{deplId}")
	public Response getDeploymentInfo(@PathParam("deplId") String deplId) 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		String response;
		response = "";
		
		//return response
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Gets the deployment topology.
	 *
	 * @param deplId
	 *            the depl id
	 * @return the deployment topology
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{deplId}/topology")
	public Response getDeploymentTopology(@PathParam("deplId") String deplId) 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		Deployment dpl = app.getDeployment(deplId);
		
		String response;
		response = app.getVersionTopology(dpl.applicationId);
		
		//return response;
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the deployment instances.
	 *
	 * @param deplId
	 *            the depl id
	 * @param compId
	 *            the comp id
	 * @param sTime
	 *            the s time
	 * @param eTime
	 *            the e time
	 * @return the deployment instances
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{deplId}{compId : (/tier/[^/]+?)?}/instances")
	public Response getDeploymentInstances(@PathParam("deplId") String deplId, @PathParam("compId") String compId, 
										@QueryParam("sTime") String sTime, @QueryParam("eTime") String eTime) 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		Long sTime_long = (long) 0;
		if(sTime != null && !sTime.trim().isEmpty())
			sTime_long = Long.parseLong(sTime);
		
		Long eTime_long = (long) 0;
		if(eTime != null && !eTime.trim().isEmpty())
			eTime_long = Long.parseLong(eTime);		
		
		
		/*
		 *	/tier/{cmponetId} is an optional parameter
		 *	If it is function will return
		 *	data regarding the specific component / tier
		 *	In any other case this function will return 
		 *	data regarding the whole deployment
		 */
		if (compId.equals("")) {
			// Optional parameter not specified
			// System.out.println("No format specified.");
		} else {
			 // Optional parameter has looks like "/tier/{cmponetId}" - need to get it's value only
			 compId = compId.split("/")[2];
			 //System.out.println("compId: " + compId);	
		}
		
		List<Metric> instances = app.getDeploymentInstances(deplId, compId, sTime_long, eTime_long);
		
		JSONArray json = new JSONArray();
		for (Metric  metric: instances)
			json.put(metric.toJSONObject());		
		
		//return response;
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the deployment cost.
	 *
	 * @param deplId
	 *            the depl id
	 * @param compId
	 *            the comp id
	 * @param sTime
	 *            the s time
	 * @param eTime
	 *            the e time
	 * @return the deployment cost
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{deplId}{compId : (/tier/[^/]+?)?}/cost")
	public Response getDeploymentCost(@PathParam("deplId") String deplId, @PathParam("compId") String compId, 
										@QueryParam("sTime") String sTime, @QueryParam("eTime") String eTime) 
	{
		Loader ld = new Loader(context);
		IMonitoring mon = (IMonitoring) ld.getDtCollectorInstance(ISourceLoader.TYPE_MONITORING_HISTORY);
		
		Long sTime_long = (long) 0;
		if(sTime != null && !sTime.trim().isEmpty())
			sTime_long = Long.parseLong(sTime);
		
		Long eTime_long = (long) 0;
		if(eTime != null && !eTime.trim().isEmpty())
			eTime_long = Long.parseLong(eTime);		
		
		
		/*
		 *	/tier/{cmponetId} is an optional parameter
		 *	If it is function will return
		 *	data regarding the specific component / tier
		 *	In any other case this function will return 
		 *	data regarding the whole deployment
		 */
		if (compId.equals("")) {
			// Optional parameter not specified
			// System.out.println("No format specified.");
		} else {
			 // Optional parameter has looks like "/tier/{cmponetId}" - need to get it's value only
			 compId = compId.split("/")[2];
			 //System.out.println("compId: " + compId);	
		}
		
		List<Metric> instances = mon.getDeploymentCost(deplId, compId, sTime_long, eTime_long);
		
		JSONArray json = new JSONArray();
		for (Metric  metric: instances)
			json.put(metric.toJSONObject());		
		
		//return response;
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	
}