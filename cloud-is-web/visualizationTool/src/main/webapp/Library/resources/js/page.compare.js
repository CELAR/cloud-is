$(document).ready(function(){
	// Init tabber controls
	var myTabber = Object.create(ui.tabber);
	myTabber.init("innerNavMenu", false);
	
	// Check if comparison is requested (via the url)
	// add get the appropriate data
	
	// TODO
	
	
	
	// Init events for the comparison ribbon
	
	
	
	/*
	 * Version Selection, Pop Up
	 */	
	//open popup
	$('[role="button"][data-action="addNew"]').off('click');
	$('[role="button"][data-action="addNew"]').on('click', function(event){
		event.preventDefault();
		$('.cd-popup').addClass('is-visible');
		
		// Fill the pop-up
		$('.cd-popup .applicationList').html('');
		$('.cd-popup .versionList').html('');
		//
		jQuery.ajax({
			type : 'get',
			dataype : "json",
			url : isserver + '/rest/application/',
			success : function(jsonObj) {
				if (jQuery.type(jsonObj) === "string")
					jsonObj = eval(jsonObj);
				
				// Build UI control According the response
				var context = $('.applicationSelectionBar');
				var wellHolder = context.find('.well > .wellContentHolder');
				if(jsonObj !=null)
				{										
					$.each(jsonObj, function(key, app) {
						var wellItem = context.find('.well > .wellItemTemplate').clone();
											
						wellItem.find('span[data-name="name"]').html(app.description);
						wellItem.find('span[data-name="uid"]').html(app.uid);
						wellItem.find('span[data-name="version"]').html(app.vMajor + '.' + app.vMinor);
						wellItem.find('span[data-name="description"]').html(app.description);
						wellItem.find('span[data-name="appCombId"]').html(app.id);
						
						
						// Assign onClick Events
						wellItem.off('click');
						wellItem.on('click', function() {
							$(this).addClass('selected');					
						});	
						
						wellItem.removeClass('noDisplay'); 
						wellHolder.append(wellItem);
					});
				}
				else
				{
					wellHolder.append('<span>Nothing to show</span>');
				}
			}
		});
	});
	
	//close popup
	$('.cd-popup').on('click', function(event){
		if( $(event.target).is('.cd-popup-close') || $(event.target).is('.cd-popup') || $(event.target).is('[data-action="cancel"]') ) {
			event.preventDefault();
			$(this).removeClass('is-visible');
		}
		if( $(event.target).is('[data-action="aprove"]') ) {
			event.preventDefault();
			disposePopUp(this);
		}
	});
	//close popup when clicking the esc keyboard button
	$(document).keyup(function(event){
    	if(event.which=='27'){
    		$(this).removeClass('is-visible');
	    }
    });
	
	/*
	 * Disposes the application version popup
	 * and 'copies' the selected item to comparisson ribbon
	 */
	var disposePopUp = function(popup){
		var context = $('.comparissonRibbon');
		var wellHolder = context.find('.well > .wellContentHolder');
		
		wellHolder.html('');
		$.each($(popup).find('.application.selected'), function() {
			var wellItem = context.find('.well > .wellItemTemplate').clone();
			
			wellItem.find('span[data-name="name"]').html($(this).find('span[data-name="name"]').html());
			wellItem.find('span[data-name="uid"]').html($(this).find('span[data-name="uid"]').html());
			wellItem.find('span[data-name="appCombId"]').html($(this).find('span[data-name="appCombId"]').html());
			wellItem.find('span[data-name="version"]').html($(this).find('span[data-name="version"]').html());			
			
			// Get additional info from the service
			// eg. Deployments (/{appId}/deployment)
			jQuery.ajax({
				type : 'get',
				dataype : "json",
				url : isserver + '/rest/application/' + $(this).find('span[data-name="appCombId"]').html() + '/deployment',
				success : function(jsonObj) {
					if (jQuery.type(jsonObj) === "string")
						jsonObj = eval(jsonObj);
					
					if(jsonObj !=null && jsonObj !="")
						wellItem.find('span[data-name="deployment"]').html(jsonObj[0].id);
					else
						wellItem.find('span[data-name="deployment"]').html("No Eligible Deployemen Found");
				}
			});
						
			// Assign onClick Events
			wellItem.off('click');
			wellItem.on('click', function() {
				$(this).toggleClass('selected');					
			});	
			
			wellItem.removeClass('noDisplay'); 
			wellHolder.append(wellItem);
		});
		
		$(popup).removeClass('is-visible');
	}
	
	// Compare Button
	$('[role="button"][data-action="comparePrep"]').off('click');
	$('[role="button"][data-action="comparePrep"]').on('click', function(event){
		var context = $('.componentSelectionBar');
		var wellHolder = context.find('.well > .wellContentHolder');
		// Clear Holder
		wellHolder.html('');
		$.each($('.comparissonItems > .comparableVersion'), function() {
			var wellItem = context.find('.well > .wellItemTemplate').clone();			
			
			// Create an accordion holder on the component sidebar
			deplId = $(this).find('span[data-name="deployment"]').html();
			version = $(this).find('span[data-name="version"]').html();
				
			// Add Meta info
			wellItem.data("deplRef", deplId);
			
			// Set Header - Version Name / id
			wellItem.find('.versionItemHead span[data-name="version"]').html(version);
			
			// Assign onClick Events - Toggle accordion / component list
			wellItem.find('.versionItemHead').off('click');
			wellItem.find('.versionItemHead').on('click', function() {
				$(this).closest('.versionComponent').find('.versionItemMain').toggleClass('noDisplay')
			});
			wellItem.removeClass('noDisplay');
			wellHolder.append(wellItem);
			
			// Build / prepare the single component item
			var componentTemplate = $('<div><span/></div>');
			componentTemplate.addClass('singleComponent');
			
			// Main content holder / Components list
			var holder = wellItem.find('.versionItemMain');
			
			// Get the topology (components) of the
			// version => deployment and fill the side bar
			jQuery.ajax({
				type : 'get',
				dataype : "json",
				url : isserver + '/rest/deployment/' + deplId + '/topology',
				success : function(jsonObj) {
					if (jQuery.type(jsonObj) === "string")
						jsonObj = eval(jsonObj);
					
					// Build UI control According the response
					

					if(jsonObj !=null && jsonObj !="")
					{	
						$.each(jsonObj, function(key, module) {
							$.each(module.components, function(key, component) {			
								var item = componentTemplate.clone();
								
								item.find('span').html(component.description);
								item.attr("data-id", component.id);
								item.attr("data-component", component.id);
								item.attr("data-module", module.id);
								
								// Assign onClick Events
								item.off('click');
								item.on('click', function() {
									$(this).toggleClass('selected');
									
									if($('.componentSelectionBar .singleComponent.selected').length > 0) {
										$('[role="button"][data-action="commonMetrics"]').removeClass('disabled');
									}
									else {
										$('[role="button"][data-action="commonMetrics"]').addClass('disabled');
									}
								});							
								holder.append(item);
							});
						});
					}
					else
					{
						wellHolder.append('<span>Nothing to show</span>');
					}
				}
			});
			
			// if no error, show content
			$('#chartBuilder .noContentNotice').addClass('noDisplay');
			$('#chartBuilder .comparissonContent').removeClass('noDisplay');
		});
		
		
		
	});
	
	$('[role="button"][data-action="commonMetrics"]').off('click');
	$('[role="button"][data-action="commonMetrics"]').on('click', function(event){
		dataObject = {};
		//dataObject["data"] = {};
		$.each($('.versionComponent'), function() {
			if($(this).find('.singleComponent.selected').length > 0)
			{
				dId = $(this).data("deplRef");
				dataObject[dId] = {};
				$.each($(this).find('.singleComponent.selected'), function() {
					dataObject[dId][$(this).attr("data-id")] = $(this).attr("data-component");
				});
			}			
		});
		
		// Get additional info from the service
		// eg. Deployments (/{appId}/deployment)
		
		jQuery.ajax({
			type : 'get',
			dataype : "json",
			url : isserver + '/rest/compare/commonMetrics?data=' + JSON.stringify(dataObject),
			success : function(jsonObj) {
				if (jQuery.type(jsonObj) === "string")
					jsonObj = eval(jsonObj);
				
				var context = $('.metricsSelectionBar');
				var wellHolder = context.find('.well > .wellContentHolder');
				// Clear Holder
				wellHolder.html('');
				$.each(jsonObj, function() {
					var wellItem = context.find('.well > .wellItemTemplate').clone();			
					
					wellItem.html(this);
					
					// Assign onClick Events
					wellItem.off('click');
					wellItem.on('click', function() {
						$(this).toggleClass('selected');
					});
					
					
					wellItem.removeClass('noDisplay');
					wellHolder.append(wellItem);
				});
			}
		});
		
	});	
	
	$('[role="button"][data-action="compare"]').off('click');
	$('[role="button"][data-action="compare"]').on('click', function(event){
		var qString = '';
		var combinedJSON = {};
		$.each($('.metricsSelectionBar .selected'), function() {
			//Build Metrics Array / metrics requests string
			metricId = $(this).html();
			qString	+= '&metrics=' + metricId;
			// Init combine jsaon object
			combinedJSON[metricId] = {};
		});
		
		
		function analyticsRequests(){
		    var requests = [];
        	$.each($('.versionComponent'), function() {
				if($(this).find('.singleComponent.selected').length > 0)
				{
					var deplId = $(this).data("deplRef");
					$.each($(this).find('.singleComponent.selected'), function() {
						var compId = $(this).attr("data-id");
						// Get Application Servers (tier) Instance Count (over the time) data
						 var promise = $.ajax({
							type: 'get',
							dataype: "json",
							url: isserver + '/rest/analysis/' + deplId + '/tier/' + compId + '?' + qString,
							success: function(jsonResponse){
								if(jQuery.type(jsonResponse) === "string")
									jsonResponse = $.parseJSON(jsonResponse);
								
								$.each(jsonResponse, function(id, data) {
									combinedJSON[id][deplId + '.' + compId] = data;
									
								});										
							}
						});
						requests.push(promise); 
					});
				}
		        
		    });
		    console.log(requests);
		    // return a promise that will resolve when all ajax calls are done
		    return $.when.apply($, requests);
		}

		analyticsRequests().done(function() {
			console.log(combinedJSON);			

			var instancesDataTable;
			var linesCounter = 0;
			
			// Iterate Through Different Metrics
			$.each(combinedJSON, function(key, metricData) {
				var metricName = key;
				console.log(metricData);				
				// Iterating through differnt deployment - tier compinations
				$.each(metricData, function(id, obj) {
					var metricDataTable = new google.visualization.DataTable();
					metricDataTable.addColumn('datetime', 'Time');
					metricDataTable.addColumn('number', metricName + "(" + id + ")");
					$.each(obj.data, function(index, metricObj) {
						var date = new Date(parseInt(metricObj.t));
						metricDataTable.addRow([date, parseInt(metricObj.v)]);
					});
					if(linesCounter == 0)
					{
						instancesDataTable = metricDataTable;
					}
					else
					{
						// Find Columns to join
						var cols=[];
						for(k = 0; k < linesCounter; k++)
						{
							cols[k] = k + 1;
						}
						instancesDataTable = google.visualization.data.join(instancesDataTable, metricDataTable, 'inner', [[0,0]], cols, [1]);
					}
					linesCounter++;
				});
			}); 
			
			//-
			// For debugging print the data table
			//var visualization = new google.visualization.Table($('.chartContentHolder')[0]);
			//visualization.draw(instancesDataTable, {
			//    sort: "disable",
			//    allowHtml: true,
			//});
			
			// Build Chart Options
			var options = {
			   curveType: 'function',
		       //height: height - 10, /* Temporary hack, cause: the chart overlaps the outer container and hides its border */
		       allowHtml: 'true',
		       interpolateNulls: true,
		       hAxis: {
		           title: 'Time'
	         },
			};
			
			// Draw Chart
			var googleChart = new google.visualization.LineChart($('.chartContentHolder')[0]);
			googleChart.draw(instancesDataTable, options);
			
		}).fail(function() {
		   console.log('Error / Fail');
		});
		
		
		
		
		
		
		
		
		
		/*
		// Get Deployments and Component Inf
		// TODO : this code is similar of the one in 
		// $('[role="button"][data-action="commonMetrics"]').on('click') event		
		$.when(
			$.each($('.versionComponent'), function() {
				if($(this).find('.singleComponent.selected').length > 0)
				{
					var deplId = $(this).data("deplRef");
					$.each($(this).find('.singleComponent.selected'), function() {
						var compId = $(this).attr("data-id");
						// Get Application Servers (tier) Instance Count (over the time) data
						jQuery.ajax({
							type: 'get',
							dataype: "json",
							url: isserver + '/rest/analysis/' + deplId + '/tier/' + compId + '?' + qString,
							success: function(jsonResponse){
								if(jQuery.type(jsonResponse) === "string")
									jsonResponse = $.parseJSON(jsonResponse);
								
								$.each(jsonResponse, function(id, data) {
									combinedJSON[id][deplId + '.' + compId] = data;
									
								});
								
							}
						})						
					});
				}			
			})		
		).then(function() {			
			console.log(combinedJSON);			

			var instancesDataTable;
			var linesCounter = 0;
			
			// Iterate Through Different Metrics
			$.each(combinedJSON, function(key, metricData) {
				var metricName = key;
				console.log(metricData);				
				// Iterating through differnt deployment - tier compinations
				$.each(metricData, function(id, obj) {
					var metricDataTable = new google.visualization.DataTable();
					metricDataTable.addColumn('datetime', 'Time');
					metricDataTable.addColumn('number', id + '.' + metricName);
					$.each(obj.data, function(index, metricObj) {
						var date = new Date(parseInt(metricObj.t) * 1000);
						metricDataTable.addRow([date, parseInt(metricObj.v)]);
					});
					if(linesCounter == 0)
					{
						instancesDataTable = metricDataTable;
					}
					else
					{
						// Find Columns to join
						var cols=[];
						for(k = 0; k < linesCounter; k++)
						{
							cols[k] = k + 1;
						}
						instancesDataTable = google.visualization.data.join(instancesDataTable, metricDataTable, 'inner', [[0,0]], cols, [1]);
					}
					linesCounter++;
				});
			}); 
			
			//-
			// For debugging print the data table
			var visualization = new google.visualization.Table($('.chartContentHolder')[0]);
			visualization.draw(instancesDataTable, {
			    sort: "disable",
			    allowHtml: true,
			});
			
		});
		*/
		
		
	});	
});








