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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class RestClient.
 */
public class RestClient {
	
	/** The accept json. */
	public final String ACCEPT_JSON = "application/json";
	
	/** The accept xml. */
	public final String ACCEPT_XML = "text/xml";
	
	/** Meta data information, for debugging reasons. */
	private String name;
	
	/** The httpclient. */
	private CloseableHttpClient httpclient;
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RestClient.class.getName());
	
	/** The request start. */
	private long requestStart;
	
	/**
	 * Instantiates a new rest client.
	 */
	public RestClient() {
		// Shut off the uneeded logging off httpClient
		// TODO Need to reduce level from DEBUG to WARN and not disable it
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		
		SSLContextBuilder SSlcBuilder = new SSLContextBuilder();
		try {
			SSlcBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSlcBuilder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			this.httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			LOG.warn("Rest Client " + this.name + " :" + "SSl Authendication Error");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Instantiates a new rest client.
	 *
	 * @param name
	 *            the name
	 */
	public RestClient(String name) {
		this();
		this.name = name;
	}
	
	/**
	 * Execute get.
	 *
	 * @param uri
	 *            the uri
	 * @param acceptType
	 *            the accept type
	 * @return the closeable http response
	 */
	public CloseableHttpResponse executeGet(URI uri, String acceptType) {
		HttpGet httpGet = new HttpGet(uri);
	    httpGet.addHeader("accept", acceptType);
	    httpGet.addHeader("Content-Type", "application/octet-stream");
	    
	    return this.execute(httpGet);				
	}
	
	/**
	 * Execute post.
	 *
	 * @param uri
	 *            the uri
	 * @param acceptType
	 *            the accept type
	 * @param xmlString
	 *            The body message to be send in string format
	 * @return the closeable http response
	 */
	public CloseableHttpResponse executePost(URI uri, String acceptType, String xmlString) {
		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader("accept", acceptType);
		httpPost.addHeader("Content-Type", "application/octet-stream");

		
		StringEntity xmlEntity = null;
		try {
			xmlEntity = new StringEntity(xmlString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(xmlEntity);
		
	    return this.execute(httpPost);				
	}
	
	/**
	 * Execute get.
	 *
	 * @param uri
	 *            the uri
	 * @return the closeable http response
	 */
	public CloseableHttpResponse executeGet(URI uri) {
		return this.executeGet(uri, ACCEPT_JSON);	
	}
	
	/**
	 * Gets the content.
	 *
	 * @param response
	 *            the http response
	 * @return the content
	 */
	public String getContent(CloseableHttpResponse response) {
		if(response == null)
		{
			LOG.debug("Response is null");
			return "";
		}
		
		// Check Headers
		if(response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK)
		{
			int responseCode =  response.getStatusLine().getStatusCode();
			LOG.warn("Server Responded " + response.getStatusLine().getStatusCode() + " exiting...");
			switch (responseCode) {
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
		        // HTTP Status-Code 404: Not Found.
		        break;
		    case HttpURLConnection.HTTP_NOT_FOUND:
		        // HTTP Status-Code 404: Not Found.
		        break;
		    }
			
			return "";
		}
		
		
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
	    } catch (IOException e) {
	    	LOG.warn("Could Not build request URI [" + e.getMessage() + "]");
			e.printStackTrace();
			return "";
		}
	    finally {
	        try {
				response.close();
			} catch (IOException e) {
				LOG.warn("Failed to close HttpResponse [" + e.getMessage() + "]");
				e.printStackTrace();
				return "";
			}
	    }
	    	    
	    return result.toString();		
	}
	
	/**
	 * Execute.
	 *
	 * @param http
	 *            the http
	 * @return the http response
	 */
	private CloseableHttpResponse execute(HttpUriRequest http) {
		this.requestStart = System.currentTimeMillis();
		
		CloseableHttpResponse response = null;
	    try {
			response = this.httpclient.execute(http);
			//System.out.println(response.getStatusLine());
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Rest Client " + this.name + " :" + "Endpoint is not accessible");
			return null;
		}
	    
	    
	    long executionTime = System.currentTimeMillis() - this.requestStart;
	    System.out.println("Request to " + http.getURI() + " completed in " + (float)executionTime / 1000 + " sec");
	    
	    return response;		
	}
  
	
//	HttpClient httpClient = new HttpClient();
//	HttpGet getRequest;
//	HttpResponse response = null;
//	try {
//		getRequest = new HttpGet(builder.build());
//		getRequest.addHeader("accept", "application/json");
//		response = httpClient.execute(getRequest);
//	} catch (URISyntaxException | IOException e) {
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
//		e1.printStackTrace();
//	}
//
//	httpClient.getConnectionManager().shutdown();
	
}
