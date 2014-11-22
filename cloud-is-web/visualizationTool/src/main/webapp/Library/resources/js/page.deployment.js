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
		// Omit unused tabs
		// TODO
		$('[data-tabber-ref="liveMonitorData"]').remove();
		$('[data-tabber-ref="liveCostData"]').remove();

		// Hacky approach
		// TODO
		$('[data-tabber-ref="generReport"] > span').trigger('click');

		initScripts.initAnalysis();
	} else if (tab == 'monitoring') {
		// Hacky approach
		// Omit unused tabs
		// TODO
		$('[data-tabber-ref="generReport"]').remove();
		$('[data-tabber-ref="pastMonitorData"]').remove();

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

		console.log('initAnalysis');
		// Load App Structure Tree Navigator
		jQuery.ajax({
			type : 'get',
			dataype : "json",
			url : isserver + '/rest/application/deployment/' + deplId
					+ '/topology',
			success : function(jsonObj) {
				// Build UI control According the response
				$('.appComponentList').removeClass('noDisplay');
				// Assign onClick Events
				$.each($('.appComponentList [data-component]'), function() {
					$(this).off('click');
					$(this).on('click', onClick_component);
				});
				// Load Charts API
				chartsApiWating();
				initScripts.initChartsAPI(chartsApiLoaded);
			}
		});
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

	// Avoid to add the same report multiple time
	if ($('.analyticsReports [data-reportid="' + reportID + '"]').length > 0) {
		return null;
	}

	data = {};
	data['type'] = templType;
	data['template'] = template;

	jQuery.ajax({
		type : 'get',
		dataype : "json",
		data : data,
		url : wcserver + '/ajax/analytics/reports/template',
		success : function(jsonObj) {
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

		}
	});
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
