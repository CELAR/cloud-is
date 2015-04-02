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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Application;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.MetricValue;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IApplicationMetadata;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDeploymentMetadata;


// TODO: Auto-generated Javadoc
/**
 * The Class CelarApplication.
 */
public class DummyDeplData implements IDeploymentMetadata {
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.services.application.IApplication#getRecentDeployments(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Deployment> getRecentDeployments(String limit, String status) {
		List<Deployment> deployments = new ArrayList<Deployment>();
		Deployment deployment;
    	
		/*
		// Dummy data
		//
		int count = 100;
		// dummy calculations
		// Find the start and the end of the deployment
		BigInteger tStart = new BigInteger("1413290766468");
		int sRate = 500 * 1000; // to ms
		BigInteger durration = new BigInteger(String.valueOf((sRate * count)));
		BigInteger tEnd = tStart.add(durration);
		//	
		*/
		Random rand = new Random();
		
    	deployment = new Deployment();
    		deployment.id = String.valueOf(rand.nextInt((9999 - 1000) + 1) + 1000);
			deployment.applicationId = "67890";
			deployment.status = "online";
			deployment.startTime = "1413290766468";
			deployment.endTime = null;
		deployments.add(deployment);
    	
		deployment = new Deployment();
	    	deployment.id = String.valueOf(rand.nextInt((9999 - 1000) + 1) + 1000);
	    	deployment.applicationId = "10293";
			deployment.status = "offline";
			deployment.startTime = "1413290766468";
			deployment.endTime = "1413298766468";
		deployments.add(deployment);
    	
	    return deployments;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDeploymentMetadata#getDeployment(java.lang.String)
	 */
	@Override
	public Deployment getDeployment(String deplId) {
		Deployment deployment = new Deployment();
			deployment.id = deplId;
			deployment.applicationId = "";
			deployment.status = "offline";
			deployment.startTime = "1413290766468";
			deployment.endTime = "1413299766468";
	    return deployment;
	}


	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDeploymentMetadata#searchDeployments(long, long, java.lang.String)
	 */
	@Override
	public List<Deployment> searchDeployments(long tStart, long tEnd, String status) {
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDeploymentMetadata#getDeploymentInstances(java.lang.String, java.lang.String, long, long)
	 */
	@Override
	public List<MetricValue> getDeploymentInstances(String deplId, String tierId, long sTime, long eTime) {
		List<MetricValue> instances = new ArrayList<MetricValue>();
		
		//-
		int sRate = 60 * 60 * 1000; // to ms, Rate = 1hour
		long currTime = sTime;
		
		//-
		if(tierId == "appServer")
		{
			// 07:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "2"));
			currTime += sRate;
			// 08:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "4"));
			currTime += sRate;
			// 09:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "5"));
			currTime += sRate;
			// 10:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "8"));
			currTime += sRate;
			// 11:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "10"));
			currTime += sRate;
			// 12:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "7"));
			currTime += sRate;
			// 13:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "5"));
			currTime += sRate;
			// 14:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "6"));
			currTime += sRate;
			// 15:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "9"));
		}
		else if (tierId == "database")
		{
			// 07:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "1"));
			currTime += sRate;
			// 08:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "2"));
			currTime += sRate;
			// 09:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "4"));
			currTime += sRate;
			// 10:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "6"));
			currTime += sRate;
			// 11:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "7"));
			currTime += sRate;
			// 12:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "6"));
			currTime += sRate;
			// 13:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "4"));
			currTime += sRate;
			// 14:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "7"));
			currTime += sRate;
			// 15:00:00
			instances.add(new MetricValue(String.valueOf(currTime), "8"));
		}
		
		return instances;
	}

	@Override
	public List<Deployment> getUserDeployments() {
		throw new java.lang.UnsupportedOperationException();
	}

}
