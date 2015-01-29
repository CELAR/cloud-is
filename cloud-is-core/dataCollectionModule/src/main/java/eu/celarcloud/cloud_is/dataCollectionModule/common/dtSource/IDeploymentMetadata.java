package eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource;

import java.util.List;

import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Deployment;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.Metric;

public interface IDeploymentMetadata extends IDataSource{	
	/**
	 * Gets the recent deployments.
	 *
	 * @param limit
	 *            the limit
	 * @param status
	 *            the status
	 * @return the recent deployments
	 */
	public List<Deployment> getRecentDeployments(String limit, String status);
	
	public Deployment getDeployment(String deplId);
	
	public List<Metric> getDeploymentInstances(String deplId, String tierId, long sTime, long eTime);
	
	public List<Deployment> searchDeployments(long start_time, long end_time, String  status);
}
