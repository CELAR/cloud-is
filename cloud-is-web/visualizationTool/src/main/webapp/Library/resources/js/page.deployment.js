// Get and store the url parameters
var urlParams;
(window.onpopstate = function() {
	var match, pl = /\+/g, // Regex for replacing addition symbol with a space
	search = /([^&=]+)=?([^&]*)/g, decode = function(s) {
		return decodeURIComponent(s.replace(pl, " "));
	}, query = window.location.search.substring(1);

	urlParams = {};
	while (match = search.exec(query))
		urlParams[decode(match[1])] = decode(match[2]);
})();

$(window).on('resize', function() {
	/*
	 * var md = $('body > .page'); var h = parseInt(md.height()); // Set the
	 * left content new height var top = 0; var tp = md.find('.headBar'); // var
	 * top = parseInt(tp.height()); var top = tp.outerHeight(); //.outerHeight()
	 * to get also the padding offset
	 * 
	 * //var topbar = parseInt(md.find('.right > .topbar').height()); nh = h -
	 * top; $('.pagesContainer').height(nh.toString());
	 */
});

// Initialize page
$(document).ready(function() {
	// Init tabber controls
	var myTabber = Object.create(ui.tabber);
	myTabber.init("innerNavMenu", false);

	var tab;
	if (!("tab" in urlParams)) {
		// getDeplInfo

		tab = 'analysis';
	} else
		tab = urlParams.tab;

	if (tab == 'analysis') {
		// Hacky approach
		// TODO
		$('[data-tabber-ref="generReport"] > span').trigger('click');

		initScripts.initAnalysis();
		initScripts.initTopologyExplorer();		
	} else if (tab == 'monitoring') {
		// Hacky approach
		// TODO
		$('[data-tabber-ref="liveMonitorData"] > span').trigger('click');

		initScripts.initMonitoring();
	}
});

var initScripts = {
	'initAnalysis' : function() {
		if (typeof (Storage) !== "undefined") {
			console.log('Web Storage Supported');
			// Code for localStorage/sessionStorage.

			// Define Clear Cache Functionality
			window.onbeforeunload = function(e) {
				// Clear the whole cashe on windows reload
				// TODO
				// The best is to clear only the chart data
				console.log("Clearing Cache / Charts Data");
				sessionStorage.clear();
			};

		} else {
			console.log('Web Storage NOT Supported');
			// Sorry! No Web Storage support..
		}

		var deplId = 0;
		if (("deplID" in urlParams))
			deplId = urlParams.deplID;
		
		// Pass the authendication token to the IS Server
		// requests if exists.
		var reqParams = {};
		if (("token" in urlParams))
			reqParams['token'] =  urlParams.token;

		console.log('initAnalysis');
		// Load App Structure Tree Navigator
		
		ajaxRequest(
				isserver + '/rest/deployment/' + deplId + '/topology',
		        'get',
		        reqParams,
		        'json',
		        function(jsonObj) {
					if (jQuery.type(jsonObj) === "string")
						jsonObj = eval(jsonObj);
					
					// Build UI control According the response
					var context = $('.appComponentList');
					var wellHolder = context.find('.well > .wellContentHolder');

					if(jsonObj !=null && jsonObj !="")
					{	
						$.each(jsonObj, function(key, module) {
							$.each(module.components, function(key, component) {
								var wellItem = context.find('.well > .wellItemTemplate').clone();
								var inner = wellItem.find('span');
								//console.log("This is node");
								//console.log(node);
								
								//inner.html(module.name + "->" + component.description);
								inner.html(component.description);
								inner.attr("data-id", component.id);
								inner.attr("data-component", component.id);
								inner.attr("data-module", module.id);
								
								// Assign onClick Events
								inner.off('click');
								inner.on('click', onClick_component);	
								
								wellHolder.append(wellItem);
							});
						});
					}
					else
					{
						wellHolder.append('<span>Nothing to show</span>');
					}
					$('.appComponentList').removeClass('noDisplay');
					
					// Add click event to overview pane / report
					context.find('span[data-id="overview"]').off('click');
					context.find('span[data-id="overview"]').on('click', onClick_component);
					
					// Load Charts API
					chartsApiWating();
					initScripts.initChartsAPI(chartsApiLoaded);
				},
				function(jsonObj) {
					// TODO: Try to remove duplicate code
					// Build UI control According the response
					var context = $('.appComponentList');
					var wellHolder = context.find('.well > .wellContentHolder');
					
					wellHolder.append('<span>Nothing to show</span>');
					$('.appComponentList').removeClass('noDisplay');
				},
		        null);
		
		
		/*
		jQuery.ajax({
			type : 'get',
			dataype : "json",
			url : isserver + '/rest/deployment/' + deplId + '/topology',
			success : function(jsonObj) {
				if (jQuery.type(jsonObj) === "string")
					jsonObj = eval(jsonObj);
				
				// Build UI control According the response
				var context = $('.appComponentList');
				var wellHolder = context.find('.well > .wellContentHolder');

				if(jsonObj !=null && jsonObj !="")
				{	
					$.each(jsonObj, function(key, module) {
						$.each(module.components, function(key, component) {
							var wellItem = context.find('.well > .wellItemTemplate').clone();
							var inner = wellItem.find('span');
							//console.log("This is node");
							//console.log(node);
							
							//inner.html(module.name + "->" + component.description);
							inner.html(component.description);
							inner.attr("data-id", component.id);
							inner.attr("data-component", component.id);
							inner.attr("data-module", module.id);
							
							// Assign onClick Events
							inner.off('click');
							inner.on('click', onClick_component);	
							
							wellHolder.append(wellItem);
						});
					});
				}
				else
				{
					wellHolder.append('<span>Nothing to show</span>');
				}
				$('.appComponentList').removeClass('noDisplay');
				
				// Add click event to overview pane / report
				context.find('span[data-id="overview"]').off('click');
				context.find('span[data-id="overview"]').on('click', onClick_component);
				
				// Load Charts API
				chartsApiWating();
				initScripts.initChartsAPI(chartsApiLoaded);
			}
		});
		*/
	},
	'initMonitoring' : function() {
		console.log('initMonitoring');
		// Init event to connect to the monitoring Server
		$('.connectionToolbar .notConnected > .connectBtn').off('click');
		$('.connectionToolbar .notConnected > .connectBtn').on('click',
				connectToMonitoring);
		$('.connectionToolbar .connected > .disconnectBtn').off('click');
		$('.connectionToolbar .connected > .disconnectBtn').on('click',
				disconnectFromMonitoring);
	},
	'initChartsAPI' : function(callback) {
		google.load('visualization', '1', {
			packages : [ 'corechart', 'table' ],
			callback : callback
		});
	},
	'initTopologyExplorer' : function() {
		console.log('initTopologyExplorer');
		
		var deplId = 0;
		if (("deplID" in urlParams))
			deplId = urlParams.deplID;
		
		// Pass the authendication token to the IS Server
		// requests if exists.
		var reqParams = {};
		if (("token" in urlParams))
			reqParams['token'] =  urlParams.token;
		
		//initTime Control
		ajaxRequest(
			isserver + '/rest/deployment/' + deplId,
	        'get',
	        reqParams,
	        'json',
	        function(jsonObj) {
				if (jQuery.type(jsonObj) === "string")
					jsonObj = eval(jsonObj);
				
				// Build UI control According the response	
				if(jsonObj !=null)
				{					
					$("#slider").dateRangeSlider({
						/*
				        formatter:function(val){
				            var days = val.getDate(),
				                month = val.getMonth() + 1,
				                year = val.getFullYear(),
				                hours = val.getHours(),
				                min = val.getMinutes(),
				                sec = val.getSeconds();

				            return days + "/" + month + "/" + year + "-" + hours + ":"+min+":"+sec;
				        },
				        */												
				        valueLabels: "hide",
				        bounds:{
				            //min: new Date(Math.floor("1327743132000")),
				            //max: new Date(2012, 11, 31)
				        	min: new Date(Math.floor(jsonObj.sTime)),
				            max: new Date(Math.floor(jsonObj.eTime))
				        },
				        
				        defaultValues:{
				        	//min: new Date(Math.floor("1327743132000")),
				            //max: new Date(2012, 11, 31)
				        	min: new Date(Math.floor(jsonObj.sTime)),
				            max: new Date(Math.floor(jsonObj.eTime))
				          }
				    });
				   
					
				    // Set time range message
				    var values = $("#slider").dateRangeSlider("values");
				    $('.timeRange').html("<span>Accessing Deployment Data <strong>from</strong> <i>" + values.min + "</i>  <strong>to</strong> <i>" + values.max + "</i></span>");
				    // Set event to change time range message
				    $("#slider").off("valuesChanging valuesChanged");
				    $("#slider").on("valuesChanging valuesChanged", function(e, data) {
				    	  $('.timeRange').html("<span>Accessing Deployment Data <strong>from</strong> <i>" + data.values.min + "</i>  <strong>to</strong> <i>" + data.values.max + "</i></span>");
				    });
				    
				    // Resize windows, for the slider to take the correct dimensions
				    $(document).on('ui.tabber.change', function() {
				    	$("#slider").dateRangeSlider('resize');
				    });
				    
				}
			},
			function(jsonObj) {
				
			},
	        null
	    );
		
		
		/*
		jQuery.ajax({
			type : 'get',
			dataype : "json",
			url : isserver + '/rest/deployment/' + deplId,
			success : function(jsonObj) {
				
			}
		});	
		*/
		
		// Makes use of jspumb library
		jsPlumb.ready(function() {
			// your jsPlumb related init code goes here  
			
			// Pass the authendication token to the IS Server 
			// requests if exists.
			var reqParams = {};
			if (("token" in urlParams))
				reqParams['token'] =  urlParams.token;
			
			
			// Load App Structure
			
			ajaxRequest(
				isserver + '/rest/deployment/' + deplId + '/topology',
		        'get',
		        reqParams,
		        'json',
		        function(jsonObj) {
					// Success
					if (jQuery.type(jsonObj) === "string")
						jsonObj = eval(jsonObj);
					
					// Build UI control According the response					
					var context = $('.topologyCanvas');					 
					jsPlumb.setContainer(context);
									
					if(jsonObj !=null && jsonObj !="")
					{	
						//var nodesNum = jsonObj.length;
						//var nodesNum = Object.keys(jsonObj).length;
						
						var nodesNum = 0;
						$.each(jsonObj, function(key, module) {
							//nodesNum += Object.keys(module.components).length; // TODO: WARN: This does not work in IE 7 or 8
							nodesNum += module.components.length;
						});
												
						/*
						 * Minor Hack to calculate the size of a hidden (display:none) item
						 * Clone the item, apply a negative left position value (so as not be visible to the user)
						 * append the temp item to body and calculate size
						 * remove temp item
						 */
						var temp = context.clone();
						temp.css({ left: "-10000px", display: "block" });
						temp.appendTo('body');
						var viewPortSize_X = temp.width();
						var viewPortSize_Y = temp.height(); // Height for inner component will be always 0, to correctly calculated we need to find the height of the outer wrapper
						temp.remove();
						// Use the same method to find node element dimensions;
						var node_X = $('.templatePool > .component').width();
						var node_Y = $('.templatePool > .component').height();	
						//
						
						var x = 0;
						var y = 0;
						var index = 0;
						
						var offset = Math.abs((viewPortSize_X - (nodesNum * node_X)) / (nodesNum + 1));	
						$.each(jsonObj, function(key, module) {
							$.each(module.components, function(key, node) {
								index++;
								
								// Calculating box cordinates
								var outerBox_Y = viewPortSize_Y; // Considering a flat application, topology is a row no multiple components on one layer							
								x = (index * offset) + ((index - 1) * node_X);
								y = Math.abs(((outerBox_Y / 2) + (node_Y / 2)));							
										
								// Fill box data
								var component = $('.topologyCanvasWrapper > .templatePool').find('.component[data-type="template"]').clone();
								component.attr('data-type', '');
							    
								component.find('.title span[data-name="name"]').html(node.description);
								connect = component.find('.connect');
							    
								// Fill box metadata info
								component.find('.metadata').data('name', node.description);
								component.find('.metadata').data('id', node.id);
								
							    component.css({
							      'top': y,
							      'left': x
							    });
							   						   						    
							    context.append(component);
							    
							    jsPlumb.makeTarget(component, {
							      anchor: 'Continuous'
							    });
							    
							    jsPlumb.makeSource(connect, {
							      parent: component,
							      anchor: 'Continuous'
							    });
								
								// Click Events
							    /*
							     * On Node graphical Component Click
							     * Open a side pane for the user to choose metrics 
							     * for analytics  
							     */
							    component.off('click');
							    component.on('click', function(e) {
							    	e.stopPropagation();
							    	// Extract parameters from click component
							    	var deplId = urlParams.deplID;
							    	var referer = e.target;
							    	var compId = $(referer).find('.metadata').data('id');
							    	
							    	// Set the Component title
							    	$('.nodeInfoSection span[data-name="name"]').html($(referer).find('.title span[data-name="name"]').html());
							    	
							    	
							    	/*
							    	 * Ajax call to get the available metrics
							    	 * and populate the list
							    	 */
							    	// Build Request part url
									var partUrl = '';								
									
									if(!(compId.length === 0 || !compId))
										partUrl += '/tier/' + compId;
												
									// Get already assigned metrics
									assignedMetrics = $(referer).find('.assignedMetrics .assignedMetricsHolder').children();
									
									// Clear list boxes
									$('#metricsList').html('');
									$('#selectedMetricsList').html('');
									
									// Pass the authendication token to the IS Server
									// requests if exists.
									var reqParams = {};
									if (("token" in urlParams))
										reqParams['token'] =  urlParams.token;
									
									// Get data to display								
									jQuery.ajax({
										type: 'get',
										data: reqParams,
										dataype: "json",
										url: isserver + '/rest/deployment/' + deplId + partUrl + '/metrics',
										success: function(jsonObj) {										
											if (jQuery.type(jsonObj) === "string")
												jsonObj = eval(jsonObj);
											
											$.each(jsonObj, function(key, value) {
												var metricItem = $('<option></option>').attr('value', value).html(value);
												
												// Append Event
												metricItem.off('dblclick');
												metricItem.on('dblclick', function(event) {
													if($(this).closest('#metricsList').length > 0)
													{
														// Item resides on the left list
														$(this).appendTo("#selectedMetricsList");
													}
													else
													{
														// Item resides on the right list
														$(this).appendTo("#metricsList");
													}
													// Reset selected
													$("#selectedMetricsList").val([]);
													$("#metricsList").val([]);
												});
												
												// TODO
												// Appent to the right list
												if(value = assignedMetrics.value) {
													$('#selectedMetricsList').append(metricItem);
												}
												else {
													$('#metricsList').append(metricItem);
												}
												
											});
										},
									});
							    	
									
									//
									$('.metricsListWrapper [role="button"][data-action="moveToSelected"]').off('click');
									$('.metricsListWrapper [role="button"][data-action="moveToSelected"]').on('click', function(){
										$.each($("#metricsList > option"), function(){
											$(this).appendTo("#selectedMetricsList");
										});								
									});
									
									
									$('.metricsListWrapper [role="button"][data-action="moveToAvailable"]').off('click');
									$('.metricsListWrapper [role="button"][data-action="moveToAvailable"]').on('click', function(){
										$.each($("#selectedMetricsList > option"), function(){
											$(this).appendTo("#metricsList");
										});
									});
													    							    	
							    	
							    	
							    	// Add Events to the panel tool bar
									$('.nodeInfoPanel [role="button"][data-action="close"]').off('click');
									$('.nodeInfoPanel [role="button"][data-action="close"]').on('click', function(){
							    		$(".nodeInfoPanel").removeClass("open");						    		
							    	});
							    	
							    	$('.nodeInfoPanel [role="button"][data-action="saveClose"]').off('click');
							    	$('.nodeInfoPanel [role="button"][data-action="saveClose"]').on('click', function(){
							    		$('.nodeInfoPanel [role="button"][data-action="save"]').trigger('click');
							    		$('.nodeInfoPanel [role="button"][data-action="close"]').trigger('click');			    		
							    	});
							    	
							    	$('.nodeInfoPanel [role="button"][data-action="save"]').off('click');
							    	$('.nodeInfoPanel [role="button"][data-action="save"]').on('click', function(){
							    		// Clear assigned metric
							    		var context = $(referer).find('.assignedMetrics');
							    		context.find('.assignedMetricsHolder').html('');
							    		
							    		//console.log($("#selectedMetricsList > option").length);
							    		
							    		$.each($("#selectedMetricsList > option"), function() {
							    			console.log($(this).val());
							    			
							    			var item = context.find('.wellItemTemplate').clone();
							    			
							    			item.find('span').html($(this).val());
							    			item.data('metricId', $(this).val()); // TODO: its set the name not the id, refine
							    			item.removeClass('wellItemTemplate');
											//$(referer).find('.assignedMetrics').append($(this).val());
							    			context.find('.assignedMetricsHolder').append(item);
										});					    		
							    	});
							    	
							    	/*
							    	 * Ajax to get and populate the resizing Desicions taken for
							    	 * this particular component
							    	 * 
							    	 */
							    	// Build Request part url
									var partUrl = '';
									if(!(compId.length === 0 || !compId))
										partUrl += '/tier/' + compId;
							    	
									// Pass the authendication token to the IS Server 
									// requests if exists.
									var reqParams = {};
									if (("token" in urlParams))
										reqParams['token'] =  urlParams.token;									
									
							    	// Clear list boxes
									$('.dicisionList').html('');
									
									// Get data to display								
									jQuery.ajax({
										type: 'get',
										data: reqParams,
										dataype: "json",
										url: isserver + '/rest/deployment/' + deplId + partUrl + '/decision/',
										success: function(jsonObj) {										
											if (jQuery.type(jsonObj) === "string")
												jsonObj = eval(jsonObj);
											
											$.each(jsonObj, function(key, decision) {												
												var decisionItem = $('<div></div>').attr('data-dtime', decision.timestamp)
																					.attr('data-dtype', decision.type)
																						.html(new Date(Math.floor(decision.timestamp)) + ' : ' + decision.type)
																						.addClass('decisionItem');
												
												// Append Event
												decisionItem.off('click');
												decisionItem.on('click', function(event) {
													console.log('decisionItem clicked');
													//
													var eventTime = parseInt($(this).attr('data-dtime'));
													var offeset = 1 * 60 *60 * 1000; // one hour in ms
													var lower = 0;
													var upper = 0;
														
													var max = new Date($("#slider").dateRangeSlider("bounds").max).getTime();													
													var min = new Date($("#slider").dateRangeSlider("bounds").min).getTime();
													
													console.log(offeset + " ## " + lower + " ## " + upper);
													
													var flag = true;
													while(flag)
													{
														console.log(min + " ## " + max);
														
														l = eventTime - offeset;
														u = eventTime + offeset;
														
														if(u < max && l > min)
														{
															lower = l;
															upper = u;
															flag = false;
														}
														else
														{
															console.log(offeset);
															offeset = offeset / 2;
															console.log(offeset);
														}
														
													}	
													console.log(lower + ":" + Math.floor(lower) + " ## " + upper  + ":" + Math.floor(upper) )
													$("#slider").dateRangeSlider("values", new Date(Math.floor(lower)), new Date(Math.floor(upper)));
													
												});					
												
												$('.dicisionList').append(decisionItem);
											});
										},
									});
							    	
							    	// Finally Open / Show the panel
							    	$(".nodeInfoPanel").addClass("open");
							    });
						    });
							
						});
					}
					else
					{
						context.append('<span>Nothing to show</span>');
					}
				},
				function(jsonObj) {
					// error
					
				},
		        null
	        );
			
			
			
			/*
			jQuery.ajax({
				type : 'get',
				dataype : "json",
				url : isserver + '/rest/deployment/' + deplId + '/topology',
				success : function(jsonObj) {
					
				}
			});
			*/
		});
		
		// Set 'Apply' Button enent handler
		$('.applyBtn [role="button"][data-action="analyze"]').off('click');
		$('.applyBtn [role="button"][data-action="analyze"]').on('click', function() {
			var values = $("#slider").dateRangeSlider("values");
	        console.log(new Date(values.min) + " " + new Date(values.max));
	       	       
	        $.each($('.topologyCanvas .component'), function() {
	        	var component = $(this);	        	
	        	var tier = component.find('.metadata').data('id');
	        	
	        	// Build Request Parameters		
	        	var qString = "";
	        	if(component.find('.assignedMetrics .assignedMetricsHolder .singleMetric').length > 0)
	        	{
		        	$.each(component.find('.assignedMetrics .assignedMetricsHolder .singleMetric'), function () {	    			  		
		        		qString	+= '&metrics=' + $(this).data('metricId');	
		    		});
		    		qString	+= '&method=' + 'trend';
		    		qString	+= '&sTime=' + new Date(values.min).getTime();
		    		qString	+= '&eTime=' + new Date(values.max).getTime();	    		
		    		
		    		// Pass the authendication token to the IS Server 
		    		// requests if exists.
		    		var reqParams = {};
		    		if (("token" in urlParams))
		    			reqParams['token'] =  urlParams.token;
		    		
		    		ajaxRequest(
	    				isserver + '/rest/deployment/' + deplId + '/topology',
	    		        'get',
	    		        reqParams,
	    		        'json',
	    		        function(jsonObj) {
	    					// Success
	    					if(jQuery.type(jsonResponse) === "string")
								jsonResponse = $.parseJSON(jsonResponse);
							
							// Draw a chart below the component
							var chart = $('<div></div>');
							var width = 300;
							var height = 180;
							
							pos = component.position();
							//
							chart.addClass('chart');							
							//chart.offset({ top: pos.top + component.height() - (component.height() / 4), left: pos.left - width + (component.width() / 4)});							
							chart.css({
						      'top': pos.top + component.height() - (component.height() / 4) + 50, /* TODO 50 is an add hoc value clculate from the additional height / positioning offset */
						      'left': pos.left - width + (component.width() / 2)
						    });
							
							
							var data;
							var merticsCounter = 0;
							$.each(jsonResponse, function(metricName, metricDataObj) {
								var singleMetric = new google.visualization.DataTable();
								singleMetric.addColumn('datetime', 'Time');
								singleMetric.addColumn('number', capitaliseFirstLetter(metricName));
								$.each(metricDataObj.data, function(index, metricObj) {
									var date = new Date(parseInt(metricObj.t) /* * 1000*/);
									singleMetric.addRow([date, parseFloat(metricObj.v)]);
								});
								if(merticsCounter == 0)
								{
									data = singleMetric;
								}
								else
								{
									// Find Columns to join
									var cols=[];
									for(k = 0; k < merticsCounter; k++)
									{
										cols[k] = k + 1;
									}
									data = google.visualization.data.join(data, singleMetric, 'full', [[0,0]], cols, [1]);
								}
								merticsCounter++;
							});
													
														
							// Build Chart Options
							var options = {
							   curveType: 'function',
						       height: height - 10, /* Temporary hack, cause: the chart overlaps the outer container and hides its border */
						       chartArea: {
						    	   'width': '80%',
						       },
						       allowHtml: 'true',
						       interpolateNulls: true,
						       legend : {
						    	   position: 'top', 
						    	   alignment :'center'
						       },
						       hAxis: {
						           title: 'Time'
					         },
							};
							
							// Append to the container
							//chart.addClass('noDisplay');
							$('.topologyCanvasWrapper').append(chart);
							
							// Draw Chart
							var googleChart = new google.visualization.LineChart(chart[0]);
							googleChart.draw(data, options);
							
							//chart.removeClass('noDisplay');	
	    				},
	    				function(jsonObj) {
	    					// error
	    					
	    				},
	    		        null
    		        );
		    		
		    		
		    		/*
					jQuery.ajax({
						type: 'get',
						dataype: "json",
						url: isserver + '/rest/analysis/' + urlParams.deplID + '/tier/' + tier + "?" + qString,
						success: function(jsonResponse) {
												
						}
					});
					*/
	        	}
	        });
		});
		
		
	},
	
	/*
	'initPastMonitoring' : function() {
		console.log('initPastMonitoring');
		// Init datetime pickers for historical data filtering
		$('input[name="timeFrame_start"]').datetimepicker({
			timeFormat : 'HH:mm:ss',
			beforeShow : function(input, inst) {
				inst.dpDiv.css({
					marginTop : -input.offsetHeight + 'px',
					marginLeft : input.offsetWidth + 'px'
				});
			}
		});

		$('input[name="timeFrame_end"]').datetimepicker({
			timeFormat : 'HH:mm:ss',
			beforeShow : function(input, inst) {
				inst.dpDiv.css({
					marginTop : -input.offsetHeight + 'px',
					marginLeft : input.offsetWidth + 'px'
				});
			}
		});

		// Fill out agents dropdown list
		jQuery.ajax({
			type : 'get',
			dataype : "json",
			url : isserver + '/rest/compare/getAgents',
			success : function(jsonObj) {
				var options = $('select[name="agent"]');

				if (jQuery.type(jsonObj) === "string")
					jsonObj = eval(jsonObj);
				jQuery.each(jsonObj.agents, function(index, data) {
					options.append($("<option />").val(data.agentID).text(
							data.agentIP));
				});
			}
		});

		$('select[name="agent"]').change(function() {
			var val = this.value;

			console.log(val);

			// var firstDropVal = $('#pick').val();
			jQuery.ajax({
				type : 'get',
				dataype : "json",
				url : isserver + '/rest/compare/getAgents',
				success : function(jsonObj) {
					var options = $('select[name="metric"]');

					if (jQuery.type(jsonObj) === "string")
						jsonObj = eval(jsonObj);
					jQuery.each(jsonObj.agents, function(index, data) {
						options.append($("<option />")
								.val(data.agentID).text(data.agentIP));
					});
				}
			});
		});
	},
	*/
};

var capitaliseFirstLetter = function(string) {
	return string.charAt(0).toUpperCase() + string.slice(1);
};



var connectToMonitoring = function() {
	console.log("Trying to connect to Monitoring...");
	$('.connectionToolbar .notConnected').addClass('noDisplay');
	$('.connectionToolbar .connected').removeClass('noDisplay');

	var deplId = 0;
	if (("deplID" in urlParams))
		deplId = urlParams.deplID;

	$('<iframe>', {
		src : isserver + '/monitoringProxy/?deplId=' + deplId,
		sandbox : "allow-same-origin allow-forms allow-scripts",
	}).appendTo('#liveMonitorData > .frameHolder');

	// <iframe sandbox="allow-same-origin allow-forms allow-scripts"
	// src="<%=pageContext.findAttribute("isserver")%>/monitoringProxy/">
	// </iframe>
}

var disconnectFromMonitoring = function() {
	console.log("Trying to disconnect from Monitoring...");
	$('.connectionToolbar .connected').addClass('noDisplay');
	$('.connectionToolbar .notConnected').removeClass('noDisplay');
	$('#liveMonitorData  > .frameHolder').remove();
}

var chartsApiWating = function() {
	$('.analyticsReportHolder > .placeHolder').addClass('msgOn').removeClass(
			'msgOff'); // show holder msg
	$('.analyticsReportHolder > .placeHolder').html("Wating API to load");
}

var chartsApiLoaded = function() {
	$('.analyticsReportHolder > .placeHolder').html("API is loaded");
	// sleep(2); //sleep 2seconds
	// Load the default
	$('[data-templtype="overview"]').trigger('click');
}

var onClick_component = function() {
	var component = $(this).data('component');
	var template = $(this).data('template');
	var templType = $(this).data('templtype');
	var reportID = $(this).data('id');
	var title = $(this).html();
	
	var deplId = urlParams.deplID;

	// Avoid to add the same report multiple time
	if ($('.analyticsReports [data-reportid="' + reportID + '"]').length > 0) {
		return null;
	}

	/*
	data = {};
	data['type'] = templType;
	data['template'] = template;
	 */
	
	// Pass the authendication token to the IS Server 
	// requests if exists.
	var reqParams = {};
	if (("token" in urlParams))
		reqParams['token'] =  urlParams.token;
	
	ajaxRequest(
		isserver + '/rest/deployment/' + deplId + '/topology',
        'get',
        reqParams,
        'json',
        function(jsonObj) {
			// Success
			// Build UI control According the response
			var well = $('.analyticsReports.well');
			var item = well.find('.wellItemTemplate').clone();
			var contentHolder = well.find('.wellContentHolder');

			var reportTemplate = $(jsonObj.reportTemplate);

			// Prepare well item
			item.removeClass('wellItemTemplate');
			item.addClass('wellItem');
			item.attr('data-reportid', reportID);
			item.find('.header .title').html(title);

			// TODO
			// Build visualizations draw object
			var visObject;
			if (templType == 'overview') {
				var vis = new appOverview(reportID);
				// getAjaxStuff();
				visObject = {
					'repId' : reportID,
					'dtCollector' : vis.getAjaxStuff,
					'dtVisualize' : vis.onDataReady,
					'cache' : false
				}
			} else {
				var vis = new appComponent(reportID);
				visObject = {
					'repId' : reportID,
					'dtCollector' : vis.ajax_getAnalysisStats,
					'dtVisualize' : vis.onDataReady,
					'cache' : true
				}
			}

			// Fill the report with appropriate content

			// TODO
			// Define rest call parameters such as metrics to analyze
			if (("deplID" in urlParams))
				deplId = urlParams.deplID;
			
			metrics = {};
			if(jsonObj.metrics != null)
				metrics = jsonObj.metrics;
			
			ajxParams = {
				'deplId' : deplId,
				'compId' : component,
				'metrics' : metrics
			};

			// Append Report
			item.find('.innerReportHolder').append(reportTemplate);
			$('.wellContentHolder > .placeHolder').removeClass('msgOn')
					.addClass('msgOff'); // hide holder msg
			contentHolder.append(item);

			// Add events to the report controls
			var closeBtn = item.find('[data-button="close"]');
			var expandBtn = item.find('[data-button="expand"]');

			closeBtn.off('click');
			closeBtn.on('click', function() {
				$(this).closest('.singleReport').remove();
				// Check and Resize
				resizeReports();
			});

			expandBtn.off('click');
			var expand = function() {
				// Hide all reports
				$(this).closest('.analyticsReportHolder').children(
						'.singleReport').addClass('noDisplay');
				// Resize current
				$(this).closest('.singleReport').addClass('expanded');
				// Display current
				$(this).closest('.singleReport').removeClass('noDisplay');
				// Rebuild charts according to the new width
				drawVisualization(visObject.repId, visObject.dtCollector,
						visObject.dtVisualize, ajxParams, visObject.cache);
				// Change Button functionality to collapse
				$(this).html('Collapse');
				$(this).off('click');
				$(this).on(
						'click',
						function() {
							$(this).closest('.analyticsReportHolder').children(
									'.singleReport').removeClass('noDisplay');
							$(this).closest('.singleReport').removeClass(
									'expanded');
							// change button functionality again to expand
							$(this).html('Expand');
							$(this).off('click');
							$(this).on('click', expand);
							// Rebuild charts according to the new width
							drawVisualization(visObject.repId,
									visObject.dtCollector,
									visObject.dtVisualize, ajxParams,
									visObject.cache);
						});
			}
			expandBtn.on('click', expand);

			// According to the reportHolder width and the
			// opened reports, resize the reports accordingly to fit into the
			// space
			// This must be done before drawing the charts

			// Temporary Code
			// Fill the report with appropriate content
			// TODO
			drawVisualization(visObject.repId, visObject.dtCollector,
					visObject.dtVisualize, ajxParams, visObject.cache);

			// Fix height
			$(window).trigger('resize');
		},
		function(jsonObj) {
			// error
			
		},
        null
    );
	
	/*
	jQuery.ajax({
		type : 'get',
		dataype : "json",
		data : data,
		url : wcserver + '/ajax/analytics/reports/template',
		success : function(jsonObj) {
			

		}
	});
	*/
}

var resizeReports = function() {
	var reportsHolder = $('.analyticsReportHolder');
	// Make all reports visible for any case
	reportsHolder.children('.singleReport').removeClass('noDisplay');

	// Calculate new size
	var newWidth = 200;

	// Append size
	reportsHolder.children('.singleReport').not('.noDisplay').css('width',
			newWidth + "px");

}

var resizeReportCharts = function() {

}
