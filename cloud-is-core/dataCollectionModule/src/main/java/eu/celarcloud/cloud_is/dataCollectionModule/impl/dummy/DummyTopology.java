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
package eu.celarcloud.cloud_is.dataCollectionModule.impl.dummy;

import org.json.JSONObject;

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ITopology;

// TODO: Auto-generated Javadoc
/**
 * The Class DummyTopology.
 */
public class DummyTopology implements ITopology {

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ITopology#getTopology(java.lang.String)
	 */
	@Override
	public String getTopology(String deplId) {
		//
		JSONObject node, name;
		
		// TODO: Change dummy data, topology formating has been changed
		
		
		// Build report	
		JSONObject topology = new JSONObject();
			// First Node
    		node = new JSONObject();
    			node.put("name", "Load Balancer");
    			node.put("id", "loadBalancer");
    		topology.put("0", node);
    		
    		// Second  Node
    		node = new JSONObject();
    			node.put("name", "Application Server");	  
    			node.put("id", "appServer");
    		topology.put("1", node);    	
    		
    		// Third Node
    		node = new JSONObject();	    		
    			node.put("name", "Database");
    			node.put("id", "dbServer");
    		topology.put("2", node);
    	
    	
	    /*
    	
    		case "5":
    			desc = "<description>" +
			    		    "<modules>" +
			    		        "<module name=\"loadBalancer\">" +
			    		            "<image>haproxy</image>" +
			    		        "</module>" +
			    		        "<module name=\"appServer\">" +
			    		            "<image>apache7</image>" +
			    		        "</module>" +
			    		        "<module name=\"dbServer\">" +
			    		            "<image>cassndra</image>" +
			    		        "</module>" +
			    		    "</modules>" +
			    		"</description>";
    			break;
    	}*/
    	
    	
    		
	    return topology.toString();
	}

}
