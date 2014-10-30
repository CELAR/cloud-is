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



// TODO: Auto-generated Javadoc
/**
 * The Class EndpointConfig.
 */
public class EndpointConfig {
	
	/** The config. */
	private Config config;
	
	/** The name. */
	public String name;
	
	/** The address. */
	public String address;
	
	/** The port. */
	public String port;
	
	/** The base url. */
	public String baseUrl;
	
	private String uri;
	
	public EndpointConfig()
	{
		
	}
	
	/**
	 * Instantiates a new endpoint config.
	 *
	 * @param path
	 *            the path
	 */
	public EndpointConfig(String path)
	{
		this();
		this.loadConfigFile(path);
	}
	
	public void loadConfigFile(String path)
	{
		String temp = null;
		this.config = new Config(path);
		//
		this.name = this.config.getProperty("name");
		//
		temp = this.config.getProperty("address");
		if (temp.endsWith("/")) {
			temp = temp.substring(0, temp.length() - 1);
		}
		this.address = temp;
		temp = null;		
		//
		temp = this.config.getProperty("port");
		if (temp.endsWith("/")) {
			temp = temp.substring(0, temp.length() - 1);
		}
		this.port =  temp;
		temp = null;		
		//
		temp = this.config.getProperty("baseUrl");
		if (temp.endsWith("/")) {
			temp = temp.substring(0, temp.length() - 1);
		}
		this.baseUrl =  temp;
		temp = null;
		//
		temp = this.config.getProperty("uri");
		if (temp.endsWith("/")) {
			temp = temp.substring(0, temp.length() - 1);
		}
		this.uri =  temp;
		temp = null;
	}
	
	/**
	 * Gets the uri.
	 *
	 * @return the uri
	 */
	public String getUri()
	{
		String url = this.uri;
		// Check full uri exists
		if(url == null || url.isEmpty())		
		{
			// Calculate uri from its components
			// check to remove trailing slashes before concat
			if(this.address != null && !this.address.isEmpty())
				url += this.address;
			if(this.port != null && !this.port.isEmpty())
				url +=  ":" + this.port;
			if(this.baseUrl != null && !this.baseUrl.isEmpty())
				url += "/" + this.baseUrl;
		}
		
		// Remove trailing slash
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		
		return url;
	}
}
