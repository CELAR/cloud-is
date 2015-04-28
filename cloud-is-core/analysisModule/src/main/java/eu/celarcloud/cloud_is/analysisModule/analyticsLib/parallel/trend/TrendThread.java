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
 * The Class TrendThread.
 */
public class TrendThread implements Runnable {
	
	/** The destination. */
	Number[][] destination;
	
	/** The initial. */
	Number[][] initial;
	
	/** The start. */
	Integer start;
	
	/** The window. */
	Integer window;
	
	/** The size. */
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
	public TrendThread(Number[][] destination, Number[][] initial, Integer start, Integer window, Integer size){
		this.destination = destination;
		this.initial = initial;
		this.start = start;
		this.window = window;
		this.size = size;
		
		System.out.println(start +"|"+ window +"|"+ size);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		double sum = 0;
		
		for(int i = this.start; i < (this.start + size); i++)
		{
			if(i == this.start)
			{
				for(int j = (this.start - this.window); j < this.start; j++)
					sum += (double)this.initial[j][1];
			}
			sum = sum - (double)this.initial[i - this.window][1] + (double)this.initial[i][1];
			this.destination[i][0] = this.initial[i][0];
			this.destination[i][1] = sum / this.window;
		}
	}
}