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
package eu.celarcloud.cloud_is.controllerModule.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class Config.
 */
public class Config {
	
	/** The config. */
	private Properties config;
	 
	/**
	 * Instantiates a new config.
	 *
	 * @param path
	 *            the path
	 */
	public Config(String path)
	{
		try 
		{
			this.parse(path);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the property.
	 *
	 * @param name
	 *            the name
	 * @return the property
	 */
	public String getProperty(String name)
	{
		return config.getProperty(name);		
	}
	
	/**
	 * Gets the property list.
	 *
	 * @param name
	 *            the name
	 * @return the property list
	 */
	public String[] getPropertyList(String name)
	{
		return config.getProperty(name).split(",");
	}
	
	/**
	 * Parses the.
	 *
	 * @param path
	 *            the path
	 * @return the properties
	 * @throws Exception
	 *             the exception
	 */
	private Properties parse(String path) throws Exception
	{
		this.config = new Properties();		
		try 
		{
			FileInputStream fis = new FileInputStream(path);
			this.config.load(fis);
			if (fis != null)
	    		fis.close();
		} 
		catch (FileNotFoundException e)
		{
			throw new Exception("config file not found");
		} 
		catch (IOException e)
		{
			throw new Exception("config file parsing error");
		}		
		return config;
	}
}
