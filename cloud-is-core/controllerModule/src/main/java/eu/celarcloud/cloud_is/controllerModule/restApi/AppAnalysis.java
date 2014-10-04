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

import java.math.BigInteger;
import java.text.DecimalFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.celarcloud.cloud_is.analysisModule.Average;
import eu.celarcloud.cloud_is.controllerModule.services.Loader;
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
	 * @return the app stats
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("stats/{appId}/{verId}/{deplId}")
	public Response getAppStats() 
	{
		Loader ld = new Loader(context);
		IApplication app = (IApplication) ld.getDtCollectorInstance(ISourceLoader.TYPE_APPLICATION);
		IMonitoring monitor = (IMonitoring) ld.getDtCollectorInstance(ISourceLoader.TYPE_MONITORING);
		
		String response;
		JSONObject prop;
		JSONArray rawData;
		int min = 0, max = 0, count = 100;
		double randNum, averageValue = 0.0, maxValue = 0.0, minValue=0.0;
		DecimalFormat df = new DecimalFormat("#.000"); 
		JSONObject json = new JSONObject();
		    
		String[] metricNames = {"cpu", "ram", "disk"};
		for(int index = 0; index < metricNames.length; index++) 
		{
			rawData = new JSONArray();
			randNum = 0.0;
			min = 15;
			max = 100;
			for(int i = 0; i < count; i++)
			{
				if(i == 0)
				{
					randNum = TestClass.randDouble(min, max);
					maxValue = randNum; minValue = randNum; averageValue  = randNum;
				}
				else
				{
					randNum = TestClass.randDoubleKnoledge((int) Math.round(randNum), min, max);
					averageValue = Average.incrementalAverage(averageValue, randNum);
				}
				
				rawData.put(randNum);				
				minValue = randNum < minValue ? randNum : minValue;
				maxValue = randNum > maxValue ? randNum : maxValue;
			}
		
			prop = new JSONObject();	
				prop.put("max", df.format(maxValue));
				prop.put("min", df.format(minValue));
				prop.put("avg", df.format(averageValue));
				
				//data = new JSONArray(rawData);
				prop.put("data", rawData);
			json.put(metricNames[index], prop);		
		}
		
		//
		
		// dummy calculations
		// Find the start and the end of the deployment
		BigInteger tStart = new BigInteger("1401036023");
		int sRate = 500 * 1000; // to ms
		BigInteger durration = new BigInteger(String.valueOf((sRate * count)));
		BigInteger tEnd = tStart.add(durration);
		//	
		
		// actions
		JSONObject actions = new JSONObject();
			for(int i = 1; i < 4; i++)
			{
				BigInteger offset = new BigInteger(String.valueOf((sRate * TestClass.randInt(min + 1, max - 1))));
				BigInteger time = tStart.add(offset);
				actions.put("Action " + i, time);
			}		
		json.put("actions", actions);
		
		// version data
				
		JSONObject versData = new JSONObject();
			versData.put("tStart", tStart);
			versData.put("tEnd", tEnd);
			versData.put("sRate", sRate);			
		json.put("version", versData);
		
		
    	response =  json.toString();
		//return response;
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
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
