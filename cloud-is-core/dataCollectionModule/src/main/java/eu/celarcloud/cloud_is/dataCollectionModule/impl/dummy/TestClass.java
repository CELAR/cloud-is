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

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class TestClass.
 */
public class TestClass {
	
	/**
	 * Gets the cpu data.
	 *
	 * @return the cpu data
	 */
	public static String getCpuData()
	{
		JSONArray data = new JSONArray();
		
		data.put(1);
	    data.put(2);
	    data.put(4);
	    data.put(8);
	    data.put(7);
	    data.put(7);
	    data.put(4);
	    data.put(2);
	    data.put(3.5);
	    data.put(3.5);
	    data.put(1);
	    data.put(1);		
		
		return data.toString();
	}
	
	/**
	 * Gets the random data series.
	 *
	 * @param count
	 *            the count
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return the random data series
	 */
	public static String getRandomDataSeries(int count, int min, int max)
	{
		JSONArray data = new JSONArray();
		double randNum = 0.0;
		for(int i = 0; i < count; i++)
		{
			if(i == 0)
				randNum = randDouble(min, max);
			else
				randNum = randDoubleKnoledge((int) Math.round(randNum), min, max);
			
			data.put(randNum);				
		}
		
		return data.toString();
	}
	
	
	/**
	 * Gets the action data.
	 *
	 * @return the action data
	 */
	public static String getActionData()
	{
		JSONObject data = new JSONObject();
				data.put("Action 1",8);
				data.put("Action 2",3);
		return data.toString();
	}
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max)
	{

	    // Usually this should be a field rather than a method variable so
	    // that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	/**
	 * Rand double.
	 *
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return the double
	 */
	public static double randDouble(int min, int max) 
	{
		Random rand = new Random();
		
	    double randomNum = randInt(min, max - 1);
	    randomNum += rand.nextDouble();
	    
	    return randomNum;
	}
	
	/**
	 * Rand double knoledge.
	 *
	 * @param prev
	 *            the prev
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return the double
	 */
	public static double randDoubleKnoledge(int prev, int min, int max) 
	{
		int nmin = prev - (prev/3) < min ? prev : prev - (prev/3);
		int nmax = prev + (prev/3) > max ? prev : prev + (prev/3);
	    
	    return randDouble(nmin, nmax);
	}
	
}
