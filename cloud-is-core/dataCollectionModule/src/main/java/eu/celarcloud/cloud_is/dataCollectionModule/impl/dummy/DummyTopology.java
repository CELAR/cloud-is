package eu.celarcloud.cloud_is.dataCollectionModule.impl.dummy;

import org.json.JSONObject;

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ITopology;

public class DummyTopology implements ITopology {

	@Override
	public String getTopology(String deplId) {
		//
		JSONObject node, name;
		
		// Build report	
    	JSONObject json = new JSONObject();
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
    		json.put("topology", topology);
    	
    	
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
    	
    	
    		
	    return json.toString();
	}

}
