var drawVisualization = function (repId, dtCollector, dtVisualize, ajxParams, cache) {
	// Define the ajax data collection function
	var ajaxCollector = function(params) {
		dtCollector(params, function(ajxResponse) {			
			if (cache)
			{	
				// Cache data
				console.log("Caching Data: " + repId);
			    sessionStorage.setItem(repId, JSON.stringify(ajxResponse));			
			}
			dtVisualize(ajxResponse);
		});
	}
	
	//-	
	if (cache)
	{
		// Check browser support for caching
		// and define cashing method
		if(typeof(Storage) !== "undefined") {
		    // Support localStorage/sessionStorage.
		} else {
		    // Sorry! No Web Storage support..
			cache = false;
		}
	}
	// TODO : Check the code below and the one above, maybe can merge.
	// Check if cashing is enabled
	if (cache)
	{
		if (sessionStorage.getItem(repId)) {
			console.log("Loading from Cache: " + repId);
			dtVisualize(sessionStorage.getItem(repId));
		} else {
			ajaxCollector(ajxParams);
		}
	}
	else
	{
		ajaxCollector(ajxParams);
	}
	
	
}

/* obsolete staff */
var buildPerformanceAnalysisPane = function() {
	// Default options
	var optionDefault = {
	        title: '',
	        chartArea: {
	        	left: 10,
	            top: 20,
	            width:'100%'
	        },
	        colors: ['#468ba9', "#67696c", "#6cc96c"],
	        width:400,
            height:400,
            legend:"none"
	    };
	
	// set custom legend
	var legend = $('.pieChartSeriesLegend');
	
	var lItem = $('.pieChartSeriesLegend > .legendItem');
	lItem.find('.color').css("background-color", "#468ba9");
	lItem.find('.title').html('> 80%');
	
	var tmpItem = lItem.clone();
	tmpItem.find('.color').css("background-color", "#67696c");
	tmpItem.find('.title').html('20% - 80%');
	legend.append(tmpItem);
	
	var tmpItem = lItem.clone();
	tmpItem.find('.color').css("background-color", "#6cc96c");
	tmpItem.find('.title').html('< 20%');
	legend.append(tmpItem);
	
	// Create and populate the data table.
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Load Percentage');
    data.addColumn('number', 'Time');    
    
    var cpuData = data.clone();
    cpuData.addRows([
                  ['> 80%', 3],
                  ['20% - 80%', 9],
                  ['< 20%', 2]
                ]);
    //
    var options = jQuery.extend({}, optionDefault, {title:"CPU"});
    new google.visualization.PieChart(document.getElementById('pc_cpu_app')).
    draw(cpuData, options);
    
    var ramData = data.clone();
    ramData.addRows([
                  ['> 80%', 1],
                  ['20% - 80%', 6],
                  ['< 20%', 5]
                ]);
    //
    var options = jQuery.extend({}, optionDefault, {title:"RAM"});
    new google.visualization.PieChart(document.getElementById('pc_ram_app')).
    draw(ramData, options);
    
    var diskData = data.clone();
    diskData.addRows([
                  ['> 80%', 8],
                  ['20% - 80%', 3],
                  ['< 20%', 2]
                ]);
    //
    var options = jQuery.extend({}, optionDefault, {title:"DISK I/O", });
    new google.visualization.PieChart(document.getElementById('pc_disk_app')).
    draw(diskData, options);
    
    var networkData = data.clone();
    networkData.addRows([
                  ['> 80%', 5],
                  ['20% - 80%', 5],
                  ['< 20%', 2]
                ]);
    //
    var options = jQuery.extend({}, optionDefault, {title:"NET I/O",});
    new google.visualization.PieChart(document.getElementById('pc_net_app')).
    draw(networkData, options);
};