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

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse.Status;

import eu.celarcloud.cloud_is.controller.analyticsController.AnalyticsController;
import eu.celarcloud.cloud_is.controller.collectorLoader.Loader;
import eu.celarcloud.cloud_is.controller.configuration.Config;
import eu.celarcloud.cloud_is.controller.services.restful.handlers.RestHandler;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Decision;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.DataSourceType;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDeploymentMetadata;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IElasticityLog;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering;
import eu.celarcloud.cloud_is.dataCollectionModule.common.exception.CommonException;

// TODO: Auto-generated Javadoc
/**
 * The Class AppAnalysis.
 */
@Path("/analysis")
public class Analysis  extends RestHandler {	
	/**
	 * Gets the app stats.
	 *
	 * @param deployment_id
	 *            the depl id
	 * @param compId
	 *            the comp id
	 * @param sTime
	 *            the s time
	 * @param eTime
	 *            the e time
	 * @param metrics
	 *            the metrics
	 * @return the app stats
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{deplId}{compId : (/tier/[^/]+?)?}")
	public Response deploymentMetricAnalytics(@PathParam("deplId") String deployment_id, @PathParam("compId") String compId, 
											@QueryParam("sTime") String sTime, @QueryParam("eTime") String eTime,
											@QueryParam("metrics") List<String> metrics, @QueryParam("method") List<String> method) 
	{
		// Define measuring variables
		// for diagnostic and profiling purposes
		long startTime;
		
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
		
		// Load the appropriate Data Collectors
		Loader ld = this.getLoader(context);
		IDeploymentMetadata deplMeta = (IDeploymentMetadata) ld.getDtCollectorInstance(DataSourceType.DEPLOYMENT);
		IMetering monitor = (IMetering) ld.getDtCollectorInstance(DataSourceType.MONITORING);
		
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
		
		// Try to parse values to long times
		long start_time, end_time;
		try {
			start_time = Long.parseLong(sTime);
			end_time = Long.parseLong(eTime);
	    } catch (NumberFormatException nfe) {
	    	throw nfe;
	    }
		
		
		DecimalFormat df = new DecimalFormat("#.000"); 
		JSONObject json = new JSONObject();
		    
		// Fallback code
		// If metrics list is empty,
		// create one with some default values
		
		System.out.println(metrics);		
		
		if(metrics.size() <= 0)
		{
			metrics = Arrays.asList("cpu", "ram", "disk");
		}
		
		// Init analysis object
		Config sysCnf = new Config((String) context.getAttribute("configFilePath"));
		AnalyticsController analysis = new AnalyticsController(sysCnf.export());
		
		// Iterate through selected metrics
		// and calculate analytics (trend)
		for (String metric : metrics) {
			// 
			startTime = System.nanoTime();
			Number[][] trend = analysis.calculateTrend(monitor.getMetricValues(deployment_id, metric, start_time, end_time));		
			System.out.println("Analysis in: " + (System.nanoTime() - startTime) + " ns");			
			
			startTime = System.nanoTime();
			JSONArray rawData = new JSONArray();
			for (int i=0; i < trend.length; i++)
			{
				JSONObject m = new JSONObject();
				m.put("t", String.valueOf(trend[i][0]));
				m.put("v", String.valueOf(trend[i][1]));
	            rawData.put(m);
			}
			System.out.println("To json in: " + (System.nanoTime() - startTime) + " ns");
			
			//
			double averageValue = 0.0, maxValue = 0.0, minValue=0.0;			
		
			//
			JSONObject prop = new JSONObject();	
				prop.put("max", df.format(maxValue));
				prop.put("min", df.format(minValue));
				prop.put("avg", df.format(averageValue));
				prop.put("data", rawData);
			json.put(metric, prop);		
		}
		
		// Return the Response
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{deplId}{compId : (/tier/[^/]+?)?}/stats")
	public Response deploymentAnalyticsReport(@PathParam("deplId") String deployment_id, @PathParam("compId") String compId, 
											@QueryParam("sTime") String sTime, @QueryParam("eTime") String eTime,
											@QueryParam("metrics") List<String> metrics) 
	{
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
		
		// Load the appropriate Data Collectors
		Loader ld = this.getLoader(context);
		//IApplicationMetadata app = (IApplicationMetadata) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		IDeploymentMetadata deplMeta = (IDeploymentMetadata) ld.getDtCollectorInstance(DataSourceType.DEPLOYMENT);
		IMetering monitor = (IMetering) ld.getDtCollectorInstance(DataSourceType.MONITORING);
		IElasticityLog eLog = (IElasticityLog) ld.getDtCollectorInstance(DataSourceType.ELASTICITY);
		
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
		
		// Try to parse values to long times
		long start_time, end_time;
		try {
			start_time = Long.parseLong(sTime);
			end_time = Long.parseLong(eTime);
	    } catch (NumberFormatException nfe) {
	    	throw nfe;
	    }
		
		DecimalFormat df = new DecimalFormat("#.000"); 
		JSONObject json = new JSONObject();
		    
		// Fallback code
		// If metrics list is empty,
		// create one with some default values
		
		System.out.println(metrics);		
		if(metrics.size() <= 0)
		{
			metrics = Arrays.asList("cpu", "ram", "disk");
		}
		
		// Init analysis object
		Config sysCnf = new Config((String) context.getAttribute("configFilePath"));
		AnalyticsController analysis = new AnalyticsController(sysCnf.export());
		
		// Iterate through selected metrics
		// and calculate analytics (trend)
		for (String metric : metrics) {			
			// 
			Number[][] trend = analysis.calculateTrend(monitor.getMetricValues(deployment_id, metric, start_time, end_time));		
			JSONArray rawData = new JSONArray();
			
			for (int i=0; i < trend.length; i++)
			{
				JSONObject m = new JSONObject();
				m.put("t", String.valueOf(trend[i][0]));
				m.put("v", String.valueOf(trend[i][1]));
	            rawData.put(m);
			}
			
			//
			double averageValue = 0.0, maxValue = 0.0, minValue=0.0;			
		
			//
			JSONObject prop = new JSONObject();	
				prop.put("max", df.format(maxValue));
				prop.put("min", df.format(minValue));
				prop.put("avg", df.format(averageValue));
				prop.put("data", rawData);
			json.put(metric, prop);		
		}
		
		// Check if application
		// - is elastic
		// - elasticity are allow / supported from the collector
		JSONArray dc = new JSONArray();
		if(eLog != null)
		{
			List<Decision> enforcedActions = eLog.getEnforcedActions(deployment_id, compId, "", start_time, end_time);			
			if(enforcedActions != null)
			{
				// Add Information about
				// Resizing Decisions Taken
				// to the response
				for (Decision d : enforcedActions) {
					JSONObject action = new JSONObject();
					action.put("t", d.timestamp);
					action.put("v", d.type);
					dc.put(action);
				}			
			}
		}
		json.put("actions", dc);		
		
		
		// Add Deployment Specific Data
		// to the response
		JSONObject versData = new JSONObject();
			versData.put("tStart", dpl.startTime);
			versData.put("tEnd", dpl.endTime);	
		json.put("version", versData);
		
		
		// Return the Response
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
}
