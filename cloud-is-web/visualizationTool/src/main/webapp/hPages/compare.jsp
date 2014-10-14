<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>CELAR IS | Compare</title>
	<!--bootstrap script -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/script.js"></script>
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
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.tabber.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.outer.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.inner.css" rel="stylesheet">	
	<link href="<%=request.getContextPath()%>/Library/resources/css/page.compare.css" rel="stylesheet">
	
	<script src="http://code.jquery.com/jquery-1.9.0.js"></script>
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
    		packages: ['corechart'],
    		callback : pageLoaded
   		});
    });
   
    </script>
  </head>

<body>
	<div class="page">
		<div class="headInfoBar">
			<div class="notification dashboard-notice noDisplay">
				<button type="button" class="close" style="padding-right:50px">&times;</button>
				<strong>{{alert.title}}</strong> 
				<span></span>
				<div class="pull-right small ng-binding" style="padding-right:10px">1 alert(s)</div>
	    	</div>
	    	<div class="infoContent">
	    	
	    	</div>
	    	<div class="pageTitle">
	    		<div class="title">
	    			Comparison
	    		</div>
	    	</div>
    	</div>
	    <div class="navbar pageNavMenu">
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
	    <div class="pageMainContainer navOn compare">
			<div class="objectSelectorWrapper">
			  <div class="comparissonRibbon inner">		  	
			  	<div class="well">
			  		<div class="wellItemTemplate wellItem noDisplay">
			  			<div class="inner ribbonItem" >
			  				<div class="compItemHead">
			  					<div></div>
			  					<div></div>
			  				</div>
			  				<div class="compItemMain">
								<div><span>Application Id: </span><span data-name="appId"></span></div>
								<div><span>Version:  </span><span data-name="version"></span></div>
							</div>
							<div class="compItemFooter">
								<div></div>
							</div>
						</div>
					</div>
					<div class="wellContentHolder"></div>
			  	</div>
		  		<div class="addNewControl ribbonItem"></div>
		  	</div>
		  	</div>
			<div class="innerNavMenu">
		  		<div class="inner pull-right tabber">
					<div class="tabberNav" data-tabber-nav=true data-tabber-parentId="innerNavMenu">
						<div class="navTile selected" data-tabber-navTile=true data-tabber-ref="generReport"><span>Generated Report</span></div>
						<div class="navTile disabled" data-tabber-navTile=true data-tabber-ref="chartBuilder"><span>Chart Builder</span></div>
					</div>
				</div>
			</div>
			<div class="contentMainHolder tabber">
				<div class="tabberPagesWhapper pagesContainer" data-tabber-pages=true data-tabber-parentId="innerNavMenu">
					<div id="generReport" class="tabberPage mainCol full" data-tabber-page=true data-tabber-pageId="generReport">
						<span>Bar charts showing for how much time an application was on peek state (similar to performance analysis) in contrast with other versions</span>
						<div id="barChart"></div>
					</div>
					<div id="chartBuilder" class="tabberPage mainCol full" data-tabber-page=true data-tabber-pageId="chartBuilder">
						<div class="wellItem">
						  	<%
						  	String versID = request.getParameter("versID") == null ? "" : request.getParameter("versID");
						  	String[] versIDArray = null;
						  	if(versID.contains(","))
					    	{
						  		// More than one
					    		//split version
					    		versIDArray = versID.split(",");
					    	}		  	
						  	else if(!versID.isEmpty())
						  	{
						  		// only one
						  		versIDArray[0] = versID;		  		
						  	}
						  	if(versIDArray!=null)
						  	{
							  	for(int i=0; i < versIDArray.length; i++)
							  	{
							  		out.print("<select>");
							  		out.print("<option selected value=\""+versIDArray[i]+"\">");
							  		out.print("version " + versIDArray[i]);
							  		out.print("</option>");
							  		out.print("</select>");
							  	}
						  	}
						  	%>		  	
						</div>
					</div>
				</div>
			</div>
	    </div>	
	</div>
</body>
</html>
