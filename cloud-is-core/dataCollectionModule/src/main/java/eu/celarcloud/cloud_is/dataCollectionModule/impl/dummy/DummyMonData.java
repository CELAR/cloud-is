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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import eu.celarcloud.cloud_is.dataCollectionModule.common.Config;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.MetricValue;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering;

// TODO: Auto-generated Javadoc
/**
 * The Class Jcatascopia.
 */
public class DummyMonData implements IMetering {
	private Config config;
	
	
	public void init(String path) {
		this.config = new Config(path + File.separator + "config.properties");					
	}
	
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getAgents(java.lang.String, java.lang.String)
	 */
	@Override
	public String getAgents(String deplId, String string) {
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getAgentMetrics(java.lang.String, java.lang.String)
	 */
	@Override
	public String getAgentMetrics(String deplId, String agentId) {
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getMetricValues(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<MetricValue> getMetricValues(String deplId, String name, long sTime, long eTime) {
		// Get starting time
		long startTime = System.nanoTime();
		
		List<MetricValue> list = new ArrayList<MetricValue>();
		
		//-	
		int sRate = Integer.parseInt(config.getProperty("metrics.rate")) * 1000; // to ms
		int count = Integer.parseInt(config.getProperty("metrics.count"));
		
		double randNum = 0.0;
		int min = 15;
		int max = 100;
		
		long currTime = sTime; 
		for(int i = 0; i < count; i++)
		{
			if(i == 0)
			{
				randNum = TestClass.randDouble(min, max);
			}
			else
			{
				randNum = TestClass.randDoubleKnoledge((int) Math.round(randNum), min, max);
			}
			
			MetricValue m = new MetricValue(String.valueOf(currTime), String.valueOf(randNum));
			list.add(m);
			currTime += sRate;
		}
		
		// Print completion time		
		System.out.println("Creating Random Metrics: " + (System.nanoTime() - startTime) + " ns");		
		
		return list;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getDeploymentCost(java.lang.String, java.lang.String, long, long)
	 */
	@Override
	public List<MetricValue> getDeploymentCost(String deplId, String tierId, long sTime, long eTime) {
		return getMetricValues(deplId, tierId, sTime, eTime);
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getAvailableMetrics(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getAvailableMetrics(String deplId, String compId) {
		
		// Set a static list of X metrics
		int count = 10;
		List<String> metricslist = new ArrayList<String>();		
		for(int i = 0; i < count; i++) {
			String m = "metric_"+i;
			metricslist.add(m);
		}
		
		// Select a subset of metrics
		// Subset size must be (metricslist / 2) + 1 to ensure overlapping 
		int subListSize = (Math.abs(metricslist.size() / 2)) + 1;
		List<String> subsetList = new ArrayList<String>(subListSize);

		Random rand = new Random(System.currentTimeMillis()); 
		for (int i = 0; i < 5; i++) {
		     subsetList.add(metricslist.remove(rand.nextInt(metricslist.size())));
		}
		
		return subsetList;
	}
}
