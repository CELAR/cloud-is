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
package eu.celarcloud.cloud_is.dataCollectionModule.impl.celar;

import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager;

// TODO: Auto-generated Javadoc
/**
 * The Class CelarManagerEndpoint.
 */
public abstract class CelarManagerEndpoint {
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ApplicationData.class.getName());
	
	/** The app. */
	protected CelarManager cmClient;
	
	/**
	 * Initialises the CELAR Manager Rest Client.
	 *
	 * @param restApiUri
	 *            the rest api uri
	 */
	public void init(String restApiUri) {
		this.cmClient = new CelarManager(restApiUri);		
	}
	
	/**
	 * Inits the.
	 *
	 * @param cm
	 *            the cm
	 */
	public void init(CelarManager cm) {
		this.cmClient = cm;		
	}
	
	/**
	 * Gets the cm client.
	 *
	 * @return the cm client
	 */
	protected CelarManager getCmClient() {
		return cmClient;
	}
}
