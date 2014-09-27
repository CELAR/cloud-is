function appOverview () { 	
 	var drawAreaChart = function (data, containerID) {
 		 // Some raw data (not necessarily accurate)
 		  var data = google.visualization.arrayToDataTable([
 		    ['Time',   'Reserved', 'On Demend'],
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

 		  // Create and draw the visualization.
 		  var ac = new google.visualization.AreaChart(document.getElementById(containerID));
 		  ac.draw(data, {
 		    title : 'Total Active Instances per Time',
 		    isStacked: true,
 		    height: 400,
 		    vAxis: {title: "Number of Instances"},
 		    hAxis: {title: "Tiime"}
 		  });
 	};
 	
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
	
	var costToInstanceCount = function (data, containerID) {
		  // Some raw data (not necessarily accurate)
		  var data = google.visualization.arrayToDataTable([
		    ['Hour', 'App Server', 'Database', 'App Server Cost', 'Database Cost'],
		    ['07:00:00',   2, 1, 10, 7],
		    ['08:00:00',   4, 2, 20, 14],
		    ['09:00:00',   5, 4, 25, 28],
		    ['10:00:00',   8, 6, 40, 42],
		    ['11:00:00',   10, 7, 50, 49],
		    ['12:00:00',   7, 6, 35, 42],
		    ['13:00:00',   5, 4, 25, 28],
		    ['14:00:00',   6, 7, 35, 49],
		    ['15:00:00',   9, 8, 45, 56]
		  ]);

		  var options = {
			vAxes: {0: {title: "Instances Count"},
			        1: {title: "Cost ($)"}},
		    hAxis: {title: "Time"},
		    height: 300,
		    seriesType: "bars",
		    series: {2: {type: "line", targetAxisIndex:1}, 
		    		 3: {type: "line", targetAxisIndex:1}}
		  };

		  var chart = new google.visualization.ComboChart(document.getElementById(containerID));
		  chart.draw(data, options);
		}

	
    
this.onDataReady = function(jsonObj){
	
	// example copied from Google Visualization API playground,
	// modified for category axis annotations
		   
	if(jQuery.type(jsonObj) === "string")
		jsonObj = $.parseJSON(jsonObj);
	
	// Create and populate the data table.
	
	//drawLineChart(data, containerID);
	//drawColChart(null, "chartHolder_Cols");
	drawAreaChart(null, "chartHolder_area");
	//buildPerformanceAnalysisPane();
	costToInstanceCount(null, "chartHolder_costToInstanceCount");
};
    
this.getAjaxStuff = function(onSuccess){
    	jQuery.ajax({
    		type: 'get',
    		dataype: "json",
    		url: isserver + '/rest/analysis/stats/0/0/0',
    		success: onSuccess
    	});
    };
}
    