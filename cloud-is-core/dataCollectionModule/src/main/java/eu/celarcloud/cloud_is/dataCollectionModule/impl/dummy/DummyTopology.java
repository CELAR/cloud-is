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

import org.json.JSONArray;
import org.json.JSONObject;

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ITopology;

/**
 * The Class DummyTopology.
 */
public class DummyTopology implements ITopology {

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ITopology#getTopology(java.lang.String)
	 */
	@Override
	public String getTopology(String deplId) {
		JSONObject component, module;
		JSONArray components;
		
		/*
		 * Create a dummy 3-tier application topology
		 */		
		JSONArray topology = new JSONArray();
		// First Module
		module = new JSONObject();
			module.put("name", "Load Balancer");
			module.put("id", "loadBalancer");
			// Create module components
			components = new JSONArray();
				// Create a component
				component = new JSONObject();
				component.put("description", "Load Balancer");
				component.put("id", "loadBalancer");
			components.put(component);
			module.put("components", components);
		topology.put(module);
		// Second Module
		module = new JSONObject();
			module.put("name", "Application Server");
			module.put("id", "appServer");
			// Create module components
			components = new JSONArray();
				// Create a component
				component = new JSONObject();
				component.put("description", "Application Server");
				component.put("id", "appServer");
			components.put(component);
			module.put("components", components);
		topology.put(module);
		// Third Module
		module = new JSONObject();
			module.put("name", "Database");
			module.put("id", "dbServer");
			// Create module components
			components = new JSONArray();
				// Create a component
				component = new JSONObject();
				component.put("description", "Database");
				component.put("id", "dbServer");
			components.put(component);
			module.put("components", components);
		topology.put(module);
			
	    	
    	
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
