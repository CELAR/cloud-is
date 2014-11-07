<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>CELAR IS | Version</title>
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
	<script src="http://code.jquery.com/jquery-1.9.0.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/util.ui.tabber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/page.version.js"></script>
	
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.onoffswitch.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.well.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.tabber.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.well.extended.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.outer.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.inner.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/page.version.css" rel="stylesheet">

</head>
<body>
	<div class="pageHelper">
		<span data-appID><%=pageContext.findAttribute("appId")%></span>
	</div>
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
	    			Version
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
	        	<div class="navTile">
	        		<div class="navTile-inner">
	        			<a href="<%=request.getContextPath()%>/application/version/compare/" target="_self">
		        			<div class="tileIco"><span class="helper"></span><img src="<%=request.getContextPath()%>/Library/media/compare.svg"></div>
		        			<div class="tileText"><span>Comparison</span></div>
	        			</a>
	        		</div>
	        	</div>
	        	<div class="navTile selected">
	        		<div class="navTile-inner">
	        			<a href="" target="_self">
		        			<div class="tileIco"><span class="helper"></span><img src="<%=request.getContextPath()%>/Library/media/versions.svg"></div>
		        			<div class="tileText"><span>Version</span></div>
	        			</a>
	        		</div>
	        	</div>
	        </div>
	      </div>
	    </div>
	    <div class="pageMainContainer navOn version" data-tabber-id="innerNavMenu">
		  	<div class="innerNavMenu">
		  		<div class="inner pull-left">
					<span class="glyphicon glyphicon-tasks clickable"><img height="25px" width="35px" src="<%=request.getContextPath()%>/Library/media/tasks.svg"/></span>
				</div>
		  		<div class="inner tabber pull-right">
					<div class="tabberNav" data-tabber-nav=true data-tabber-parentId="innerNavMenu">
						<div class="navTile selected" data-tabber-navTile=true data-tabber-ref="overview"><span>Overview</span></div>
						<div class="navTile disabled" data-tabber-navTile=true data-tabber-ref="description"><span>Description</span></div>
						<div class="navTile" data-tabber-navTile=true data-tabber-ref="deployments"><span>Deployments</span></div>
						<!-- Ommited should go to deployment 
						<div class="navTile disabled" data-tabber-navTile=true data-tabber-ref="versAnalysis"><span>Performance Analysis</span></div>
						<div class="navTile disabled" data-tabber-navTile=true data-tabber-ref="desicionsTaken"><span>Resizing Actions</span></div> 
						-->
					</div>
				</div>
			</div>
			<div class="contentMainHolder">
	    		 <div class="sideContentHolder"></div>
	    		 <div class="mainContentHolder tabber">
	    		 	<div class="tabberPagesWhapper pagesContainer" data-tabber-pages=true data-tabber-parentId="innerNavMenu">
						<div class="tabberPage versOverviewPage" data-tabber-page=true data-tabber-pageId="overview">
							<div class="generalInfoSidebar">
								<div class="infoBox">
									<div class="inner">
										<div class="header">
											<span>Version Info</span>
										</div>
										<div class="content">
								
										</div>
									</div>
								</div>
							</div>
							<div class="mainPane">
								<div class="section">
									<div class="inner">
										<div class="header">
											<span>This sestion 1 Header</span>
										</div>
										<div class="content">
								
										</div>
									</div>
								</div>
								
								<div class="section"></div>
								<div class="section"></div>
							</div>
						</div>
						<div id="versDescription" class="tabberPage noDisplay" data-tabber-page=true data-tabber-pageId="description" style="overflow-y:scroll; height: inherit;">	
							<div class="appTopology"></div>
						</div>
						<div id="versDeployments" class="tabberPage deploymentsPage noDisplay" data-tabber-page=true data-tabber-pageId="deployments" style="overflow-y:scroll; height: inherit;">
							<div class="well">
								<div class="wellItemTemplate wellItem noDisplay">
									<div><span>Deployment Id: </span><span data-name="deplId"></span></div>
									<div><span>Started On: </span><span data-name="strTime"></span></div>
									<div><span>Ended On: </span><span data-name="endTime"></span></div>
								</div>
								<div class="wellContentHolder">
									<div class="tempItem wellItem">
										<div><span>Deployment Id: </span><span data-name="deplId">1</span></div>
										<div><span>Started On: </span><span data-name="sTime"> - </span></div>
										<div><span>Ended On: </span><span data-name="eTime"> - </span></div>
									</div>
								</div>
							</div>								
						</div>
						
					</div>
	    		 </div>
	    	</div>
		</div>
	</div>
</body>
</html>
<!--
	<div class="page">
		<div class="dashboard collapsed">
			<div class="applicationsHolder">
				<div class="header"><span>My applications</span></div>
				<div class="well">
					<div class="wellItemTemplate wellItem noDisplay">
						<div><span>Application Id: </span><span data-name="appId"></span></div>
						<div class="noDisplay"><span>App Name</span><span data-name="appName"></span></div>
						<div><span>Submited On: </span><span data-name="sumbited"></span></div>
					</div>
					<div class="wellContentHolder"></div>
				</div>
			</div>
			<div class="versionsHolder">			
				<div class="inner">
					<div class="header">
						<div class="content"><span>Versions</span></div>
						<div class="compareControl">
							<div data-btn="compare"><span>Compare: </span></div>
							<div class="onoffswitch">
							    <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch">
							    <label class="onoffswitch-label" for="myonoffswitch">
							        <div class="onoffswitch-inner">
							            <div class="onoffswitch-active"><div class="onoffswitch-switch">ON</div></div>
							            <div class="onoffswitch-inactive"><div class="onoffswitch-switch">OFF</div></div>
							        </div>
							    </label>
							</div>
						</div>
					</div>
					<div class="well">
						<div class="wellItemTemplate wellItem noDisplay">
							<div><span>Version Id: </span><span data-name="versId"></span></div>
							<div class="noDisplay"><span>App Name</span><span data-name="versName"></span></div>
							<div><span>Submited On: </span><span data-name="sumbited"></span></div>
						</div>
						<div class="wellContentHolder"></div>
					</div>				
				</div>
			</div>
			-->
			
<!-- 
// These parts should go to Deployments Page
	//
	<div id="cpu_visualizationContainer">
		<div class="lineGraphHeader">
			<div class="graphTitle">Overall (aggregarated average) <span class="value"></span> usage percentage</div>
			<div class="graphInfo">
				<div data-context="max"><span class="label">Max: </span><span class="value"></span></div>
				<div data-context="min"><span class="label">Min: </span><span class="value"></span></div>
				<div data-context="avg"><span class="label">Avg: </span><span class="value"></span></div>
			</div>
		</div>
		<div id="cpu_visualization" style="height:300"></div>
	</div>
	<div id="ram_visualizationContainer">
		<div class="lineGraphHeader">
			<div class="graphTitle">Overall (aggregarated average) <span class="value"></span> usage percentage</div>
			<div class="graphInfo">
				<div data-context="max"><span class="label">Max: </span><span class="value"></span></div>
				<div data-context="min"><span class="label">Min: </span><span class="value"></span></div>
				<div data-context="avg"><span class="label">Avg: </span><span class="value"></span></div>
			</div>
		</div>
		<div id="ram_visualization" style="height:300"></div>
	</div>
	<div id="disk_visualizationContainer">
		<div class="lineGraphHeader">
			<div class="graphTitle">Overall (aggregarated average) <span class="value"></span> usage percentage</div>
			<div class="graphInfo">
				<div data-context="max"><span class="label">Max: </span><span class="value"></span></div>
				<div data-context="min"><span class="label">Min: </span><span class="value"></span></div>
				<div data-context="avg"><span class="label">Avg: </span><span class="value"></span></div>
			</div>
		</div>
		<div id="disk_visualization" style="height:300"></div>	
	</div>
	
	//
	<div id="versAnalysis" class="tabberPage noDisplay" data-tabber-page=true data-tabber-pageId="versAnalysis" style="overflow-y:scroll; height: inherit;">
		<span>Percentange of time, the resource's CPU/RAM/DISK was over / under -ultilized</span>
		<div class="pieChartSeriesLegend rowLegend" data-targetcontext="application">
			<div class="legendItem"><div class="color"></div><span class="title"></span></div>
		</div>
		<div class="pieChartSeriesWrapper" data-targetcontext="application">
			<div id="pc_cpu_app" class="chartSeriesFrag"></div>
			<div id="pc_ram_app" class="chartSeriesFrag"></div>
		</div>
		<div class="pieChartSeriesWrapper" data-targetcontext="application">
			<div id="pc_disk_app" class="chartSeriesFrag"></div>
			<div id="pc_net_app" class="chartSeriesFrag"></div>
		</div>
		<div class="noDisplay toBeImplemented">
			<h4>Per Modules</h4>
			<span>For 20% (1200s) of total time, Module_1 cpu (average aggregated) was over 80%</span>
			<h4>Per Resource Type</h4>
			<span>For 20% (1200s) of total time, Resource(type: m1.small) cpu (average aggregated) was over 80%</span>
		</div>
	</div>
	
	//
	<div id="desicionsTaken" class="tabberPage noDisplay" data-tabber-page=true data-tabber-pageId="desicionsTaken" style="overflow-y:scroll; height: inherit;">
		<h4>Per Module Decisions</h4>
		<span>Desicion 1 : Alocated X resources , Deallocated Y resources</span>
		<h4>List of Actions</h4>
	</div>
	
 -->
			
			