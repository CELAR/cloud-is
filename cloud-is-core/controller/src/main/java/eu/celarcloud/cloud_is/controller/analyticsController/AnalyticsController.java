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
package eu.celarcloud.cloud_is.controller.analyticsController;

import java.util.List;
import java.util.Properties;

import eu.celarcloud.cloud_is.analysisModule.analyticsLib.Trend;
import eu.celarcloud.cloud_is.analysisModule.analyticsLib.Sampling;
import eu.celarcloud.cloud_is.analysisModule.analyticsLib.parallel.trend.ParallelTrendExetutor;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.*;

// TODO: Auto-generated Javadoc
/**
 * The Class AnalyticsController.
 */
public class AnalyticsController {
	/** The global props. 
	 * 	Contains:
	 * 		config: The Config Folder Path
	 *  
	 */
	protected Properties globalProps = null;	
	
	public AnalyticsController(Properties props) {
		this.globalProps = props;
	}
		
	/**
	 * Calculate trend.
	 *
	 * @param list
	 *            the list
	 * @param window
	 *            the window
	 * @return the linked hash map
	 */
	public Number[][] calculateTrend(List<MetricValue> list, int window)
	{
		int index = 0;
		int threshold;
		Number[][] trend, sample, result = null;		
		
		Number[][] metrics = new Number[list.size()][2];

		long startTime = System.nanoTime();
		for (MetricValue m : list) {
			metrics[index][0] = Long.valueOf(m.timestamp).longValue();
			metrics[index][1] = Double.valueOf(m.value).doubleValue();
			index++;
		}
		list.clear();
		System.out.println("Transform to Number[][] Array : " + (System.nanoTime() - startTime) + " ns");
        //-
      	System.out.println("Values Before: " + metrics.length);
      	//-
        
      	// Get Sampling properties from file
      	boolean sampling = true, presampling;
      	float threshPercentage = Float.valueOf(globalProps.getProperty("sampling.threshhold", "1")).floatValue();
      	if( threshPercentage >= 1)
      		sampling = false;
      	presampling = Boolean.parseBoolean(globalProps.getProperty("sampling.presampling", "false"));
      	
      	int parallelTrendThreads = Integer.valueOf(globalProps.getProperty("trend.parallel.threads", "1")).intValue();
      	ParallelTrendExetutor pTrend = new ParallelTrendExetutor(parallelTrendThreads);
      	
      	// Calculate Trend According to Sampling Parameters.
      	if(!sampling)
      	{
      		// Sampling is OFF
      		// Display raw -> trend
      		
        	//trend = Trend.calculateTrend(metrics, window);
    		//result  = Trend.calculateTrend(metrics, window);       	
      		result  = pTrend.calculateTrend(metrics, window);
      	}
      	else if(sampling && !presampling)
      	{
      		// Sampling is ON, Presampling is OFF
      		// Display raw -> trend -> sampling

        	trend  = pTrend.calculateTrend(metrics, window); 
        	
        	metrics = null;
        	threshold = (int) Math.round(trend.length * threshPercentage);
        	//sample = Sampling.largestTriangleThreeBuckets(trend, threshold);
        	result  = Sampling.largestTriangleThreeBuckets(trend, 9000);
      	}
      	else if(sampling && presampling)
      	{
	      	// Sampling is ON, Presampling is ON
	      	// Display raw -> sampling -> trend
      		
        	threshold = (int) Math.round(metrics.length * threshPercentage);
        	sample = Sampling.largestTriangleThreeBuckets(metrics, threshold);
        	metrics = null;
        	//trend = Trend.calculateTrend(sample, window);
        	//result  = Trend.calculateTrend(sample, window);   
        	result  = pTrend.calculateTrend(sample, window); 
        }      
      	
		return result;
	}
	
	/**
	 * Calculate trend.
	 *
	 * @param list
	 *            the list
	 * @return the linked hash map
	 */
	public Number[][] calculateTrend(List<MetricValue> list)
	{
		// Get SMA window from properties
		// Window will fall back to default 10, if property is unreadable
		int smaWindow = Integer.valueOf(globalProps.getProperty("trend.sma.window", "10")).intValue();
		
		if(smaWindow > list.size())
		{
			// If for any reason SMA window is
			// larger that the amount of data
			// set it to 0, in order to be calculated automatically
			smaWindow = 0;
		}
		
		if(smaWindow == 0)
		{
			// Calculate window
			long timeFrame = Math.abs(Long.valueOf(list.get(list.size() - 1).timestamp).longValue() - Long.valueOf(list.get(0).timestamp).longValue());
			smaWindow = 10;
		}
		
		return calculateTrend(list, smaWindow);
	}
}
