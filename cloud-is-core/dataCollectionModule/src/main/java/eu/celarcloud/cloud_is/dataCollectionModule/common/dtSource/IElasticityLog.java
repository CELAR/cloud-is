package eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource;

import java.util.List;


public interface IElasticityLog extends ILogEvents {
	public List<String> getEnforcedActions(String deplId, String name, String sTime, String eTime);	
	public List<String> getEnforcedActions(String deplId, String compId, String name, String sTime, String eTime);
}
