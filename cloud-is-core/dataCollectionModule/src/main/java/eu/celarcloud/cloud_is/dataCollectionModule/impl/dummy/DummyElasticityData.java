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
package eu.celarcloud.cloud_is.dataCollectionModule.impl.dummy;

import java.util.ArrayList;
import java.util.List;

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IElasticityLog;

// TODO: Auto-generated Javadoc
/**
 * The Class Jcatascopia.
 */
public class DummyElasticityData implements IElasticityLog {

	@Override
	public List<String> getEnforcedActions(String deplId, String name, Long sTime, Long eTime) {
		List<String> list = new ArrayList<String>();
		
		//-
		int count = 4;
				
		for(int i = 0; i < count; i++)
		{
		    // nextInt is normally exclusive of the top value,
		    // so add 1 to make it inclusive
		    long randomNum = TestClass.randLong(sTime, eTime);
			
			//
			list.add(String.valueOf(randomNum));
		}
		
		return list;
	}

	@Override
	public List<String> getEnforcedActions(String deplId, String compId, String name, Long sTime, Long eTime) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
