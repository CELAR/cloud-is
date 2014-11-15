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
package eu.celarcloud.cloud_is.controllerModule.restApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.celarcloud.cloud_is.controllerModule.services.Loader;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplication;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 */
@Path("/application")
public class ApplicationInfo 
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
	 * @return the user applications
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response getUserApplications() 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		String response;
		response = app.getUserApplications();
		
		//return response;
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the user applications.
	 *
	 * @return the user applications
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appId}")
	public Response getApplication(@PathParam("appId") String appId) 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		//String.format("%010d", uniqueId)
		
		Application response;
		response = app.getApplicationInfo(appId); //0000000002.001.000
		
		//return response;
		return Response.ok(response.toJSONObject().toString(), MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the application versions.
	 *
	 * @return the application versions
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appId}/version")
	public Response getApplicationVersions(@PathParam("appId") String appId) 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		String response;
		response = app.appVersions();
		
		//return response;
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the application version deployments.
	 *
	 * @return the application version deployments
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appId}/deployment")
	public Response getApplicationDeployments(@PathParam("appId") String appId) 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		List<Deployment> deployments = app.getApplicationDeployments(appId);
		// Convert to json
		JSONArray response = new JSONArray();
		for (Deployment depl : deployments) {
			response.put(depl.toJSONObject());
		}
		
		//return response;
		return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	
	/**
	 * Gets the application version deployments.
	 *
	 * @return the application version deployments
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appId}/version/{verId}/deployment")
	public Response getApplicationVersionDeployments() 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		String response;
		response = app.versDeployments();
		
		//return response;
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the deployment info.
	 *
	 * @return the deployment info
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appId}/{verId}/{deploymentId}")
	public Response getDeploymentInfo() 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		String response;
		response = "";
		
		//return response
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the version topology.
	 *
	 * @param appId
	 *            the app id
	 * @param verId
	 *            the ver id
	 * @return the version topology
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appId}/{verId}/topology")
	public Response getVersionTopology(@PathParam("appId") String appId, @PathParam("verId") String verId) 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		
		String response;
		response = app.getVersionTopology(verId);
		
		//return response;
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deployment/{deplId}/topology")
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
	 * Search applications.
	 *
	 * @param submitted_start
	 *            the submitted_start
	 * @param submitted_end
	 *            the submitted_end
	 * @param description
	 *            the description
	 * @param module_name
	 *            the module_name
	 * @param component_description
	 *            the component_description
	 * @param provided_resource_id
	 *            the provided_resource_id
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public Response searchApplications(@QueryParam("submitted_start") long submitted_start, @QueryParam("submitted_end") long submitted_end, @QueryParam("description") String description, 
			@QueryParam("module_name") String module_name, @QueryParam("component_description") String component_description, @QueryParam("provided_resource_id") String provided_resource_id) 
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
		
		List<Application> applications = app.searchApplications(submitted_start, submitted_end, description, module_name, component_description, provided_resource_id);
		// Convert to json 
		JSONArray response = new JSONArray();
		for (Application apll : applications) {
			response.put(apll.toJSONObject());
		}
		
		//return response;
		return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deployments/search")
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
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deployments/recent")
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
}
