package eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource;

import java.util.Properties;

public interface ISourceLoader {
	
	/** The Constant TYPE_RESOURCES. */
	public final static Integer TYPE_RESOURCES = 0;
	
	/** The Constant TYPE_MONITORING. */
	public final static Integer TYPE_MONITORING = 1;
	
	/** The Constant TYPE_MONITORING. */
	public final static Integer TYPE_MONITORING_HISTORY = 2;
	
	/** The Constant TYPE_APPLICATION. */
	public final static Integer TYPE_APPLICATION = 3;
	
	/** The Constant TYPE_DEPLOYMENT. */
	public final static Integer TYPE_DEPLOYMENT = 4;
	
	public final static Integer TYPE_TOPOLOGY  = 5;
	
	/** The Constant TYPE_ELASTICITY. */
	public final static Integer TYPE_ELASTICITY = 6;
	
	public IDataSource getDtCollectorInstance(Integer sourceType);
	
	/**
	 * Inits the.
	 */
	public void init();
	
	/**
	 * Inits the.
	 *
	 * @param props
	 *            the props
	 */
	public void init(Properties props);
	
	/**
	 * Inits the.
	 *
	 * @param configPath
	 *            the config path
	 */
	public void init(String configPath);
	
	/**
	 * Inits the.
	 *
	 * @param configPath
	 *            the config path
	 * @param dataPath
	 *            the data path
	 */
	public void init(String configPath, String dataPath);
	public String getConfigPath();
	
	/**
	 * Gets the data path.
	 *
	 * @return the data path
	 */
	public String getDataPath();
	
	
	public IDataSource loadAppMetaInterface();
	public IDataSource loadDeplMetaInterface();
	public IDataSource loadMeteringInterface();
	public IDataSource loadMeteringHistoryInterface();
	public IDataSource loadTopologyInterface();
	
	// TODO
	// Add getter for each supported type
}
