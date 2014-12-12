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

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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

import eu.celarcloud.cloud_is.controller.analyticsController.AnalyticsController;
import eu.celarcloud.cloud_is.controller.collectorLoader.Loader;
import eu.celarcloud.cloud_is.analysisModule.Average;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplication;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMonitoring;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader;
import eu.celarcloud.cloud_is.dataCollectionModule.impl.dummy.TestClass;

// TODO: Auto-generated Javadoc
/**
 * The Class AppAnalysis.
 */
@Path("/analysis")
public class AppAnalysis 
{
	
	/** The http request. */
	@Context HttpServletRequest httpRequest;
	
	/** The http response. */
	@Context HttpServletResponse httpResponse;
	
	/** The context. */
	@Context ServletContext context;
	
	/**
	 * Gets the app stats.
	 *
	 * @param deplId
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
	@Path("stats/{deplId}{compId : (/tier/[^/]+?)?}")
	public Response deploymentAnalyticsReport(@PathParam("deplId") String deplId, @PathParam("compId") String compId, 
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
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		IMonitoring monitor = (IMonitoring) ld.getDtCollectorInstance(ISourceLoader.TYPE_MONITORING);
		
		// Get deployment informations
		Deployment dpl = app.getDeployment(deplId);		
		
		// Specify the time windows for which
		// the analysis will take place
		if(sTime == null || sTime.trim().isEmpty())
			sTime = dpl.startTime;
		
		if(eTime == null || eTime.trim().isEmpty())
			eTime = dpl.endTime;
		
		//-		
		int sRate = 500 * 1000; // to ms
		int count = (int) (Long.parseLong(eTime) - Long.parseLong(sTime)) / sRate;		
		
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
		
		// Iterate through selected metrics
		// and calculate analytics (trend)
		for (String metric : metrics) {
			// Init analysis object
			AnalyticsController analysis = new AnalyticsController();
			
			// 
			LinkedHashMap<String, String> trend = analysis.calculateTrend(monitor.getMetricValues(deplId, metric, sTime, eTime));		
			JSONArray rawData = new JSONArray();
			
			for (String name: trend.keySet())
			{
				JSONObject m = new JSONObject();
				m.put("t", name.toString());
				m.put("v", trend.get(name).toString());
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
		
		
		// Add Information about
		// Resizing Decisions Taken
		// to the response
		int min = 15;
		int max = 100;
		JSONObject actions = new JSONObject();
			for(int i = 1; i < 4; i++)
			{
				long tStart = Long.parseLong(dpl.startTime);
				long offset = sRate * TestClass.randInt(min + 1, max - 1);
				long time = tStart + offset;
				actions.put("Action " + i, time);
			}		
		json.put("actions", actions);
		
		// Add Deployment Specific Data
		// to the response
		JSONObject versData = new JSONObject();
			versData.put("tStart", dpl.startTime);
			versData.put("tEnd", dpl.endTime);
			versData.put("sRate", sRate);			
		json.put("version", versData);
		
		
		// Return the Response
		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	/*
	public Response getComponentStats() 
	{
		String application = ApplicationFactory.TYPE_celar;
		
		Loader ld = new Loader(Loader.TYPE_APPLICATION, application);
		ApplicationFactory appFact = (ApplicationFactory) ld.getInstance();
		IApplication app = appFact.getInstance(context);
		
		String response;
		response = app.getVersionTopology();
		
		//return response;
		return Response.ok(response, MediaType.TEXT_PLAIN).build();
	}
	
	public Response getNodeStats() 
	{
		String application = ApplicationFactory.TYPE_celar;
		
		Loader ld = new Loader(Loader.TYPE_APPLICATION, application);
		ApplicationFactory appFact = (ApplicationFactory) ld.getInstance();
		IApplication app = appFact.getInstance(context);
		
		String response;
		response = app.getVersionTopology();
		
		//return response;
		return Response.ok(response, MediaType.TEXT_PLAIN).build();
	}
	*/
}
