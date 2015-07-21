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
package eu.celarcloud.cloud_is.dataCollectionModule.common.beans;

import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class MetricValue.
 */
public class MetricValue implements IOccurance {
	
	/** The timestamp. 
	 *  Time in unix time stamp format (milliseconds since Jan 1, 1970)
	 * */
	public long timestamp;
	
	/** The value. */
	public double value;
	
	/**
	 * Instantiates a new metric value.
	 */
	public MetricValue() {
	        
	}
	 
	/**
	 * Instantiates a new metric value.
	 *
	 * @param timestamp
	 *            the timestamp
	 * @param value
	 *            the value
	 */
	public MetricValue(long timestamp, double value) {
		this.timestamp = timestamp;
		this.value = value;
	}
	 
	/**
	 * Instantiates a new metric value.
	 *
	 * @param timestamp
	 *            the timestamp
	 * @param value
	 *            the value
	 */
	public MetricValue(String timestamp, String value) {
		this(Long.valueOf(timestamp).longValue(), Double.valueOf(value).doubleValue());
	}
	
	/* (non-Javadoc)
	 * @see eu.celarcloud.cloud_is.dataCollectionModule.common.beans.IBean#toJSONObject()
	 */
	@Override
	public JSONObject toJSONObject() {
		JSONObject metric;
		metric = new JSONObject();
			metric.put("timestamp", this.timestamp);
	    	metric.put("value", this.value);
		return metric;
	}
}
