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
package eu.celarcloud.cloud_is.dataCollectionModule.impl.common.clients;

import java.io.OutputStream;
import java.net.URISyntaxException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;

import eu.celarcloud.cloud_is.dataCollectionModule.common.RestClient;
import gr.ntua.cslab.celar.server.beans.Component;
import gr.ntua.cslab.celar.server.beans.Metric;
import gr.ntua.cslab.celar.server.beans.MyTimestamp;


// TODO: Auto-generated Javadoc
/**
 * The Class CelarManager.
 */
public class CelarManager {
	
	/** The server ip. */
	private String serverIp;
	
	/** The rest path. */
	private String restPath = "";	
	
	
	/**
	 * Instantiates a new celar manager.
	 *
	 * @param serverIp
	 *            the server ip
	 */
	public CelarManager(String serverIp) 
	{
		  this.serverIp = serverIp;
	}	
	
	/**
	 * Gets the iaas resources.
	 *
	 * @return the iaas resources
	 */
	public void getIaasResources()
	{
//		ClientResponse response;
//		// 
//		response = this.service.path("/iaas/resources").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//		System.out.println(response.getEntity(String.class));
	}	
	
	/**
	 * Gets the iaas quotas.
	 *
	 * @return the iaas quotas
	 */
	public void getIaasQuotas()
	{
//		ClientResponse response;
//		// 
//		response = this.service.path("/iass/quotas").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//		System.out.println(response.getEntity(String.class));
	}	
	
	/**
	 * Gets the iaas actions.
	 *
	 * @return the iaas actions
	 */
	public void getIaasActions()
	{
//		ClientResponse response;
//		// 
//		response = this.service.path("/iaas/actions").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//		System.out.println(response.getEntity(String.class));
	}	
	
	
	
	/**
	 * Gets the action history.
	 *
	 * @return the action history
	 */
	public void getActionHistory()
	{
//		ClientResponse response;
//		// 
//		response = this.service.path("/action/getActionHistory").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//		System.out.println(response.getEntity(String.class));
	}
	
	// // // // // // // // // // // //
	/**
	 * Gets the monitoring probes.
	 *
	 * @return the monitoring probes
	 */
	public void getMonitoringProbes()
	{
//		ClientResponse response;
//		// 
//		response = this.service.path("/iaas/probes").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//		System.out.println(response.getEntity(String.class));
	}
	
	/**
	 * Gets the metrics.
	 *
	 * @return the metrics
	 */
	public String getMetrics(String compId, String metricId)
	{
		if(metricId == null || metricId.isEmpty())
			return null;		
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/metrics/get/" + metricId;		
	    builder.setPath(path);	
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	
	/**
	 * Put metric.
	 *
	 * @param compId
	 *            the component id
	 * @param timestamp
	 *            the timestamp
	 * @param value
	 *            the value
	 * @return the string
	 */
	public String putMetric(String compId, String name)
	{
		if(compId == null || compId.isEmpty())
			return null;		
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/metrics/put/";		
	    builder.setPath(path);	
		
	    //the output to the server
		OutputStream  svrOutput = null;
	    try {
			Component c = new Component(Integer.parseInt(compId));			
			Metric m = new Metric(c);
			
			//m.name = name;

			//unmarshal the metric you created and write it to the output stream
			m.marshal(svrOutput);
			svrOutput.close();//maybe
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	    
	    String body = svrOutput.toString();	    
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executePost(builder.build(), client.ACCEPT_XML, body);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	public String putMetricValue(String compId, String metricId, String timestamp, String value)
	{
		return null;	
	}
	
	// // // // //
	// New Api
	/**
	 * Gets the application info.
	 *
	 * @param appId
	 *            the app id
	 * @return the application info
	 */
	public String getApplicationInfo(String appId)
	{
		if(appId == null || appId.isEmpty())
			return null;		
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/application/" + appId;		
	    builder.setPath(path);	
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	/**
	 * Search applications by property.
	 *
	 * @param submitted_start
	 *            the submitted_start
	 * @param submitted_end
	 *            the submitted_end
	 * @param description
	 *            the description
	 * @param user_id
	 *            the user_id
	 * @param module_name
	 *            the module_name
	 * @param component_description
	 *            the component_description
	 * @param provided_resource_id
	 *            the provided_resource_id
	 * @return the string
	 */
	public String searchApplicationsByProperty(long submitted_start, long	submitted_end, String description, int user_id, 
												String module_name, String component_description, String provided_resource_id )
	{
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/application/search/";
		
	    builder.setPath(path);	 
		
		if(submitted_start != 0)
			builder.setParameter("submitted_start", String.valueOf(submitted_start));
		if(submitted_end != 0)
			builder.setParameter("submitted_end", String.valueOf(submitted_end));
		if(description != null && !description.isEmpty())
			builder.setParameter("description", description);
		if(user_id != 0)
			builder.setParameter("user_id", String.valueOf(user_id));
		if(module_name != null && !module_name.isEmpty())
			builder.setParameter("module_name", module_name);
		if(component_description != null && !component_description.isEmpty())
			builder.setParameter("component_description", component_description);
		if(provided_resource_id != null && !provided_resource_id.isEmpty())
			builder.setParameter("provided_resource_id", provided_resource_id);
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return client.getContent(response);
		
	}
	
	public String searchDeploymentsByProperty(long submitted_start, long submitted_end, String status, int application_id)
	{
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/deployment/search/";
		
		builder.setPath(path);	 
		
		if(submitted_start != 0)
		builder.setParameter("submitted_start", String.valueOf(submitted_start));
		if(submitted_end != 0)
		builder.setParameter("submitted_end", String.valueOf(submitted_end));
		if(status != null && !status.isEmpty())
		builder.setParameter("status", status);
		if(application_id != 0)
		builder.setParameter("user_id", String.valueOf(application_id));	
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
		response = client.executeGet(builder.build());
		} catch (URISyntaxException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	public String getOrchestationVm(String deplId)
	{
		// TODO 
		// Remove Add Hoc variable assignment
		
		return "http://83.212.86.244:8080/JCatascopia-Web";		
	}

}
