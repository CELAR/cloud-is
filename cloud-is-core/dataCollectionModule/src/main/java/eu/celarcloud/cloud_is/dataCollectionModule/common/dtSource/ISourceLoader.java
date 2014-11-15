package eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource;

import java.util.Properties;

public interface ISourceLoader {
	
	/** The Constant TYPE_RESOURCES. */
	public final static String TYPE_RESOURCES = "resources";
	
	/** The Constant TYPE_MONITORING. */
	public final static String TYPE_MONITORING = "monitoring";
	
	/** The Constant TYPE_MONITORING. */
	public final static String TYPE_MONITORING_HISTORY = "monitoringHistory";
	
	/** The Constant TYPE_APPLICATION. */
	public final static String TYPE_APPLICATION = "application";
	
	/** The Constant TYPE_ELASTICITY. */
	public final static String TYPE_ELASTICITY = "elasticity";
	
	public IDataSource getDtCollectorInstance(String sourceType);
	
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
	
	// TODO
	// Add getter for each supported type
}
