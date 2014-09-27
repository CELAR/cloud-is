package eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource;

public interface ISourceLoader {

	/** The Constant TYPE_RESOURCES. */
	public final static String TYPE_RESOURCES = "resources";
	
	/** The Constant TYPE_MONITORING. */
	public final static String TYPE_MONITORING = "monitoring";
	
	/** The Constant TYPE_APPLICATION. */
	public final static String TYPE_APPLICATION = "application";
	
	/** The Constant TYPE_ELASTICITY. */
	public final static String TYPE_ELASTICITY = "elasticity";
	
	public IDataSource getDtCollectorInstance(String sourceType, String uri);
}
