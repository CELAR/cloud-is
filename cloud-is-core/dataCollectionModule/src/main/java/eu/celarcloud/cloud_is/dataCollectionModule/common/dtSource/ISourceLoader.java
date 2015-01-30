package eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource;

import java.util.Properties;

public interface ISourceLoader {
	public IDataSource getDtCollectorInstance(DataSourceType type);
	
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
