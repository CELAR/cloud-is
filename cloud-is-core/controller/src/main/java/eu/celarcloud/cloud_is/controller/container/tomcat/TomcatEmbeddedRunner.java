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
package eu.celarcloud.cloud_is.controller.container.tomcat;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.LoggerFactory;

import eu.celarcloud.cloud_is.controller.container.IEmbeddedRunner;

/**
 * The Class TomcatEmbeddedRunner.
 */
public class TomcatEmbeddedRunner implements IEmbeddedRunner{
	
	/** The server. */
	private Tomcat server;
	
	/** The server base. */
	private File serverBase; 
	
	/** The port. */
	private int port;
	
	/** The is running. */
	private boolean isRunning;

	/** The Constant LOG. */
	//private static final Logger LOG = Logger.getLogger(TomcatEmbeddedRunner.class.getName());
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TomcatEmbeddedRunner.class.getName());
	
	/** The Constant isInfo. */
	//private static final boolean isInfo = LOG.isLoggable(Level.INFO);
	private static final boolean isInfo = true;
	
	private static int DEFAULT_PORT = 8282;
	
	/**
	 * Instantiates a new tomcat embedded runner.
	 */
	public TomcatEmbeddedRunner() {
		this(DEFAULT_PORT);
	}

	/**
	 * Instantiates a new tomcat embedded runner.
	 *
	 * @param port
	 *            the port
	 */
	public TomcatEmbeddedRunner(int port) {		
		this("", port);
	}
	
	/**
	 * Instantiates a new tomcat embedded runner.
	 *
	 * @param baseDir
	 *            the base dir
	 */
	public TomcatEmbeddedRunner(String baseDir) {
		this(baseDir, DEFAULT_PORT);
	}

	/**
	 * Create a new Tomcat embedded server instance. Setup looks like:
	 *
	 * @param baseDir
	 *            the base dir
	 * @param port
	 *            Port number to be used for the embedded Tomcat server
	 */
	public TomcatEmbeddedRunner(String baseDir, int port) {
		// TODO
		// Implement checks for port e,g port !=0
		// Check if the port is free to bind and throw error, or fallback to default, 
		// or leave the tomcatContainer to get the next first available port
		if(port == 0)
			port = DEFAULT_PORT;		
		this.port = port;

		this.server = new Tomcat();
		// If the port is not free to bind to
		// tomcat container will look for the next first available port to use
		this.server.setPort(port);		
		// set up context 
		// The location from where the files will be served
		if(baseDir == null || baseDir.isEmpty())
			baseDir = System.getProperty("java.io.tmpdir");		
		this.serverBase = new File(baseDir);

		this.server.setBaseDir(baseDir);
		System.out.println("Server Base Dir: " + this.serverBase);
		
		// Add AprLifecycleListener
		AprLifecycleListener listener = new AprLifecycleListener();
		this.server.getServer().addLifecycleListener(listener);		
				
		// true sets the log level to WARN for the loggers that log information on Tomcat start up. 
		// This prevents the usual startup information being logged. 
		// false sets the log level to the default level of INFO.
		//this.server.setSilent(true);
		
		// Register shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if (isRunning) {
					if (isInfo)
						LOG.info("Stopping the Tomcat server, through shutdown hook");
					try {
						if (server != null) {
							server.stop();
						}
					} catch (LifecycleException e) {
						//LOG.log(Level.SEVERE, "Error while stopping the Tomcat server, through shutdown hook", e);
						LOG.error("Error while stopping the Tomcat server, through shutdown hook {}", e);
					}
				}
			}
		});
	}	
	
	/**
	 * Start the tomcat embedded server.
	 *
	 * @throws LifecycleException
	 *             the lifecycle exception
	 */
	public void start() throws LifecycleException {
		if (isRunning) {
			//LOG.log(Level.WARNING, "Tomcat server is already running @ port="+ this.port + "; ignoring the start");
			 LOG.warn("Tomcat server is already running @ port={}; ignoring the start; ignoring the start", port);
			return;
		}

		if (isInfo)
			LOG.info("Starting Tomcat at server @ port=" + this.port + " in: " + new File(server.getHost().getAppBase()).getAbsolutePath());
			//LOG.log(Level.INFO, "Starting Tomcat at server @ port=" + this.port + " in: " + new File(server.getHost().getAppBase()).getAbsolutePath());

		server.start();
		if (isInfo)
			LOG.info("Tomcat server listens at port=" + this.server.getConnector().getPort());	
			//LOG.log(Level.INFO, "Tomcat server listens at port=" + this.server.getConnector().getPort());		
		isRunning = true;		
		server.getServer().await();		
	}

	/**
	 * Stop the tomcat embedded server.
	 *
	 * @throws LifecycleException
	 *             the lifecycle exception
	 */
	public void stop() throws LifecycleException {
		if (!isRunning) {
			LOG.warn("Tomcat server is not running @ port=" + this.port);
			//LOG.log(Level.WARNING, "Tomcat server is not running @ port=" + this.port);
			return;
		}

		if (isInfo)
			LOG.info("Stopping the Tomcat server");

		
		try {
			server.stop();
			server.destroy();
	        FileUtils.deleteDirectory(new File("work"));
	        FileUtils.deleteDirectory(new File("tomcat." + this.port));
	    } catch (LifecycleException e) {
	        throw new RuntimeException(e);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
		
		isRunning = false;
	}

	/**
	 * Checks if is running.
	 *
	 * @return true, if is running
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Gets the server.
	 *
	 * @return the server
	 * @throws LifecycleException
	 *             the lifecycle exception
	 */
	public Tomcat getServer() {
		return this.server;
	}
	
	public File getServerBase() {
		return this.serverBase;
	}
	
	/**
	 * Gets the application url.
	 *
	 * @param appName
	 *            the app name
	 * @return the application url
	 */
	public String getApplicationUrl(String appName) {
	    return String.format("http://%s:%d/%s", this.server.getHost().getName(),
	    		this.port, appName);
	}

}