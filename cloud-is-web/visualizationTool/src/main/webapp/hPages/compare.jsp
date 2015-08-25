<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>CELAR IS | Compare</title>
	<!--bootstrap script -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/bootstrap.js"></script>
	<script type="text/javascript">
		var isserver = "<%=pageContext.findAttribute("isserver")%>";	
		var wcserver = window.location.protocol + "//" + window.location.host + "<%=request.getContextPath()%>/";
		/*
		var path = window.location.pathname;
		var dPath = "<%=pageContext.findAttribute("vspath")%>"; // path after the server address showing the webapp root
		if(path.indexOf(dPath) >= 0)
		{
			wcserver += dPath;
		}
		*/		
		isServerValidation();
	</script>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/util.ui.tabber.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.outer.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.inner.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/page.compare.css"/>
	
	<script src="http://code.jquery.com/jquery-1.9.0.js"></script>
	<script type="application/javascript" src="<%=request.getContextPath()%>/Library/resources/js/util.ajax.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/util.ui.tabber.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/page.compare.js">	</script>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>

    <!--Load the AJAX API-->
    
    <script type="text/javascript">

    var pageLoaded = function () {
    	// some stuf...
 		// $(document).trigger('load.external');
    	//getAjaxStuff();
    	var dt = new Object();
    	var vers = $('.headBar select');
    	
    	var i = 0;
    	$.each(vers, function(index, obj) {
    		val = $(obj).find('option:selected').text();
    		dt[i] = val;
    		i++;
    	});
    	
    	drawChart(dt, "barChart");
 	};
    
    var formalizeData = function (opts) {
    	var data = new google.visualization.DataTable();
		 data.addColumn('string', 'x');
		 data.addColumn({type: 'string', role: 'annotation'});
		 data.addColumn({type: 'string', role: 'annotationText'});
		 data.addColumn('number', 'Cpu');		 
		 return data;
 	};
 	
 	var inputData = function (dataObj, data) {
    	$.each(data, function(index, value){
    		dataObj.addRow(["", null, null, parseFloat(value)]);
    	});
		return dataObj;
 	};
 	
 	var inputActions = function (dataObj, data) {
		 $.each(data, function(name, value){
			 dataObj.addRow(["", name, 'Resizing Action', parseFloat(value)]);
    	});
		return dataObj;
 	};
 	
 	var drawChart = function (data, containerID) {
 		// Create and populate the data table.
 		var dataT = google.visualization.arrayToDataTable([
 			['Year', 'Cpu', 'Ram', 'Disk IO', 'Net IO'],
 		    [data[0],  36,    40,    24,   12],
 		    [data[1],  58,    36,    48,   15],
 		 ]);

 		// Create and draw the visualization.
 		var bc = new google.visualization.BarChart(document.getElementById(containerID));
		bc.draw(dataT,
           {title:"",
            height:400,
            vAxis: {title: "Versions"},
           }
    	);
 	};
    
    var onDataReady = function(jsonObj){
		 // example copied from Google Visualization API playground,
		 // modified for category axis annotations
		   
		 // Create and populate the data table.
		 jsonObj = $.parseJSON(jsonObj);
		 var actions = jsonObj.actions;
		 delete jsonObj['actions'];
		 $.each(jsonObj, function (title, objData){
			 var containerID = title + "_visualization";
			 $.each(objData, function (title, value){
				if(title == "data" )
				{
					data = formalizeData(null);
					data = inputData(data, value);
					data = inputActions(data, actions);
					drawChart(data, containerID);
				}
			 });			 
		 });		 
    };
    
    var getAjaxStuff = function(){
    	jQuery.ajax({
    		type: 'get',
    		dataype: "json",
    		url: isserver + '/rest/analysis/stats/0/0/0',
    		success: onDataReady
    	});
    };
    
    $(document).ready(function() {      	 	
    	google.load('visualization', '1', {
    		packages: ['corechart', 'table'],
    		callback : pageLoaded
   		});
    });
   
    </script>
  </head>

<body>
	<div class="page">
		<div class="headInfoBar background dark">
			<div class="noDisplay"></div>
	    	<div class="visualFeedback">
	    		<div class="loadingIndicator noDisplay">
				  <div></div>
				  <div></div>
				  <div></div>
				  <div></div>
	    		</div>
	    		<div class="globalNotification noDisplay">
	    			<div class="ntf">
	    				<div class="ntf_message"></div>
	    			</div>
	    		</div>
	    	</div>
	    	<div class="noDisplay"></div>
    	</div>
	    <div class="navbar pageNavMenu background dark">
			<div class="navbar-inner">
				<div class="nav pull-left">
					<div class="navTile">
		        		<div class="navTile-inner">
		        			<a href="<%=request.getContextPath()%>/search/" target="_self">
			        			<div class="tileIco"><span class="helper"></span><img src="<%=request.getContextPath()%>/Library/media/search.svg"></div>
			        			<div class="tileText"><span>Search</span></div>
		        			</a>
		        		</div>
		        	</div>					
					<div class="navTile">
		        		<div class="navTile-inner">
		        			<a href="<%=request.getContextPath()%>/" target="_self">
			        			<div class="tileIco"><span class="helper"></span><img src="<%=request.getContextPath()%>/Library/media/dashboard.svg"></div>
			        			<div class="tileText"><span>Dashboard</span></div>
		        			</a>
		        		</div>
		        	</div>
		        	<div class="navTile selected">
		        		<div class="navTile-inner">
		        			<a href="<%=request.getContextPath()%>/application/version/compare/" target="_self">
			        			<div class="tileIco"><span class="helper"></span><img src="<%=request.getContextPath()%>/Library/media/compare.svg"></div>
			        			<div class="tileText"><span>Comparison</span></div>
		        			</a>
		        		</div>
		        	</div>					
				</div>
			</div>
	    </div>
	    <div class="pageMainContainer background light navOn compare"  data-tabber-id="innerNavMenu">
			<div class="objectSelectorWrapper">
			  <div class="comparissonRibbon inner">		  	
			  	<div class="well">
			  		<div class="wellItemTemplate wellItem comparableVersion noDisplay">
			  			<div class="inner ribbonItem" >
			  				<div class="compItemHead">
			  					<div><span data-name="name"></span></div>
			  				</div>
			  				<div class="compItemMain">
								<div class="noDisplay"><span>Application Id: </span><span data-name="uid"></span></div>
								<div class="noDisplay"><span>Version:  </span><span data-name="version"></span></div>
								<div style="font-style: italic;"><span data-name="appCombId"></span></div>
								<div><span>Deployment: </span><span data-name="deployment"></span></div>
							</div>
							<div class="compItemFooter">
								<div></div>
							</div>
						</div>
					</div>
					<div class="wellContentHolder comparissonItems"></div>
			  	</div>
		  		<div class="controlWrapper ribbonItem">
		  			<div class="commons-controll controll addNew" role="button" data-action="addNew"><span>ADD</span></div>
		  			<div class="commons-controll controll compare" role="button" data-action="comparePrep"><span>COMPARE</span></div>
		  		</div>
		  	</div>
		  	</div>
			<div class="innerNavMenu">
		  		<div class="inner pull-right tabber">
					<div class="tabberNav" data-tabber-nav=true data-tabber-parentId="innerNavMenu">
						<div class="navTile" data-tabber-navTile=true data-tabber-ref="generReport"><span>Generated Report</span></div>
						<div class="navTile selected" data-tabber-navTile=true data-tabber-ref="chartBuilder"><span>Chart Builder</span></div>
					</div>
				</div>
			</div>
			<div class="contentMainHolder background lighter tabber">
				<div class="tabberPagesWhapper pagesContainer" data-tabber-pages=true data-tabber-parentId="innerNavMenu">
					<div id="generReport" class="noDisplay tabberPage mainCol full" data-tabber-page=true data-tabber-pageId="generReport">
						<span>Bar charts showing for how much time an application was on peek state (similar to performance analysis) in contrast with other versions</span>
						<div id="barChart"></div>
					</div>
					<div id="chartBuilder" class="tabberPage mainCol full" data-tabber-page=true data-tabber-pageId="chartBuilder">
						<div class="comparissonContent noDisplay">
							<div class="componentSelectionBar sidebar">
								<div class="toolbar">
									<div class="controll commonMetrics actGo disabled" role="button" data-action="commonMetrics"><span>Show Metric</span></div>
								</div>															
								<div class="well">
							  		<div class="wellItemTemplate wellItem versionComponent noDisplay">
							  			<div class="inner" >
							  				<div class="versionItemHead">
							  					<div><span>Version:  </span><span data-name="version"></span></div>
							  				</div>
							  				<div class="versionItemMain noDisplay">
												
											</div>
										</div>
									</div>
									<div class="wellContentHolder selectedVersions"></div>
								</div>
							</div>
							<div class="chartAreaOuterWrapper">
								<div class="chartContentToolbar toolbar">
										<div class="controll compare actGo" role="button" data-action="compare"><span>Build Chart</span></div>
								</div>
								<div  class="chartArea">
									<div class="metricsSelectionBar sidebar">
										<div class="well">
									  		<div class="wellItemTemplate wellItem singleMetric noDisplay">
									  			
											</div>
											<div class="wellContentHolder selectedMetrics"></div>
										</div>
									</div>
									<div class="chartContentWrapper">									
										<div class="chartContentHolder">
									
										</div>
										<div class="chartContentSummary">
									
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="noContentNotice"></div>
					</div>
				</div>
			</div>
	    </div>	
	</div>
	<div class="cd-popup appSelectorPopUp" role="alert">
		<div class="cd-popup-container">
			<p>Select Application Versions to Compare</p>
			<div>
				<div class="applicationSelectionBar sidebar">
					<div class="well">
				  		<div class="wellItemTemplate wellItem application noDisplay">
				  			<div class="inlineChilds">
					  			<div class=""><span data-name="description"></span></div>
					  			<div class=""><span data-name="uid"></span></div>
					  			<div class=""><span data-name="appCombId"></span></div>
					  			<div class=""><span data-name="version"></span></div>
				  			</div>
				  			<div class="metaContent noDisplay">
				  				<span data-name="name"></span>
				  			</div>
						</div>
						<div class="wellContentHolder applicationList"></div>
					</div>
				</div>
			</div>
			<ul class="cd-buttons">
				<li><a href="#0" data-action="aprove">Yes</a></li>
				<li><a href="#0" data-action="cancel">No</a></li>
			</ul>
			<a href="#0" class="cd-popup-close img-replace">Close</a>
		</div> <!-- cd-popup-container -->
	</div> <!-- cd-popup -->
</body>
</html>
