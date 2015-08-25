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
package eu.celarcloud.cloud_is.controller.services.restful.handlers.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import eu.celarcloud.cloud_is.controller.collectorLoader.Loader;
import eu.celarcloud.cloud_is.controller.services.restful.handlers.RestHandler;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.DataSourceType;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata;

// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 */
@Path("/application")
public class ApplicationInfo  extends RestHandler {	
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
		Loader ld = this.getLoader(context);
		IApplicationMetadata app = (IApplicationMetadata) ld.getDtCollectorInstance(DataSourceType.APPLICATION);
		List<Application> applications = app.getUserApplications();
		
		// Convert to json 
		JSONArray response = new JSONArray();
		for (Application apll : applications) {
			response.put(apll.toJSONObject());
		}
		
		//return response;
		return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the user applications.
	 *
	 * @param appId
	 *            the app id
	 * @return the user applications
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appId}")
	public Response getApplication(@PathParam("appId") String appId) 
	{
		Loader ld = this.getLoader(context);
		IApplicationMetadata app = (IApplicationMetadata) ld.getDtCollectorInstance(DataSourceType.APPLICATION);
		
		//String.format("%010d", uniqueId)
		
		Application response;
		response = app.getApplicationInfo(appId); //0000000002.001.000
		
		//return response;
		return Response.ok(response.toJSONObject().toString(), MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the application versions.
	 *
	 * @param appId
	 *            the app id
	 * @return the application versions
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appId}/version")
	public Response getApplicationVersions(@PathParam("appId") String appId) 
	{
		Loader ld = this.getLoader(context);
		IApplicationMetadata app = (IApplicationMetadata) ld.getDtCollectorInstance(DataSourceType.APPLICATION);
		
		String response;
		response = app.appVersions();
		
		//return response;
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Returns all the deployments of a specific application. Depending the
	 * case, the application id can be given as combined id (version attached)
	 * e.g. appIDXXX.verMajorXXX.versMinorXXX or as two separated parameters
	 * unique application id and version id. Version path parameter
	 * (/version/{versionValue}) is optional
	 *
	 * @param appId
	 *            the application id
	 * @param versId
	 *            the vers id
	 * @return the application version deployments
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{appId}{verId : (/version/[^/]+?)?}/deployment")
	public Response getApplicationDeployments(@PathParam("appId") String appId, @PathParam("versId") String versId) 
	{
		Loader ld = this.getLoader(context);
		IApplicationMetadata app = (IApplicationMetadata) ld.getDtCollectorInstance(DataSourceType.APPLICATION);
		
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
	@Path("/{appId}/topology")
	public Response getApplicationTopology(@PathParam("appId") String appId) 
	{
		Loader ld = this.getLoader(context);
		IApplicationMetadata app = (IApplicationMetadata) ld.getDtCollectorInstance(DataSourceType.APPLICATION);
		
		String response;
		response = app.getVersionTopology(appId);
		
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
	 * @param group
	 *            the group
	 * @param order
	 *            the order
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public Response searchApplications(@QueryParam("submitted_start") long submitted_start, @QueryParam("submitted_end") long submitted_end, @QueryParam("description") String description, 
			@QueryParam("module_name") String module_name, @QueryParam("component_description") String component_description, @QueryParam("provided_resource_id") String provided_resource_id, 
			@QueryParam("group") String group, @QueryParam("order") String order) 
	{
		/*
		Date date = new Date(submitted_start*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
		//sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
		String formattedDate = sdf.format(date);
		System.out.println(formattedDate);
		*/
		
		
		Loader ld = this.getLoader(context);
		IApplicationMetadata app = (IApplicationMetadata) ld.getDtCollectorInstance(DataSourceType.APPLICATION);
		
		List<Application> applications = app.searchApplications(submitted_start, submitted_end, description, module_name, component_description, provided_resource_id);
		// Convert to json 
		JSONArray response = new JSONArray();
		for (Application apll : applications) {
			response.put(apll.toJSONObject());
		}
		
		//return response;
		return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	
}
