package eu.celarcloud.cloud_is.dataCollectionModule.impl.celar;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.bind.JAXBException;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering;
import gr.ntua.cslab.celar.server.beans.structured.ApplicationInfo;

public class MonitoringHistoricalData implements IMetering {
	
	/** The app. */
	private eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager cmClient;
	
	/**
	 * Initializes the CELAR Manager Rest Client
	 *
	 * @param restApiUri
	 *            the rest api uri
	 */
	public void init(String restApiUri) {
		this.cmClient = new eu.celarcloud.cloud_is.dataCollectionModule.common.helpers.clients.CelarManager(restApiUri);		
	}
	
	@Override
	public String getAgents(String deplId, String string2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAgentMetrics(String deplId, String agentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Metric> getMetricValues(String deplId, String name, String sTime, String eTime) {
		String compId = deplId;
		String metricId = name;
		
		String temp = this.cmClient.getMetrics(compId, metricId);		
		
		// TODO
		// TODO
		
		InputStream stream = new ByteArrayInputStream(temp.getBytes(StandardCharsets.UTF_8));

		 //unmarshal an ApplicationInfo Entity
        ApplicationInfo inai = new ApplicationInfo();
        try {
			inai.unmarshal(stream);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //print the entity in a structured manner
        //you can observe all the field names and their values (in this example)
        System.out.println(inai.toString(true));
        
        //gets the first module of an application and prints its name
        //ModuleInfo mi = inai.modules.get(0);
        //System.out.println("Module name:"+mi.name);
		
        // Parse response to IS bean
        Application app = new Application();
        
        app.id = inai.id;
        app.description = inai.description;
        app.submitted = inai.submitted.toString();
        
        
		
		
		
		
		
		
		
		
		return null;
	}

	@Override
	public List<Metric> getDeploymentCost(String deplId, String tierId, long sTime, long eTime) {
		// TODO Auto-generated method stub
		return null;
	}

}
