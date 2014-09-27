package eu.celarcloud.cloud_is.dataCollectionModule.impl.celar;

import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.IDataSource;
import eu.celarcloud.cloud_is.dataCollectionModule.common.dtSource.ISourceLoader;

public class CollectorLoader implements ISourceLoader {

	
	
	public IDataSource getDtCollectorInstance(String sourceType, String uri)
	{
		// TODO
		// uri as funtion parameter is temporary and should be moved to IDataSource Implementations
		
		// Get CELAR Manager uri from configuration
		
		
		
		
		IDataSource DtCollectorInstance = null;		
		if(sourceType.equals(TYPE_MONITORING))
		{
			Jcatascopia temp = new Jcatascopia();
			temp.init(uri);
			DtCollectorInstance = temp;			
		}
		else if(sourceType.equals(TYPE_RESOURCES))
			DtCollectorInstance = null;
		else if(sourceType.equals(TYPE_APPLICATION))
		{
			CelarApplication temp = new CelarApplication();
			temp.init(uri);
			DtCollectorInstance = temp;
		}
		else if(sourceType.equals(TYPE_ELASTICITY))
			DtCollectorInstance = null;
		else
			DtCollectorInstance = null;
		
		
		return DtCollectorInstance;
	}
}
