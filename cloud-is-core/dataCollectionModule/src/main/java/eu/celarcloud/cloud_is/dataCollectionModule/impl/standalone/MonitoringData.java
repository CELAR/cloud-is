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
package eu.celarcloud.cloud_is.dataCollectionModule.impl.standalone;

import java.util.ArrayList;
import java.util.List;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IMetering;

// TODO: Auto-generated Javadoc
/**
 * The Class Jcatascopia.
 */
public class MonitoringData implements IMetering {

	@Override
	public String getAgents(String deplId, String string) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getAgentMetrics(String deplId, String agentId) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public List<Metric> getMetricValues(String deplId, String name, String sTime, String eTime) {
		List<Metric> list = new ArrayList<Metric>();
		
		//-		
		int sRate = 15 * 1000; // to ms
		int count = (int) (Long.parseLong(eTime) - Long.parseLong(sTime)) / sRate;		
		
		double randNum = 0.0;
		int min = 15;
		int max = 100;
		
		long currTime = Long.parseLong(sTime);
		for(int i = 0; i < count; i++)
		{
			if(i == 0)
			{
				//randNum = TestClass.randDouble(min, max);
				//maxValue = randNum; minValue = randNum; averageValue  = randNum;
			}
			else
			{
				//randNum = TestClass.randDoubleKnoledge((int) Math.round(randNum), min, max);
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

	@Override
	public List<Metric> getDeploymentCost(String deplId, String tierId, long sTime, long eTime) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public List<String> getAvailableMetrics(String deplId, String compId) {
		throw new java.lang.UnsupportedOperationException();
	}

	
}
