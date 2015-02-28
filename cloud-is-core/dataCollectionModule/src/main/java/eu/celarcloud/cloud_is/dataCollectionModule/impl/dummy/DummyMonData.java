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

import java.util.ArrayList;
import java.util.List;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering;

// TODO: Auto-generated Javadoc
/**
 * The Class Jcatascopia.
 */
public class DummyMonData implements IMetering {

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
	public List<Metric> getMetricValues(String deplId, String name, String sTime, String eTime) {
		List<Metric> list = new ArrayList<Metric>();
		
		//-		
		int sRate = 15 * 1000; // to ms
		int count = (int) (Long.parseLong(eTime) - Long.parseLong(sTime)) / sRate;		
		
		// TODO
		count = Math.abs(count);
		
		double randNum = 0.0;
		int min = 15;
		int max = 100;
		
		long currTime = Long.parseLong(sTime);
		for(int i = 0; i < count; i++)
		{
			if(i == 0)
			{
				randNum = TestClass.randDouble(min, max);
				//maxValue = randNum; minValue = randNum; averageValue  = randNum;
			}
			else
			{
				randNum = TestClass.randDoubleKnoledge((int) Math.round(randNum), min, max);
				//averageValue = Average.incrementalAverage(averageValue, randNum);
			}
			
			//rawData.put(randNum);
			Metric m = new Metric(String.valueOf(currTime), String.valueOf(randNum));
			list.add(m);
			currTime += sRate;
			//minValue = randNum < minValue ? randNum : minValue;
			//maxValue = randNum > maxValue ? randNum : maxValue;
		}
		
		return list;
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getDeploymentCost(java.lang.String, java.lang.String, long, long)
	 */
	@Override
	public List<Metric> getDeploymentCost(String deplId, String tierId, long sTime, long eTime) {
		return getMetricValues(deplId, tierId, String.valueOf(sTime), String.valueOf(eTime));
	}

	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering#getAvailableMetrics(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getAvailableMetrics(String deplId, String compId) {
		List<String> list = new ArrayList<String>();
		
		int count = 10;
		for(int i = 0; i < count; i++)
		{
			String m = "metric_"+i;
			list.add(m);
		}
		
		return list;
	}

	
}
