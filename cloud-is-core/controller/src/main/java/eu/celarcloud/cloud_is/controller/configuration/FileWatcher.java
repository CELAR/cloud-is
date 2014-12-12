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
package eu.celarcloud.cloud_is.controller.configuration;

import java.io.File;
import java.util.TimerTask;

// TODO: Auto-generated Javadoc
/**
 * The Class FileWatcher.
 */
public abstract class FileWatcher extends TimerTask {
	  
  	/** The time stamp. */
  	private long timeStamp;
	  
  	/** The file. */
  	private File file;

	  /**
	 * Instantiates a new file watcher.
	 *
	 * @param file
	 *            the file
	 */
  	public FileWatcher( File file) {
	    this.file = file;
	    this.timeStamp = file.lastModified();
	  }

	  /* (non-Javadoc)
  	 * @see java.util.TimerTask#run()
  	 */
  	public final void run() {
	    long timeStamp = file.lastModified();

	    if( this.timeStamp != timeStamp ) {
	      this.timeStamp = timeStamp;
	      onChange(file);
	    }
	  }

	  /**
	 * On change.
	 *
	 * @param file
	 *            the file
	 */
  	protected abstract void onChange( File file );
	}