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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Decision;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IElasticityLog;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ITopology;
import gr.ntua.cslab.celar.server.beans.structured.REList;

/**
 * The Class TopologyData.
 */
public class ElasticityData implements IElasticityLog {	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ElasticityData.class.getName());
	
	/** The cm client. */
	private eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager cmClient;
	
	/**
	 * Initializes the CELAR Manager Rest Client.
	 *
	 * @param restApiUri
	 *            the rest api uri
	 */
	public void init(String restApiUri) {
		this.cmClient = new eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager(restApiUri);
	}
	
	@Override
	public List<Decision> getEnforcedActions(String deplId, String name,  Long sTime,  Long eTime) {
		return null;		
		
	}

	@Override
	public List<Decision> getEnforcedActions(String deplId, String compId, String name,  Long sTime,  Long eTime) {
		String response = this.cmClient.getElasticityDecisions(deplId, -1, Integer.parseInt(compId), name, sTime, eTime);
		
		List<Decision> desicions = new ArrayList<Decision>();
		if(response == null || response.isEmpty())	
    		return desicions;
				   
		InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
		
    	// Parse the result into objects celarBeans
		
		REList<gr.ntua.cslab.celar.server.beans.Decision> responseEntinityList = new REList<gr.ntua.cslab.celar.server.beans.Decision>();
        try {
        	responseEntinityList.unmarshal(stream);
		} catch (JAXBException e) {
			LOG.warn("Misformatted response [ " + e.getMessage() + " ]");
			e.printStackTrace();
		}
        
        //print the entity in a structured manner
        //you can observe all the field names and their values (in this example)
        System.out.println(responseEntinityList.toString(true));
		
		// Get and parse application list
        List<gr.ntua.cslab.celar.server.beans.Decision> decisionList = responseEntinityList.getValues();        
		
		if(decisionList == null || decisionList.isEmpty())	
    		return desicions;
		
		for (gr.ntua.cslab.celar.server.beans.Decision d : decisionList) {
			Decision dc = new Decision(d.timestamp.toString(), String.valueOf(d.resizing_action_id));			
			desicions.add(dc);	
		}
		
		
		return desicions;
	}

}
