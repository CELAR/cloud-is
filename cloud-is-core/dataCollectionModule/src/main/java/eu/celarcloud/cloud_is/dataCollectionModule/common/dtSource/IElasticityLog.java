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
package eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource;

import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Interface IElasticityLog.
 */
public interface IElasticityLog extends ILogEvents {
	
	/**
	 * Gets the enforced actions.
	 *
	 * @param deplId
	 *            the depl id
	 * @param name
	 *            the name
	 * @param sTime
	 *            the s time
	 * @param eTime
	 *            the e time
	 * @return the enforced actions
	 */
	public List<String> getEnforcedActions(String deplId, String name, Long sTime, Long eTime);	
	
	/**
	 * Gets the enforced actions.
	 *
	 * @param deplId
	 *            the depl id
	 * @param compId
	 *            the comp id
	 * @param name
	 *            the name
	 * @param sTime
	 *            the s time
	 * @param eTime
	 *            the e time
	 * @return the enforced actions
	 */
	public List<String> getEnforcedActions(String deplId, String compId, String name, Long sTime, Long eTime);
}
