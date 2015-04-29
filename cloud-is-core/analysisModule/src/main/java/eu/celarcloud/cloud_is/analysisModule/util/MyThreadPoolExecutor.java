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
package eu.celarcloud.cloud_is.analysisModule.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class MyThreadPoolExecutor.
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {
	
	/** The start time. */
	private final ThreadLocal<Long> startTime  = new ThreadLocal<Long>();	

	/** The num tasks. */
	private final AtomicLong numTasks = new AtomicLong();
	
	/** The total time. */
	private final AtomicLong totalTime = new AtomicLong();
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MyThreadPoolExecutor.class.getName());
	
	/**
	 * Instantiates a new my thread pool executor.
	 *
	 * @param corePoolSize
	 *            the core pool size
	 * @param maximumPoolSize
	 *            the maximum pool size
	 * @param keepAliveTime
	 *            the keep alive time
	 * @param unit
	 *            the unit
	 * @param workQueue
	 *            the work queue
	 * @param handler
	 *            the handler
	 */
	public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) 
	{
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.ThreadPoolExecutor#beforeExecute(java.lang.Thread, java.lang.Runnable)
	 */
	protected void beforeExecute(Thread t, Runnable r) 
	{
		super.beforeExecute(t, r);
		LOG.debug(String.format("Thread %s: start %s", Thread.currentThread().getName(), r));
		startTime.set(System.nanoTime());
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.ThreadPoolExecutor#afterExecute(java.lang.Runnable, java.lang.Throwable)
	 */
	protected void afterExecute(Runnable r, Throwable t) 
	{
		try {
		    long endTime = System.nanoTime();
		    long taskTime = endTime - startTime.get();
		    numTasks.incrementAndGet();
		    totalTime.addAndGet(taskTime);
		    LOG.debug(String.format("Thread %s: end %s, time=%dns", Thread.currentThread().getName(), r, taskTime));
		} finally {
		    super.afterExecute(r, t);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.ThreadPoolExecutor#terminated()
	 */
	protected void terminated() 
	{
		try {
			LOG.info(String.format("Terminated: avg time=%dns", totalTime.get() / numTasks.get()));
		} finally {
		    super.terminated();
		}
	}

}
