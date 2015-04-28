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


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import eu.celarcloud.cloud_is.analysisModule.util.MyRejectedExecutionHandeler;
import eu.celarcloud.cloud_is.analysisModule.util.MyThreadPoolExecutor;

// TODO: Auto-generated Javadoc
/**
 * The Class ParallelTrendExetutor.
 */
public class ParallelTrendExetutor {
	
	/** The thread_num. */
	protected int thread_num;
	
	/** The executor. */
	public ExecutorService executor; 
			
	
	
	/**
	 * Instantiates a new parallel trend exetutor.
	 *
	 * @param thread_num
	 *            the thread_num
	 */
	public ParallelTrendExetutor (int thread_num)
	{
		this.thread_num = thread_num;		
		
		this.executor = new ThreadPoolExecutor(this.thread_num, this.thread_num, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10000, true),
                new MyRejectedExecutionHandeler()) {
					@Override
					protected void afterExecute(Runnable r, Throwable t)
					{
						// Do some logging here
						//
						super.afterExecute(r, t);
					}
				};
		
				
		/*		
		this.executor = new MyThreadPoolExecutor(this.thread_num, this.thread_num, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10000, true),
                new MyRejectedExecutionHandeler());
                */
	}
	
	
	/**
	 * Terminate generator.
	 */
	protected void terminateGenerator()
	{
		this.executor.shutdown();
		while (!executor.isTerminated())
		{
			try 
			{
				this.executor.awaitTermination(1, TimeUnit.MINUTES);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}		
	}
		
	
	/**
	 * Calculate trend.
	 *
	 * @param values
	 *            the values
	 * @param window
	 *            the window
	 * @return the number[][]
	 */
	public Number[][] calculateTrend(Number[][] values, int window)
	{		
		int chunkCount = this.thread_num; // ~> thread number
		int chunkSize = (int) Math.floor((values.length - window) / chunkCount);
		
		Number trend[][] = new Number[values.length][2];
		// Insert initial values (smoothed??)
		double sum = 0;
		for(int i=0; i < window; i++)
		{ 
			sum += (double)values[i][1];
			trend[i][0] = values[i][0];
			trend[i][1] = sum / (i + 1);
		}		
		// Start Smoothing
		long startTime = System.nanoTime();		
		for(int i=0; i < chunkCount; i++)
		{ 
			int size = chunkSize;
			if(i ==  chunkCount - 1)
				size = chunkSize + ((values.length - window) % chunkCount);
				
			this.executor.execute(new TrendThread(trend, values, window + (i * chunkSize), window, size));
		}
		//-
		terminateGenerator();
		//
		System.out.println("Finished Trend Calculation in: " + (System.nanoTime() - startTime));
		
		return trend;
	}
}
