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
package eu.celarcloud.cloud_is.controller.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.LifecycleException;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class Daemon.
 */
class Daemon {
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Daemon.class.getName());
			
	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws LifecycleException
	 *             the lifecycle exception
	 */
	public static void main(final String[] args) throws IOException, LifecycleException {
		final Object lock = new Object();
		
		// TODO:NOTE
		// Redundant Check
		// Ensure we are running on java 7 or greater
		if (Integer.valueOf(System.getProperty("java.version").split("\\.")[1]).intValue() < 7) {
			// Print a WARNING
			//Logger logger = Logger.getLogger(Daemon.class.getName());
			org.slf4j.Logger logger = LoggerFactory.getLogger(Daemon.class.getName());
			logger.warn("Service Needs Java or greater to run Smoothly, " + System.getProperty("java.version") + " is installed"
					+ "\n\t" + "Service may crash in varius cases");
		}
		// Print Heap Site
		long heapMaxSize = Runtime.getRuntime().maxMemory();		
		LOG.info("Available Heap: " + formatSize(heapMaxSize));
		
		/*
		 * Start Loading Submodules
		 */
		// Load Information System Service
		/*
		try {
			if (args.length > 0)
				new InformationSystemServer(args[0], args[1]);
			else {
				String workingDir = ".";
				// String OS = System.getProperty("os.name",
				// "generic").toLowerCase(Locale.ENGLISH);
				// if (OS.indexOf("win") >= 0) {
				// workingDir = "";
				// }
				new InformationSystemServer(workingDir, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		*/
		
		// Register shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutdown Invoked..");
				System.out.println("Notifying Block Lock..");
				synchronized (lock) { lock.notify(); }
			}
		});
		
		// Spawn child threads (the actual processes)
		ExecutorService es = Executors.newScheduledThreadPool(5);
		try {
			if (args.length > 0)
				//new Thread(new InformationSystemServerNew(args[0], args[1])).start();
			es.execute(new InformationSystemServer(args[0], args[1]));
			else {
				String workingDir = ".";
				// String OS = System.getProperty("os.name",
				// "generic").toLowerCase(Locale.ENGLISH);
				// if (OS.indexOf("win") >= 0) {
				// workingDir = "";
				// }
				es.execute(new InformationSystemServer(workingDir, null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		// Set a synchronous lock, in order to
		// Prevent 'Main' from exiting
		try {
			System.out.println("Setting lock and Waiting / Blocking");
	        synchronized (lock) {
	            while (true) {
	                lock.wait();
	            }
	        }
	    } catch (InterruptedException e) {
	    	e.printStackTrace();
	    }
		
	    // Do something after we were interrupted ...
		System.out.println("Shutdown child processes..");
		
		// Shutdown child processes 	
		es.shutdown();
		try {
			boolean finshed = es.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// all tasks have finished or the time has been reached.
		// Exit Main
		System.out.println("Exiting main..");
		System.exit(0);
	}
	
	/**
	 * Format the size from bytes to large possible order (kb, mb, gb, tb)
	 * Initial Idea from SO - Converting KB to MB, GB, TB dynamically (http://stackoverflow.com/a/20556766/2279200)
	 *
	 * @param size
	 *            the size
	 * @return the string
	 */
	private static String formatSize(long size) {
	    String hrSize = null;

	    double b = size;
	    double k = size/1024.0;
	    double m = ((size/1024.0)/1024.0);
	    double g = (((size/1024.0)/1024.0)/1024.0);
	    double t = ((((size/1024.0)/1024.0)/1024.0)/1024.0);

	    DecimalFormat dec = new DecimalFormat("0.00");

	    if ( t>1 ) {
	        hrSize = dec.format(t).concat(" TB");
	    } else if ( g>1 ) {
	        hrSize = dec.format(g).concat(" GB");
	    } else if ( m>1 ) {
	        hrSize = dec.format(m).concat(" MB");
	    } else if ( k>1 ) {
	        hrSize = dec.format(k).concat(" KB");
	    } else {
	        hrSize = dec.format(b).concat(" Bytes");
	    }

	    return hrSize;
	}
}
