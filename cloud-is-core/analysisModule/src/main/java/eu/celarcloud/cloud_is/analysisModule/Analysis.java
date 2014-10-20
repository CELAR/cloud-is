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
package eu.celarcloud.cloud_is.analysisModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Median;


// TODO: Auto-generated Javadoc
/**
 * The Class Analysis.
 */
public class Analysis {

	/**
	 * Calculate average.
	 *
	 * @return the double
	 */
	public double calculateAverage()
	{
		double avg = 0.0;
		
		
		return avg;		
	}
	
	public LinkedHashMap<String, String> calculateTrend(long[] stamps, double[] values, int window)
	{
		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
		
		DescriptiveStatistics ds = new DescriptiveStatistics(window);
		// Init
		for(int i = 0; i <= window - 2; i++)
			ds.addValue(values[i]);
		
		for(int i = window - 2; i <= values.length - 1; i++)
		{
			ds.addValue(values[i]);
			hm.put(String.valueOf(stamps[i]), String.valueOf(ds.getMean()));
		}
    	 
		
		
		
		/*
		List<Long> smooth_stamps = new ArrayList<Long>();
		int end = window;
		while(end < stamps.length)
		{
			smooth_stamps.add(stamps[end]);			
			end += window;
			
			if(end - stamps.length > 0)
				smooth_stamps.add(stamps[stamps.length - 1]);
		}
		
		//double[] smooth_stamps = Average.sma(stamps, window);
		double[] smooth_values = Average.sma(values, window);
		*/
		
			
		return hm;		
	}
}
