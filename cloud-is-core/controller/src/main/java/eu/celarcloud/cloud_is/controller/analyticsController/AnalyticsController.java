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

import eu.celarcloud.cloud_is.analysisModule.analyticsLib.Trend;
import eu.celarcloud.cloud_is.analysisModule.analyticsLib.Sampling;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.*;

// TODO: Auto-generated Javadoc
/**
 * The Class AnalyticsController.
 */
public class AnalyticsController {
	
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
        
     // Display raw -> trend -> sampling
    	trend = Trend.calculateTrend(metrics, window);
    	threshold = (int) Math.round(trend.length * 0.4);	    		
		System.out.println("Thres: " + threshold);
    	//sample = Sampling.largestTriangleThreeBuckets(trend, threshold);
    	result  = Sampling.largestTriangleThreeBuckets(trend, threshold);

        LinkedHashMap<String, String> res = new LinkedHashMap<String, String>();
        for(int i = 0; i <= result.length - 1; i++)
		{
        	res.put(String.valueOf(result[i][0]), String.valueOf(result[i][1]));
		}
        
        
        
        
        
		//
		//int threshold = 6;
		//Number[][] sample = Sampling.largestTriangleThreeBuckets(metrics, threshold);
		//Number[][] sample = metrics;
		
		//LinkedHashMap<String, String> res =  analysis.calculateTrend(stamps, values, window);
		//LinkedHashMap<String, String> res =  Trend.calculateTrend(sample, window);
		//metrics = Trend.calculateTrend(metrics, window);
		
		
		System.out.println("Values After: " + res.size());
		
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
		// TODO
		// Calculate windows
		int window = 10;
		
		return calculateTrend(list, window);
	}
}
