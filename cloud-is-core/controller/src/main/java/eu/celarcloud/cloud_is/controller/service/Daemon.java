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

import org.apache.catalina.LifecycleException;
import org.slf4j.LoggerFactory;

/**
 * The Class Daemon.
 */
class Daemon {
	
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
		// TODO:NOTE
		// Redundant Check
		// Ensure we are running on java 7 or greater
		if (Integer.parseInt(System.getProperty("java.version").split("\\.")[1]) < 7) {
			// Print a WARNING
			//Logger logger = Logger.getLogger(Daemon.class.getName());
			org.slf4j.Logger logger = LoggerFactory.getLogger(Daemon.class.getName());
			logger.warn("Service Needs Java or greater to run Smoothly, " + System.getProperty("java.version") + " is installed"
					+ "\n\t" + "Service may crash in varius cases");
		}
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
	}
}
