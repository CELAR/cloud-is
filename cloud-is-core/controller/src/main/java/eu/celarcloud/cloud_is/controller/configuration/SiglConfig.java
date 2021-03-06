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

// TODO: This Class is not currently in use
/**
 * The Class SiglConfig.
 */
public class SiglConfig 
{
	
	/**
	 * Instantiates a new sigl config.
	 */
	private SiglConfig()
	{
	    // no code req'd
	}

	  /**
	 * Gets the singleton object.
	 *
	 * @return the singleton object
	 */
  	public static SiglConfig getSingletonObject()
	{
	    if (ref == null)
	        // it's ok, we can call this constructor
	        ref = new SiglConfig();		
	    return ref;
	}

	  /* (non-Javadoc)
  	 * @see java.lang.Object#clone()
  	 */
  	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException();
	}

	  /** The ref. */
  	private static SiglConfig ref;

}
