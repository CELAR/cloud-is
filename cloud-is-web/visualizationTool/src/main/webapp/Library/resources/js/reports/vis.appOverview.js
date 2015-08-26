function appOverview () { 	
 	var drawAreaChart = function (data, instanesData, containerID) {
 		var instancesChartData = [];
		var instancesDataTable;
		var instancesTierCounter = 0;
		$.each(instanesData, function(tierName, tierData) {
			var tierTable = [];
			var tierDataTable = new google.visualization.DataTable();
			//tierTable[0] = ["Time", capitaliseFirstLetter(tierName)];
			tierDataTable.addColumn('datetime', 'Time');
			tierDataTable.addColumn('number', capitaliseFirstLetter(tierName));
			$.each(tierData, function(index, metricObj) {
				var date = new Date(parseInt(metricObj.timestamp) * 1000);
				tierDataTable.addRow([date, parseInt(metricObj.value)]);
			});
			if(instancesTierCounter == 0)
			{
				instancesDataTable = tierDataTable;
			}
			else
			{
				// Find Columns to join
				var cols=[];
				for(k = 0; k < instancesTierCounter; k++)
				{
					cols[k] = k + 1;
				}
				instancesDataTable = google.visualization.data.join(instancesDataTable, tierDataTable, 'inner', [[0,0]], cols, [1]);
			}
			instancesTierCounter++;
		}); 
		
		instancesDataTable.addColumn('number', "Reserved");
		instancesDataTable.addColumn('number', "On Demend");
		var res = 0;
		for(i = 0; i < instancesDataTable.getNumberOfRows() - 1; i++)
		{			
			var dem = 0;
			for(j = 1; j < instancesDataTable.getNumberOfColumns() - 1; j++)
			{
				if(i == 0)
				{
					// Take the Reserved
					dem = 0;
					res = res +  instancesDataTable.getValue(i, j);
				}
				dem = dem +  instancesDataTable.getValue(i, j);
			}
			dem = dem - res
			if(dem < 0)
				dem = 0;			
			
			instancesDataTable.setCell(i, instancesDataTable.getNumberOfColumns() - 2, res);
			instancesDataTable.setCell(i, instancesDataTable.getNumberOfColumns() - 1, dem);			
		}
 		
 		// Create a dataView
		var view = new google.visualization.DataView(instancesDataTable);
 		view.setColumns([0, instancesDataTable.getNumberOfColumns() - 2, instancesDataTable.getNumberOfColumns() - 1]);
		
 		/*
 		// Some raw data (not necessarily accurate)
 		  var data = google.visualization.arrayToDataTable([
 		    ['Time',   'Reserved', 'On Demand'],
 		    ['07:00',    6,      0],
 		    ['08:00',    6,      5],
 		    ['09:00',    6,      11],
 		    ['10:00',    6,      19],
 		    ['11:00',    6,      22],
 		   	['12:00',    6,      16],
		    ['13:00',    6,      10],
 		  	['14:00',    6,      12],
		    ['15:00',    6,      18]
 		  ]);
 		  */

 		  // Create and draw the visualization.
 		  var ac = new google.visualization.AreaChart(document.getElementById(containerID));
 		  ac.draw(view, {
 		    title : 'Total Active Instances per Time',
 		    isStacked: true,
 		    height: 400,
 		    vAxis: {title: "Number of Instances"},
 		    hAxis: {title: "Tiime"}
 		  });
 	};
 	/*
 	var drawColChart = function (data, containerID) {
 		// Create and populate the data table.
 		  var data = google.visualization.arrayToDataTable([
 		    ['Year', 'Cpu', 'Ram', 'Disk IO', 'Net IO'],
 		    ['m1.tiny',  13,   38,       97,       11],
 		    ['m1.small',  15,   39,       92,       11],
 		    ['m1.medium',  15,   40,       10,      11],
 		    ['m1.large',  16,   46,       94,       11],
 		    ['m1.xlarge',  19,   40,       10,      12]
 		  ]);
 		

 		  // Create and draw the visualization.
 		 var cc = new google.visualization.ColumnChart(document.getElementById(containerID));
 		 cc.draw(data, {
 			title:"Average (CPU / RAM / DISK IO) utilization per Instance type",
	        height:400,
	        hAxis: {title: "Instance Type"}
 		 });
	};
	
	
	var drawLineChart = function (data, containerID) {
		  // Create and populate the data table.
		  var data = google.visualization.arrayToDataTable([
		    ['x', 'Cpu Percentage'],
		    ['07:00',   10],
		    ['07:30',   20],
		    ['08:00',   40],
		    ['08:30',   80],
		    ['09:00',   70],
		    ['09:30',   70],
		    ['10:00',   80],
		    ['10:30',   40],
		    ['11:00',   20],
		    ['11:30',   35],
		    ['12:00',   30],
		    ['12:30',   35],
		    ['13:00',   50],
		    ['13:30',   40],
		    ['14:00',   25],
		    ['14:30',   20],
		    ['15:00',   10]
		  ]);
		  

		  // Create and draw the visualization.
		  lc = new google.visualization.LineChart(document.getElementById(containerID));
		  lc.draw(data, {curveType: "function",
		                  height: 300,
		                  vAxis: {maxValue: 100}
		  				}
		          );
		}
	*/
	var costToInstanceCount = function (dataObj,  instanesData, costData, containerID) {
		// FormalizeData 
		// Construct an array with the data 
		// parsed from json input.
		var chartData;
		
		// We assume that 'instanesData' and 'costData' are
		// formulated under the same granularity and are
		// indicated by the same time stamp
		// TODO
		// Try to identify the maximum dimension and fill with null
		// the missing data
		
		var instancesChartData = [];
		var instancesDataTable;
		var instancesTierCounter = 0;
		$.each(instanesData, function(tierName, tierData) {
			var tierTable = [];
			var tierDataTable = new google.visualization.DataTable();
			//tierTable[0] = ["Time", capitaliseFirstLetter(tierName)];
			tierDataTable.addColumn('datetime', 'Time');
			tierDataTable.addColumn('number', capitaliseFirstLetter(tierName));
			$.each(tierData, function(index, metricObj) {
				var date = new Date(parseInt(metricObj.timestamp) * 1000);
				tierDataTable.addRow([date, parseInt(metricObj.value)]);
			});
			if(instancesTierCounter == 0)
			{
				instancesDataTable = tierDataTable;
			}
			else
			{
				// Find Columns to join
				var cols=[];
				for(k = 0; k < instancesTierCounter; k++)
				{
					cols[k] = k + 1;
				}
				instancesDataTable = google.visualization.data.join(instancesDataTable, tierDataTable, 'inner', [[0,0]], cols, [1]);
			}
			instancesTierCounter++;
		});
		//console.log(chartData_1);
		//-
		
		var costDataTable;
		var costTierCounter = 0;
		$.each(costData, function(tierName, tierData) {
			var tierDataTable = new google.visualization.DataTable();
			tierDataTable.addColumn('datetime', 'Time');
			tierDataTable.addColumn('number', capitaliseFirstLetter(tierName) + " Cost");
			$.each(tierData, function(index, metricObj) {
				var date = new Date(parseInt(metricObj.timestamp) * 1000);
				tierDataTable.addRow([date, parseFloat(metricObj.value)]);
			});
			if(costTierCounter == 0)
			{
				costDataTable = tierDataTable;
			}
			else
			{
				// Find Columns to join
				var cols=[];
				for(k = 0; k < costTierCounter; k++)
				{
					cols[k] = k + 1;
				}
				costDataTable = google.visualization.data.join(costDataTable, tierDataTable, 'full', [[0,0]], cols, [1]);
			}
			costTierCounter++;
		});
		
		var colsI=[];
		for(k = 0; k < instancesTierCounter; k++)
		{
			colsI[k] = k + 1;
		}	
		var colsC=[];
		for(k = 0; k < costTierCounter; k++)
		{
			colsC[k] = k + 1;
		}

		console.log(colsI);
		console.log(colsC);
		dataObj = google.visualization.data.join(instancesDataTable, costDataTable, 'full', [[0,0]], colsI, colsC);
			
		
		//-
		// For debugging print the data table
		//var visualization = new google.visualization.Table(document.getElementById('topologyExplorer'));
		//visualization.draw(dataObj, {
		//    sort: "disable",
		//    allowHtml: true,
		//});
		
		// Build Chart Options
		var options = {
			vAxes: {0: {title: "Instances Count", format: '0'},
			        1: {title: "Cost ($)"}
				},
		    hAxis: {title: "Time"},
		    height: 300,
		    seriesType: "lines",
		    series: {
		    		2: {type: "line", targetAxisIndex:1},
		    		3: {type: "line", targetAxisIndex:1}
		    	},
		    	interpolateNulls: true,
		    allowHtml: true
		  };
		// Draw Chart
		var chart = new google.visualization.ComboChart(document.getElementById(containerID));
		chart.draw(dataObj, options);
	}

    
	this.onDataReady = function(jsonObj) {
		if(jQuery.type(jsonObj) === "string")
			jsonObj = $.parseJSON(jsonObj);
				
		costToInstanceCount(null, jsonObj.instances, jsonObj.cost, "chartHolder_costToInstanceCount");		
		//drawLineChart(data, containerID);
		//drawColChart(null, "chartHolder_Cols");
		drawAreaChart(null, jsonObj.instances, "chartHolder_area");
		//buildPerformanceAnalysisPane();
		
	};
	    
	this.getAjaxStuff = function(params, onSuccess){
		// Build Request part url
		var partUrl = '';
		
		var compId = params.compId.trim();
		if(!(compId.length === 0 || !compId))
			partUrl += '/tier/' + compId;	
		
		// Build Request Parameters		
		var qString = "";
		$.each(params.metrics, function (index, metric){
			qString	+= '&metrics=' + metric;	
		});
		
		
		
		
		//-
		// Build Request Parameters			
		/*		
		// TODO
		// When sending the parameters like this, the endpoint should access it as metrics[]
		var data = {'metrics' : params.metrics};	
		
		jQuery.ajax({
			type: 'get',
			data: data,
			dataype: "json",
			url: isserver + '/rest/analysis/stats/' + data.deplId,
			success: onSuccess
		});
		*/
		
		var combinedJSON = {};
		var instancesCount = {} ; // Build holder for instances count data
		combinedJSON['instances'] = instancesCount;
		var cost = {} ; // Build holder for cost data
		combinedJSON['cost'] = cost;
		
		// Pass the authendication token to the IS Server // requests if exists.
		var reqParams = {};
		if (("token" in urlParams))
			reqParams['token'] =  urlParams.token;
		
		$.when(
				// Get data to display
				/* This is old code
				jQuery.ajax({
					type: 'get',
					dataype: "json",
					url: isserver + '/rest/analysis/stats/' + params.deplId + partUrl + "?" + qString,
					success: function(jsonResponse){
						if(jQuery.type(jsonResponse) === "string")
							jsonResponse = $.parseJSON(jsonResponse);						
						combinedJSON['analysis'] = jsonResponse;
					}
				}),
				*/
				
				
				// Get Application Servers (tier) Instance Count (over the time) data
				
				ajaxRequest(
					isserver + '/rest/deployment/' + params.deplId + '/tier/' + 'appServer' + '/instances',
    		        'get',
    		        reqParams,
    		        'json',
    		        function(jsonResponse){
						if(jQuery.type(jsonResponse) === "string")
							jsonResponse = $.parseJSON(jsonResponse);
						combinedJSON.instances['appServer'] = jsonResponse;
					},
    				function(jsonObj) {
    					// error
    					
    				},
    		        null
		        ),
				/*
				jQuery.ajax({
					type: 'get',
					dataype: "json",
					url: isserver + '/rest/deployment/' + params.deplId + '/tier/' + 'appServer' + '/instances',
					success: function(jsonResponse){
						if(jQuery.type(jsonResponse) === "string")
							jsonResponse = $.parseJSON(jsonResponse);
						combinedJSON.instances['appServer'] = jsonResponse;
					}
				}),
				*/
		        	        
		        
				// Get Database Servers (tier) Instance Count (over the time) data
		        
		        ajaxRequest(
		        	isserver + '/rest/deployment/' + params.deplId + '/tier/' + 'database' + '/instances',
    		        'get',
    		        reqParams,
    		        'json',
    		        function(jsonResponse){
						if(jQuery.type(jsonResponse) === "string")
							jsonResponse = $.parseJSON(jsonResponse);
						combinedJSON.instances['database'] = jsonResponse;
					},
    				function(jsonObj) {
    					// error
    					
    				},
    		        null
		        ),
		        /*
				jQuery.ajax({
					type: 'get',
					dataype: "json",
					url: isserver + '/rest/deployment/' + params.deplId + '/tier/' + 'database' + '/instances',
					success: function(jsonResponse){
						if(jQuery.type(jsonResponse) === "string")
							jsonResponse = $.parseJSON(jsonResponse);
						combinedJSON.instances['database'] = jsonResponse;
					}
				}),
				*/
				
				// Get Application Servers (tier) Cost (over the time) data
		        
		        ajaxRequest(
		        	isserver + '/rest/deployment/' + params.deplId + '/tier/' + 'appServer' + '/cost',
    		        'get',
    		        reqParams,
    		        'json',
    		        function(jsonResponse){
						if(jQuery.type(jsonResponse) === "string")
							jsonResponse = $.parseJSON(jsonResponse);
						combinedJSON.cost['appServer'] = jsonResponse;
					},
    				function(jsonObj) {
    					// error
    					
    				},
    		        null
		        ),
		        /*
				jQuery.ajax({
					type: 'get',
					dataype: "json",
					url: isserver + '/rest/deployment/' + params.deplId + '/tier/' + 'appServer' + '/cost',
					success: function(jsonResponse){
						if(jQuery.type(jsonResponse) === "string")
							jsonResponse = $.parseJSON(jsonResponse);
						combinedJSON.cost['appServer'] = jsonResponse;
					}
				}),
				*/
				
				// Get Database Servers (tier) Cost (over the time) data
		        
		        ajaxRequest(
		        	isserver + '/rest/deployment/' + params.deplId + '/tier/' + 'database' + '/cost',
    		        'get',
    		        reqParams,
    		        'json',
    		        function(jsonResponse){
						if(jQuery.type(jsonResponse) === "string")
							jsonResponse = $.parseJSON(jsonResponse);
						combinedJSON.cost['database'] = jsonResponse;
					},
    				function(jsonObj) {
    					// error
    					
    				},
    		        null
		        )
		        
		        /*
				jQuery.ajax({
					type: 'get',
					dataype: "json",
					url: isserver + '/rest/deployment/' + params.deplId + '/tier/' + 'database' + '/cost',
					success: function(jsonResponse){
						if(jQuery.type(jsonResponse) === "string")
							jsonResponse = $.parseJSON(jsonResponse);
						combinedJSON.cost['database'] = jsonResponse;
					}
				})
				*/
				
		).then(function() {
			console.log(combinedJSON);			
			onSuccess(combinedJSON);			
		});
		
		
		
		
		
		
	};
}
    