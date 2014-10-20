package eu.celarcloud.cloud_is.controllerModule.analyticsController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import eu.celarcloud.cloud_is.analysisModule.Analysis;
import eu.celarcloud.cloud_is.dataCollectionModule.common.beans.*;

public class AnalyticsController {
	public LinkedHashMap<String, String> calculateTrend(List<Metric> list, int window)
	{
		Analysis analysis = new Analysis();
		long[] stamps = new long[list.size()];
		double[] values = new double[list.size()];
		int index = 0;
		
		for (Metric m : list) {
			stamps[index] = Long.parseLong(m.timestamp);
			values[index] = Double.parseDouble(m.value);
			index++;
		}
		return analysis.calculateTrend(stamps, values, window);
	}
	
	public LinkedHashMap<String, String> calculateTrend(List<Metric> list)
	{
		// TODO
		// Calculate windows
		int window = 10;
		
		return calculateTrend(list, window);
	}
}
