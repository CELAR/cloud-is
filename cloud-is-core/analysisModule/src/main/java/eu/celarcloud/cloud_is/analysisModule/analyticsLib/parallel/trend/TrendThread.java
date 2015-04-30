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
package eu.celarcloud.cloud_is.analysisModule.analyticsLib.parallel.trend;

// TODO: Auto-generated Javadoc
/**
 * The class Implement the Simple Moving average smoothing algorithm in a thread-like manner.
 * each thread calculates the SMA for a portion of the array. Initial array is that portion. 
 * Thread writes the result in a shared (among the threads) array. 
 */
public class TrendThread implements Runnable {
	
	/** The calculated shared trend array*/
	Number[][] destination;
	
	/** The array with the initial (before smoothing) values */
	Number[][] initial;
	
	/** Indicates the starting index from where the current thread will start writting the trend values in the destination array. */
	Integer start;
	
	/** The Simple Moving Average window */
	Integer window;
	
	/** The amount of values that the thread with calculate the SMA on. */
	Integer size;
	
	/**
	 * Instantiates a new trend thread.
	 *
	 * @param destination
	 *            the destination
	 * @param initial
	 *            the initial
	 * @param start
	 *            the start
	 * @param window
	 *            the window
	 * @param size
	 *            the size
	 */
	public TrendThread(Number[][] destination, Number[][] initial, Integer start, Integer window, Integer size) {
		this.destination = destination;
		this.initial = initial;
		this.start = start;
		this.window = window;
		this.size = size;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// Calculate the initial average
		// This average is the (previous) knowledge that the thread needs to have / calculate
		// In order to proceed with the smoothing of the input.
		double sum = 0;
		for(int j = 0; j < this.window; j++)
			sum += (double)this.initial[j][1];
		
		for(int i = this.window; i < (this.window + this.size); i++)
		{
			sum = sum - (double)this.initial[i - this.window][1] + (double)this.initial[i][1];
			this.destination[this.start + (i - this.window)][0] = this.initial[i][0];
			this.destination[this.start + (i - this.window)][1] = sum / this.window;
		}
	}
}