/*
 * Build 
 */

function appComponent (reportID) {
	var formalizeData = function (opts) {
		var data = new google.visualization.DataTable();
		data.addColumn('datetime', 'x');
		data.addColumn({type: 'string', role: 'annotation'});
		data.addColumn({type: 'string', role: 'annotationText'});
		data.addColumn('number', capitaliseFirstLetter(opts.name));
		data.addColumn('number', capitaliseFirstLetter("average"));
		
		return data;
	};
		
	var inputData = function (dataObj, data, tStart, sRate) {
		$.each(data, function(index, metricObj){
			//var date = new Date(timestamp);
			var date = new Date(parseInt(metricObj.t));
			dataObj.addRow([date, null, null, parseFloat(metricObj.v), null]);
		});
		return dataObj;
	};
	
	var inputAdditionalData = function (dataObj, value, time) {
		var date = new Date(time);
		dataObj.addRow([date, null, null, null, parseFloat(value)]);
		return dataObj;
	};
	
	var inputActions = function (dataObj, data) {
		$.each(data, function(index, event){
			 var date = new Date(parseInt(event.t));
			 dataObj.addRow([date, event.v, 'Resizing Action', null, null]);
		});
		return dataObj;
	};
		
	var drawPerformanceLineChart = function (data, container) {
		 // Create and draw the visualization.
		var chart1_options = {
		   curveType: 'function',
	       height: 400,
	       allowHtml: 'true',
	       chartArea: {'height': '80%'},
	       interpolateNulls: true,
	       vAxis: {
	           maxValue: 10
	       },
	       annotations: {
	           style: 'line',
	           color: '#d799ae',
	       }
		};
	
		var chart1_chart = new google.visualization.LineChart(container);
		chart1_chart.draw(data, chart1_options);		
	};

	this.onDataReady = function(jsonObj, params){
		var Ostart = Date.now();		
		
		console.log(params);
		// Formulate metrics array
		var metrics = {};
		$.each(params.metrics, function (index, mObj){
			metrics[mObj.name] = mObj.id;
		});
		//console.log(params.metrics);
		//console.log(metrics);
			   
		// Find the report
		report = $('[data-reportid="' + reportID + '"]');		
		
		// Create and populate the data table.	
		if(jQuery.type(jsonObj) === "string")
			jsonObj = $.parseJSON(jsonObj);
		
		 var actions = jsonObj.actions;
		 delete jsonObj['actions'];
		 var version = jsonObj.version;
		 delete jsonObj['version'];		 
		 $.each(jsonObj, function (mName, objData){
			 var start = Date.now();
			 
			//var containerID = title + "_visualization";
			//var outerContainerID = title + "_visualizationContainer";
			var opts = new Object;
			opts.name = mName;
			// Set values ont the chart container header
			report.find('[data-mid="' + metrics[mName] + '"]').find('graphTitle span.value').html(mName);			
			var graphInfoContainer = report.find('[data-mid="' + metrics[mName] + '"]').find('.graphInfo');			
			// min  objData.min;
			graphInfoContainer.find('div[data-context="min"] span.value').html(objData.min);
			// max objData.max;
			graphInfoContainer.find('div[data-context="max"] span.value').html(objData.max);
			// avg objData.avg;
			graphInfoContainer.find('div[data-context="avg"] span.value').html(objData.avg);
			 
			//data
			data = formalizeData(opts);
			/*
			* Temporary hack to display average value at the chart
			* We create an additional column to hold the average, we set the avg vale as first and last row at the dataTable
			* we make sure that interpolateNulls is set to true
			*/
			inputAdditionalData(data, objData.avg, version.tStart);
			data = inputData(data, objData.data, version.tStart, version.sRate);
			inputAdditionalData(data, objData.avg, version.tEnd);
			
			data = inputActions(data, actions);
			
			console.log("data " + (Date.now() - start) +  "milliseconds");
			
			var start = Date.now();
			
			//var container = report.find('[data-id="' + containerID + '"]')[0];
			var container = report.find('[data-mid="' + metrics[mName] + '"]').find('.chartHolder')[0];
			if(typeof container !== "undefined" )//|| container.length > 0) 
				drawPerformanceLineChart(data, container);
			else
				console.log('Chart Container ' + 'data-mid="' + metrics[mName] + '" does not exist');
			
			console.log("chart rentering " + (Date.now() - start) + " milliseconds");
		 });		
		 
		 console.log("Page load took " + (Date.now() - Ostart) + " milliseconds"); 
	};
	
	this.ajax_getAnalysisStats = function(params, onSuccess){
		// Build Request part url
		var partUrl = '';
		
		var compId = String(params.compId).trim();
		if(!(compId.length === 0 || !compId))
			partUrl += '/tier/' + compId;
		
		// Build Request Parameters		
		var qString = "";
		$.each(params.metrics, function (index, metric) {
			qString	+= '&metrics=' + metric.name;	
		});
		
		// Get data to display
		jQuery.ajax({
			type: 'get',
			dataype: "json",
			url: isserver + '/rest/analysis/' + params.deplId + partUrl + '/stats/' + "?" + qString,
			success: onSuccess
		});
	};
}



