/*
 * Build 
 */

function appComponent (reportID) {
	
	var buildDeploymentsPane = function()
	{
		$('#versDeployments .wellContentHolder .wellItem').on('click', function() {
			///application/version/compare
			var form = $("<form/>");
			form.attr('action', wcserver + '/application/version/deployment');
			form.attr('method', 'post');
			
			var versions = "";
			wellHolder.find('.wellItem').each(function(){
				if($(this).hasClass('toCompare'))
				{
					versions +=  $(this).find('input[name="versId"]').val() + ",";
				}
				versions.slice(0,-1);
			});
			
			$('<input/>').attr('type', 'hidden').attr('name', 'appID').val('something').appendTo(form);
			$('<input/>').attr('type', 'hidden').attr('name', 'versID').val(versions).appendTo(form);
		
			form.appendTo('body').submit();
		});
	};
	
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
		var timestamp = parseInt(tStart);
	$.each(data, function(index, metricObj){
		//var date = new Date(timestamp);
		var date = new Date(parseInt(metricObj.t));
		dataObj.addRow([date, null, null, parseFloat(metricObj.v), null]);
		timestamp += parseInt(sRate);
	});
	return dataObj;
	};
	
	var inputAdditionalData = function (dataObj, value, time) {
		var date = new Date(time);
	dataObj.addRow([date, null, null, null, parseFloat(value)]);
	return dataObj;
	};
	
	var inputActions = function (dataObj, data) {
		 $.each(data, function(name, value){
			 var date = new Date(value);
			 dataObj.addRow([date, name, 'Resizing Action', null, null]);
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
	           style: 'line'
	       }
		};
	
		var chart1_chart = new google.visualization.LineChart(container);
		chart1_chart.draw(data, chart1_options);
	};

	this.onDataReady = function(jsonObj){
		
		// example copied from Google Visualization API playground,
		// modified for category axis annotations
		   
		// Find the report
		report = $('[data-reportid="' + reportID + '"]')
		
		
		// Create and populate the data table.	
		if(jQuery.type(jsonObj) === "string")
			jsonObj = $.parseJSON(jsonObj);
		
		 var actions = jsonObj.actions;
		 delete jsonObj['actions'];
		 var version = jsonObj.version;
		 delete jsonObj['version'];
		 $.each(jsonObj, function (title, objData){
			var containerID = title + "_visualization";
			var outerContainerID = title + "_visualizationContainer";
			var opts = new Object;
			opts.name = title;
			// Set values ont the chart container header
			report.find('[data-id="' + outerContainerID + '"]').find('graphTitle span.value').html(title);			
			var graphInfoContainer = report.find('[data-id="' + outerContainerID + '"]').find('.graphInfo');			
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
			var container = report.find('[data-id="' + containerID + '"]')[0];
			if(typeof container !== "undefined" )//|| container.length > 0)
				drawPerformanceLineChart(data, container);
			else
				console.log("Chart Container " +containerID+ " does not exist");
		 });		 
	};
	
	this.ajax_getAnalysisStats = function(params, onSuccess){
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
		
		// Get data to display
		jQuery.ajax({
			type: 'get',
			dataype: "json",
			url: isserver + '/rest/analysis/stats/' + params.deplId + partUrl + "?" + qString,
			success: onSuccess
		});
	};
}



