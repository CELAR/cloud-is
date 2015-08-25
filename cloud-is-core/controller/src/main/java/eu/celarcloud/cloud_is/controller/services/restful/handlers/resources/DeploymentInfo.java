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

import com.sun.jersey.api.client.ClientResponse.Status;

import eu.celarcloud.cloud_is.controller.collectorLoader.Loader;
import eu.celarcloud.cloud_is.controller.services.restful.handlers.RestHandler;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Decision;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.MetricValue;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.DataSourceType;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDeploymentMetadata;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IElasticityLog;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ITopology;
import eu.celarcloud.cloud_is.dataCollectionModule.common.exception.CommonException;
//import resourceObjects.myHelloObject;


/**
 * The Class DeploymentInfo.
 */
@Path("/deployment")
public class DeploymentInfo extends RestHandler {	
	
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
		Loader ld = this.getLoader(context);
		
		IDeploymentMetadata dep = (IDeploymentMetadata) ld.getDtCollectorInstance(DataSourceType.DEPLOYMENT);
		List<Deployment> deployments = dep.getUserDeployments();
		
		// Convert to json 
		JSONArray response = new JSONArray();
		for (Deployment depl : deployments) {
			response.put(depl.toJSONObject());
		}
		
		//return response;
		return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
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
		
		Loader ld = this.getLoader(context);
		IApplicationMetadata app = (IApplicationMetadata) ld.getDtCollectorInstance(DataSourceType.APPLICATION);
		
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
		Loader ld = this.getLoader(context);
		IDeploymentMetadata deplMeta = (IDeploymentMetadata) ld.getDtCollectorInstance(DataSourceType.DEPLOYMENT);
		
		int l = 0;
		if(limit == null || limit.trim().isEmpty())
			limit = "10";
		
		try {
			l = Integer.parseInt(limit);
	    } catch (NumberFormatException nfe) {
	    	throw nfe;
	    }
		
		List<Deployment> deployments = deplMeta.getRecentDeployments(l, status);
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
	public Response getDeploymentInfo(@PathParam("deplId") String deployment_id) 
	{
		Loader ld = this.getLoader(context);
		IDeploymentMetadata deplMeta = (IDeploymentMetadata) ld.getDtCollectorInstance(DataSourceType.DEPLOYMENT);
		
		Deployment d;
		try {
			d = deplMeta.getDeployment(deployment_id);
		} catch (CommonException e) {
			String err_message = "Deployment " + deployment_id + " does not exist";
			return Response.status(Status.NOT_FOUND).entity(err_message).build();
		}
		
		//return response
		return Response.ok(d.toJSONObject().toString(), MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Gets the deployment topology.
	 *
	 * @param deplId
	 *            the deployment
	 * @return the deployment topology
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{deplId}/topology")
	public Response getDeploymentTopology(@PathParam("deplId") String deployment_id) 
	{		
		Loader ld = this.getLoader(context);
		ITopology topology = (ITopology) ld.getDtCollectorInstance(DataSourceType.TOPOLOGY);
		
		String response;
		try {
			response = topology.getTopology(deployment_id);
		} catch (CommonException e) {
			String err_message = "Topology for Deployment " + deployment_id + " does not exist";
			return Response.status(Status.NOT_FOUND).entity(err_message).build();
		}
		
		//return response;
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{deplId}{compId : (/tier/[^/]+?)?}/decision/{name : ([^/]+?)?}")
	public Response getDeploymentDecisions(@PathParam("deplId") String deployment_id, @PathParam("compId") String compId, @PathParam("name") String name,
										@QueryParam("sTime") String sTime, @QueryParam("eTime") String eTime) 
	{
		Loader ld = this.getLoader(context);
		IElasticityLog elasticityLog = (IElasticityLog) ld.getDtCollectorInstance(DataSourceType.ELASTICITY);
		
		// Load the Deployment Data if only ONE of start - end timestamps are empty
		if(sTime == null || sTime.trim().isEmpty() || eTime == null || eTime.trim().isEmpty()) {
			IDeploymentMetadata deplMeta = (IDeploymentMetadata) ld.getDtCollectorInstance(DataSourceType.DEPLOYMENT);
			
			// Get deployment informations
			Deployment dpl;
			try {
				dpl = deplMeta.getDeployment(deployment_id);
			} catch (CommonException e) {
				String err_message = "Deployment " + deployment_id + " does not exist";
				return Response.status(Status.NOT_FOUND).entity(err_message).build();
			}		
			
			// Specify the time windows for which
			// the analysis will take place
			if(sTime == null || sTime.trim().isEmpty())
				sTime = dpl.startTime;
			
			if(eTime == null || eTime.trim().isEmpty())
				eTime = dpl.endTime;
		}		
		
		// Try to parse values to long times
		long start_time, end_time;
		try {
			start_time = Long.parseLong(sTime);
			end_time = Long.parseLong(eTime);
	    } catch (NumberFormatException nfe) {
	    	throw nfe;
	    }
		
		/*
		 *	/tier/{componentId} is an optional parameter
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
		
		/*
		 *	/{name} is an optional parameter
		 *	If it is function will return
		 *	data regarding the specific resizing action name
		 *	In any other case this function will return 
		 *	data regarding all the enforced action during the deployment
		 */
		if (name.equals("")) {
			// Optional parameter not specified
			// System.out.println("No format specified.");
		} else {
			 // Optional parameter has looks like "/{name}" - need to get it's value only
			 name = name.split("/")[2];
			 //System.out.println("action name: " + name);	
		}
		
		List<Decision> decisions = elasticityLog.getEnforcedActions(deployment_id, compId, name, start_time, end_time);
		
		JSONArray json = new JSONArray();
		for (Decision  decision: decisions)
			json.put(decision.toJSONObject());
		
		//return response;
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the deployment instances.
	 *
	 * @param deployment_id
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
	public Response getDeploymentInstances(@PathParam("deplId") String deployment_id, @PathParam("compId") String compId, 
										@QueryParam("sTime") String sTime, @QueryParam("eTime") String eTime) 
	{
		Loader ld = this.getLoader(context);
		IDeploymentMetadata deplMeta = (IDeploymentMetadata) ld.getDtCollectorInstance(DataSourceType.DEPLOYMENT);
		
		// Get deployment informations
		Deployment dpl;
		try {
			dpl = deplMeta.getDeployment(deployment_id);
		} catch (CommonException e1) {
			String err_message = "Deployment " + deployment_id + " does not exist";
			return Response.status(Status.NOT_FOUND).entity(err_message).build();
		}		
		
		// Specify the time windows for which
		// the analysis will take place
		if(sTime == null || sTime.trim().isEmpty())
			sTime = dpl.startTime;
		
		if(eTime == null || eTime.trim().isEmpty())
			eTime = dpl.endTime;
		
		// Try to parse values to long times
		long start_time, end_time;
		try {
			start_time = Long.parseLong(sTime);
			end_time = Long.parseLong(eTime);
	    } catch (NumberFormatException nfe) {
	    	throw nfe;
	    }
		
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
		
		List<MetricValue> instances = null;
		try {
			 instances = deplMeta.getDeploymentInstances(deployment_id, compId, start_time, end_time);
		}
		catch(java.lang.UnsupportedOperationException e) {
			String err_message = "Deployment " + deployment_id + " has no Instances";
			return Response.status(Status.NOT_FOUND).entity(err_message).build();
		}
		
		JSONArray json = new JSONArray();
		for (MetricValue  metric: instances)
			json.put(metric.toJSONObject());		
		
		//return response;
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * Gets the deployment cost.
	 *
	 * @param deployment_id
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
	public Response getDeploymentCost(@PathParam("deplId") String deployment_id, @PathParam("compId") String compId, 
										@QueryParam("sTime") String sTime, @QueryParam("eTime") String eTime) 
	{
		Loader ld = this.getLoader(context);
		IMetering mon = (IMetering) ld.getDtCollectorInstance(DataSourceType.MONITORING_HISTORY);
		
		// Load the Deployment Data if only ONE of start - end timestamps are empty
		if(sTime == null || sTime.trim().isEmpty() || eTime == null || eTime.trim().isEmpty()) {
			IDeploymentMetadata deplMeta = (IDeploymentMetadata) ld.getDtCollectorInstance(DataSourceType.DEPLOYMENT);
			
			// Get deployment informations
			Deployment dpl;
			try {
				dpl = deplMeta.getDeployment(deployment_id);
			} catch (CommonException e) {
				String err_message = "Deployment " + deployment_id + " does not exist";
				return Response.status(Status.NOT_FOUND).entity(err_message).build();
			}		
			
			// Specify the time windows for which
			// the analysis will take place
			if(sTime == null || sTime.trim().isEmpty())
				sTime = dpl.startTime;
			
			if(eTime == null || eTime.trim().isEmpty())
				eTime = dpl.endTime;
		}
		
		// Try to parse values to long times
		long start_time, end_time;
		try {
			start_time = Long.parseLong(sTime);
			end_time = Long.parseLong(eTime);
	    } catch (NumberFormatException nfe) {
	    	throw nfe;
	    }
				
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
		
		List<MetricValue> costData = null;
		try {
			costData = mon.getDeploymentCost(deployment_id, compId, start_time, end_time);
		}
		catch(java.lang.UnsupportedOperationException e) {
			String err_message = "Cost Data Not Available for Deployment " + deployment_id;
			return Response.status(Status.NOT_FOUND).entity(err_message).build();
		}
		
				
		JSONArray json = new JSONArray();
		for (MetricValue  metric: costData)
			json.put(metric.toJSONObject());		
		
		//return response;
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{deplId}{compId : (/tier/[^/]+?)?}/metrics")
	public Response getDeploymentAvailableMetrics(@PathParam("deplId") String deployment_id, @PathParam("compId") String compId, 
										@QueryParam("sTime") String sTime, @QueryParam("eTime") String eTime) 
	{
		Loader ld = this.getLoader(context);
		IMetering mon = (IMetering) ld.getDtCollectorInstance(DataSourceType.MONITORING_HISTORY);
		
		// Load the Deployment Data if only ONE of start - end timestamps are empty
		if(sTime == null || sTime.trim().isEmpty() || eTime == null || eTime.trim().isEmpty()) {
			IDeploymentMetadata deplMeta = (IDeploymentMetadata) ld.getDtCollectorInstance(DataSourceType.DEPLOYMENT);
			
			// Get deployment informations
			Deployment dpl;
			try {
				dpl = deplMeta.getDeployment(deployment_id);
			} catch (CommonException e) {
				String err_message = "Deployment " + deployment_id + " does not exist";
				return Response.status(Status.NOT_FOUND).entity(err_message).build();
			}		
			
			// Specify the time windows for which
			// the analysis will take place
			if(sTime == null || sTime.trim().isEmpty())
				sTime = dpl.startTime;
			
			if(eTime == null || eTime.trim().isEmpty())
				eTime = dpl.endTime;
		}
		
		// Try to parse values to long times\
		/*
		long start_time, end_time;
		try {
			start_time = Long.parseLong(sTime);
			end_time = Long.parseLong(eTime);
	    } catch (NumberFormatException nfe) {
	    	throw nfe;
	    }
		*/
		
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
		
		List<String> metricNames = null;
		try {
			metricNames = mon.getAvailableMetrics(deployment_id, compId);
		}
		catch(java.lang.UnsupportedOperationException e) {
			String err_message = "No Metrics Available for Deployment " + deployment_id;
			return Response.status(Status.NOT_FOUND).entity(err_message).build();
		}
		
		JSONArray json = new JSONArray();
		for (String  name: metricNames)
			json.put(name);		
		
		//return response;
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();		
	}
}
