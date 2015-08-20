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
package eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.RestClient;

import gr.ntua.cslab.celar.server.beans.Component;
import gr.ntua.cslab.celar.server.beans.Metric;
import gr.ntua.cslab.celar.server.beans.MetricValue;
import gr.ntua.cslab.celar.server.beans.MyTimestamp;
import gr.ntua.cslab.celar.server.beans.structured.REList;

/**
 * The CELAR Manager Client Class Provides a client / wrapper to access the
 * CELAR Manager REST API.
 */
public class CelarManager {
	
	/**
	 * The server ip. 
	 */
	private String serverIp;
	
	/**
	 *The rest path.
	 */
	private String restPath = "";	
	
	/**
	 * Flag indicating wherever the REST Client will add an authentication token
	 * in the URL parameters or not.
	 */
	private boolean injectUserToken;
	
	/**
	 * The user authentication token, which will be injected to every REST Call
	 * as a URL parameter.
	 */
	private String authToken;
	
	/**
 	 *The Constant LOG.
 	 */
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CelarManager.class.getName());
	
	/**
	 * Instantiates a new CELAR Manager Client.
	 *
	 * @param serverIp
	 *            the server IP address
	 */
	public CelarManager(String serverIp) 
	{
		  this.serverIp = serverIp;
		  this.injectUserToken = false;
	}
	
	/**
	 * Instantiates a new CELAR Manager Client.
	 *
	 * @param serverIp
	 *            the server IP address
	 * @param authToken
	 *            the authentication token
	 */
	public CelarManager(String serverIp, String authToken) 
	{
		  this(serverIp);
		  if(authToken != null && !authToken.isEmpty())
		  {
			  this.injectUserToken = true;
			  this.authToken = authToken;
		  }
	}
	
	/*
	 * Application Specific Calls
	 * 
	 */
	
	/**
	 * Gets the application info.
	 *
	 * @param application_id
	 *            the application_id
	 * @return the application info
	 */
	public String getApplicationInfo(String application_id)
	{
		if(application_id == null || application_id.isEmpty())
			return null;		
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/application/" + application_id;	
		
	    builder.setPath(path);	    
	    // Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	/**
	 * Gets the application component dependencies.
	 *
	 * @param component_id
	 *            the component_id
	 * @return the application component dependencies
	 */
	public String getApplicationComponentDependencies(int component_id)
	{
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/application/component" + component_id + "/dependencies";
		
		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	
	
	
	/**
	 * Gets the application module dependencies.
	 *
	 * @param module_id
	 *            the module_id
	 * @return the application module dependencies
	 */
	public String getApplicationModuleDependencies(int module_id)
	{
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/application/module" + module_id + "/dependencies";

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	/**
	 * Gets the application deployments.
	 *
	 * @param application_id
	 *            the application_id
	 * @return the application deployments
	 */
	public String getApplicationDeployments(String application_id)
	{
		if(application_id == null || application_id.isEmpty())
			return null;		
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/application/" + application_id + "/deployments";		

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
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
	public String searchApplicationsByProperty(long submitted_start, long submitted_end, String description, int user_id, 
												String module_name, String component_description, String provided_resource_id )
	{
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/application/search/";

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
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
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
		
	}
	
	
	/**
	 * Gets the resising actions.
	 *
	 * @param component_id
	 *            the component_id
	 * @return the resising actions
	 */
	public String getResisingActions(int component_id) {
		
		if(component_id > 0)
			return null;		
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/component/" + component_id + "/resizing_actions";		

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	
	/*
	 * Deployment Specific Calls
	 * 
	 */
	
	/**
	 * Gets the deployment info.
	 *
	 * @param deployment_id
	 *            the deployment_id
	 * @return the deployment info
	 */
	public String getDeploymentInfo(String deployment_id)
	{
		if(deployment_id == null || deployment_id.isEmpty())
			return null;		
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/deployment/" + deployment_id;		

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	
	
	/**
	 * Gets the orchestation vm.
	 *
	 * @param deployment_id
	 *            the deployment_id
	 * @return the orchestation vm
	 */
	public String getOrchestationVm(String deployment_id)
	{
		InputStream stream = new ByteArrayInputStream(this.getDeploymentInfo(deployment_id).getBytes(StandardCharsets.UTF_8));
		
		//unmarshal an DeploymentInfo Entity
		gr.ntua.cslab.celar.server.beans.structured.DeploymentInfo di = new gr.ntua.cslab.celar.server.beans.structured.DeploymentInfo();
		try {
			di.unmarshal(stream);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 *  TODO: Temporary Hack!
		 *  
		 *  Check the Orchestrator IP validity
		 *  If empty | null | invalid try to parse it from State Object
		 */
		if(di.orchestrator_IP.isEmpty() || di.orchestrator_IP == null) {
			String orchestrator_IP = null;
			Map<String, String> state;
			
			state = di.stateMap;			
			String regex = "orchestrator-*:hostname";
			  
			for (String key : state.keySet())
			{
			  if (key.matches(regex))
			  {				
			     orchestrator_IP = state.get(key);
			     System.out.println(key + " --- " + orchestrator_IP);
			  }
			}
			
			
			if(orchestrator_IP.isEmpty() || orchestrator_IP == null)
				return null;
			else
				return orchestrator_IP;
		}
		else {
			return di.orchestrator_IP;
		}
	}
	
	/**
	 * Gets the elasticity decisions.
	 *
	 * @param deployment_id
	 *            the deployment_id
	 * @param module_id
	 *            the module_id
	 * @param component_id
	 *            the component_id
	 * @param action_name
	 *            the action_name
	 * @param timeWindow_start
	 *            the time window_start
	 * @param timeWindow_end
	 *            the time window_end
	 * @return the elasticity decisions
	 */
	public String getElasticityDecisions(String deployment_id, int module_id, int component_id, String action_name, long timeWindow_start, long timeWindow_end)
	{		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/deployment/" + deployment_id + "/decisions";

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		if(timeWindow_start != 0)
			builder.setParameter("start_time", String.valueOf(timeWindow_start));
		if(timeWindow_end != 0)
			builder.setParameter("end_time", String.valueOf(timeWindow_end));
		if(component_id > 0)
			builder.setParameter("component_id", String.valueOf(component_id));
		if(module_id > 0)
			builder.setParameter("module_id", String.valueOf(module_id));
		if(action_name != null && !action_name.isEmpty())
			builder.setParameter("action_name", action_name);
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	/**
	 * Gets the deployment instatiated resources.
	 *
	 * @param deployment_id
	 *            the deployment_id
	 * @param component_id
	 *            the component_id
	 * @param timeWindow_start
	 *            the time window_start
	 * @param timeWindow_end
	 *            the time window_end
	 * @return the deployment instatiated resources
	 */
	public String getDeploymentInstatiatedResources(String deployment_id, int component_id, long timeWindow_start, long timeWindow_end)
	{
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/deployment/" + deployment_id + "/resources";

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		if(timeWindow_start != 0)
			builder.setParameter("start_time", String.valueOf(timeWindow_start));
		if(timeWindow_end != 0)
			builder.setParameter("end_time", String.valueOf(timeWindow_end));
		if(component_id > 0)
			builder.setParameter("component_id", String.valueOf(component_id));
				
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	/**
	 * Search deployments by property.
	 *
	 * @param application_id
	 *            the application_id
	 * @param timeWindow_start
	 *            the submitted_start
	 * @param timeWindow_end
	 *            the submitted_end
	 * @param status
	 *            the status
	 * @return the string
	 */
	public String searchDeploymentsByProperty(String application_id, long timeWindow_start, long timeWindow_end, String status)
	{
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/deployment/search/";

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		if(timeWindow_start != 0)
			builder.setParameter("start_time", String.valueOf(timeWindow_start));
		if(timeWindow_end != 0)
			builder.setParameter("end_time", String.valueOf(timeWindow_end));		
		if(application_id != null && !application_id.isEmpty())
			builder.setParameter("application_id", String.valueOf(application_id));
		
		if(status != null && !status.isEmpty())
			builder.setParameter("status", status);
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	
	/**
	 * Gets the decisions.
	 *
	 * @param deployment_id
	 *            the deployment_id
	 * @param module_id
	 *            the module_id
	 * @param component_id
	 *            the component_id
	 * @param timeWindow_start
	 *            the time window_start
	 * @param timeWindow_end
	 *            the time window_end
	 * @param action_name
	 *            the action_name
	 * @return the decisions
	 */
	public String getDecisions (String deployment_id, int module_id, int component_id, long timeWindow_start, long timeWindow_end, String action_name) {
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/deployment/" + deployment_id + "/decisions";

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		if(timeWindow_start != 0)
			builder.setParameter("start_time", String.valueOf(timeWindow_start));
		if(timeWindow_end != 0)
			builder.setParameter("end_time", String.valueOf(timeWindow_end));
		if(component_id > 0)
			builder.setParameter("component_id", String.valueOf(component_id));
		if(module_id > 0)
			builder.setParameter("module_id", String.valueOf(module_id));
		if(action_name != null && !action_name.isEmpty())
			builder.setParameter("action_name", String.valueOf(action_name));
				
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
		
	}
	
	/*
	 * Monitoring Specific Calls
	 * 
	 */
	
	/**
	 * Gets the metrics.
	 *
	 * @param component_id
	 *            the component_id
	 * @return the metrics
	 */
	public String getComponentMetrics(String component_id)
	{
		if(component_id == null || component_id.isEmpty())
			return null;		
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/metrics/component/" + component_id;		

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	
	/**
	 * Put metric.
	 *
	 * @param component_id
	 *            the component_id
	 * @param name
	 *            the name
	 * @return the string
	 */
	public String putMetric(int component_id, String name)
	{
		if(!(component_id > 0))
			return null;		
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/metrics/put/";		

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
	    //the output to the server
		OutputStream svrOutput = null;
		String body = "";
	    try {
			Component c = new Component(component_id);			
			Metric m = new Metric(c, name);
			
			//m.name = name;

			//unmarshal the metric you created and write it to the output stream
			m.marshal(svrOutput);
			body = svrOutput.toString();
			svrOutput.close();//maybe
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	    	     	    
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executePost(builder.build(), client.ACCEPT_XML, body);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	/**
	 * Put metric value.
	 *
	 * @param component_id
	 *            the component_id
	 * @param metric_id
	 *            the metric_id
	 * @param values
	 *            the values
	 * @return the string
	 */
	public String putMetricValue(int component_id, int metric_id, Map<String, String> values)
	{
		if(!(component_id > 0))
			return null;
		
		if(!(metric_id > 0))
			return null;
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/metrics/values/put/";		

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
	    REList<MetricValue> valuesList = new REList<MetricValue>();
	    Iterator<Entry<String, String>> it = values.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<String, String> pair = it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        
	        MetricValue m = new MetricValue();
	        m.metrics_Id = metric_id;
	        m.resources_Id = component_id; // TODO
	        m.timestamp = new MyTimestamp(Long.valueOf(pair.getKey()).longValue());
	        m.value = Long.valueOf(pair.getValue()).longValue();
	        
	        // TODO
	        
	        
	        
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    	    
	    //the output to the server
		OutputStream  svrOutput = null;
		String body = "";
	    try {
			//unmarshal the metric you created and write it to the output stream
	    	valuesList.marshal(svrOutput);
	    	body = svrOutput.toString();	
			svrOutput.close();//maybe
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	         
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executePost(builder.build(), client.ACCEPT_XML, body);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	    
	}
	
	/**
	 * Gets the metric value.
	 *
	 * @param deployment_id
	 *            the deployment_id
	 * @param metric_id
	 *            the metric_id
	 * @param timeWindow_start
	 *            the time window_start
	 * @param timeWindow_end
	 *            the time window_end
	 * @return the metric value
	 */
	public String getMetricValue(String deployment_id, String metric_id, long timeWindow_start, long timeWindow_end)
	{
		if(deployment_id == null || deployment_id.isEmpty())
			return null;
		
		URIBuilder builder = new URIBuilder();
		String path = this.serverIp + this.restPath + "/metrics/" + metric_id + "/values";		

		builder.setPath(path);
		// Add authentication / token information to URL
	    if(this.injectUserToken)
	    	builder = this.addAuthTokenInformation(builder);
		
	    if(timeWindow_start != 0)
			builder.setParameter("start_time", String.valueOf(timeWindow_start));
		if(timeWindow_end != 0)
			builder.setParameter("end_time", String.valueOf(timeWindow_end));
		if(deployment_id != null && !deployment_id.isEmpty())
			builder.setParameter("deployment_id", deployment_id);
	    
		//
		CloseableHttpResponse response = null;
		RestClient client = new RestClient(this.serverIp);
		
		try {
			response = client.executeGet(builder.build(), client.ACCEPT_XML);
		} catch (URISyntaxException e1) {
			LOG.warn("Could Not build request URI [" + e1.getMessage() + "]");
			e1.printStackTrace();
		}
		
		return client.getContent(response);
	}
	
	/**
	 * Adds the authentication token information to the 
	 * request  URL through the URIBuilder class
	 *
	 * @param URIBuilder
	 *            The URL Builder Object
	 * @return URIBuilder
	 */
	private URIBuilder addAuthTokenInformation(URIBuilder builder)
	{
		return builder.setParameter("token", this.authToken);
	}
}
