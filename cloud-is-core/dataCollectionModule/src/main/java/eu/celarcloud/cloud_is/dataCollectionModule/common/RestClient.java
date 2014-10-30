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
package eu.celarcloud.cloud_is.dataCollectionModule.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;



// TODO: Auto-generated Javadoc
/**
 * The Class RestClient.
 */
public class RestClient {
	
	public final String ACCEPT_JSON = "application/json";
	
	public final String ACCEPT_XML = "text/xml";
	
	/**  
	 * Meta data information, for debugging reasons
	 * */
	private String name;
	
	/** The httpclient. */
	private CloseableHttpClient httpclient;
	
	/**
	 * Instantiates a new rest client.
	 */
	public RestClient() 
	{
		//
		SSLContextBuilder SSlcBuilder = new SSLContextBuilder();
		try {
			SSlcBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	    		SSlcBuilder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	    this.httpclient = HttpClients.custom().setSSLSocketFactory(
	            sslsf).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			// TODO Auto-generated catch block
			System.out.println("Rest Client " + this.name + " :" + "SSl Authendication Error");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Instantiates a new rest client.
	 *
	 * @param name
	 *            the name
	 */
	public RestClient(String name) 
	{
		this();
		this.name = name;
	}
	
	/**
	 * Execute get.
	 *
	 * @param uri
	 *            the uri
	 * @return the closeable http response
	 */
	public CloseableHttpResponse executeGet(URI uri, String acceptType)
	{
		HttpGet httpGet = new HttpGet(uri);
	    httpGet.addHeader("accept", acceptType);
	    

		CloseableHttpResponse response = null;
	    try {
			response = this.httpclient.execute(httpGet);
			//System.out.println(response.getStatusLine());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Rest Client " + this.name + " :" + "Endpoint is not accessible");
			return null;
		}
	    
	    return response;				
	}
	
	public CloseableHttpResponse executeGet(URI uri)
	{
		return this.executeGet(uri, ACCEPT_JSON);	
	}
	
	/**
	 * Gets the content.
	 *
	 * @param response
	 *            the response
	 * @return the content
	 */
	public String getContent(CloseableHttpResponse response)
	{
		if(response == null)
			return "";
		
		StringBuffer result = new StringBuffer();
		HttpEntity entity = response.getEntity();
        //EntityUtils.consume(entity); // consumes and closes

	    //
	    //httpclient.close();
	    try {
	        
	        BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
	       	         
        	
        	String line = "";
        	while ((line = rd.readLine()) != null) {
        		result.append(line);
        	}	        
	        //System.out.println(result);
	        
	        
	        
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	    finally {
	        try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
	    }
	    
	    return result.toString();		
	}
  
	
//	HttpClient httpClient = new HttpClient();
//	HttpGet getRequest;
//	HttpResponse response = null;
//	try {
//		getRequest = new HttpGet(builder.build());
//		getRequest.addHeader("accept", "application/json");
//		response = httpClient.execute(getRequest);
//	} catch (URISyntaxException | IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	
//	if (response.getStatusLine().getStatusCode() != 200) {
//		throw new RuntimeException("Failed : HTTP error code : "
//		   + response.getStatusLine().getStatusCode());
//	}
//
//	BufferedReader br = null;
//	try {
//		br = new BufferedReader(
//		                 new InputStreamReader((response.getEntity().getContent())));
//		String output;
//		System.out.println("Output from Server .... \n");
//		while ((output = br.readLine()) != null) {
//			System.out.println(output);
//		}
//		
//	} catch (IllegalStateException | IOException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//
//	httpClient.getConnectionManager().shutdown();
	
}
