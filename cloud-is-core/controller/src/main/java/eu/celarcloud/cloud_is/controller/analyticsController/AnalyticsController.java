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

import java.util.Iterator;
import java.util.LinkedHashMap;
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
	public LinkedHashMap<String, String> calculateTrend(List<MetricValue> list, int window)
	{
		int index = 0;
		int threshold;
		Number[][] trend, sample, result = null;		
		
		Number[][] metrics = new Number[list.size()][2];
		
		
		Iterator<MetricValue> ite = list.iterator();

        /* Remove the second value of the list, while iterating over its elements,
         * using the iterator's remove method. */
        while(ite.hasNext()) {
             MetricValue m = ite.next();
             
             metrics[index][0] = Long.parseLong(m.timestamp);
 			 metrics[index][1] = Double.parseDouble(m.value);
             
             ite.remove();
             index++;
        }
        
        //-
      	System.out.println("Values Before: " + metrics.length);
      	//-
        
      	// Get Sampling properties from file
      	boolean sampling = true, presampling;
      	float threshPercentage = (float) Float.parseFloat(globalProps.getProperty("sampling.threshhold", "1"));
      	if( threshPercentage >= 1)
      		sampling = false;
      	presampling = Boolean.parseBoolean(globalProps.getProperty("sampling.presampling", "false"));
      	
      	Integer parallelTrendThreads = (Integer) Integer.parseInt(globalProps.getProperty("trend.parallel.threads", "1"));
      	ParallelTrendExetutor pTrend = new ParallelTrendExetutor(parallelTrendThreads);
      	
      	System.out.println("presampling " + presampling);
      	System.out.println("sampling " + threshPercentage + sampling);
      	
      	// Calculate Trend According to Sampling Parameters.
      	if(!sampling)
      	{
      		// Sampling is OFF
      		// Display raw -> trend
      		
        	//trend = Trend.calculateTrend(metrics, window);
    		//result  = Trend.calculateTrend(metrics, window);       	
      		result  = pTrend.calculateTrend(metrics, window);       	
    		metrics = null;
      	}
      	else if(sampling && !presampling)
      	{
      		// Sampling is ON, Presampling is OFF
      		// Display raw -> trend -> sampling
      		
        	//trend = Trend.calculateTrend(metrics, window);        	
        	trend  = pTrend.calculateTrend(metrics, window); 
        	
        	metrics = null;
        	threshold = (int) Math.round(trend.length * threshPercentage);	    		
    		System.out.println("Thres: " + threshold);
        	//sample = Sampling.largestTriangleThreeBuckets(trend, threshold);
        	result  = Sampling.largestTriangleThreeBuckets(trend, threshold);
        	trend = null;
      	}
      	else if(sampling && presampling)
      	{
	      	// Sampling is ON, Presampling is ON
	      	// Display raw -> sampling -> trend
      		
        	threshold = (int) Math.round(metrics.length * threshPercentage);
    		System.out.println("Thres: " + threshold);
        	sample = Sampling.largestTriangleThreeBuckets(metrics, threshold);
        	metrics = null;
        	//trend = Trend.calculateTrend(sample, window);
        	//result  = Trend.calculateTrend(sample, window);   
        	result  = pTrend.calculateTrend(sample, window); 
        	
        	sample = null;
        }       

        LinkedHashMap<String, String> res = new LinkedHashMap<String, String>();
        for(int i = 0; i <= result.length - 1; i++)
		{
        	res.put(String.valueOf(result[i][0]), String.valueOf(result[i][1]));
		}
		
        //-
		System.out.println("Values After: " + res.size());
		//-
		
		return res;
	}
	
	/**
	 * Calculate trend.
	 *
	 * @param list
	 *            the list
	 * @return the linked hash map
	 */
	public LinkedHashMap<String, String> calculateTrend(List<MetricValue> list)
	{
		// Get SMA window from properties
		// Window will fall back to default 10, if property is unreadable
		int smaWindow = Integer.parseInt(globalProps.getProperty("trend.sma.window", "10"));
		
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
			long timeFrame = Math.abs(Long.parseLong(list.get(list.size() - 1).timestamp) - Long.parseLong(list.get(0).timestamp));
			smaWindow = 10;
		}
		
		return calculateTrend(list, smaWindow);
	}
}
