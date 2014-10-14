<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>CELAR IS | Deployment</title>
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
	<link href="<%=request.getContextPath()%>/Library/media/fonts/flaticon.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.tabber.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.form.css" rel="stylesheet">	
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.contentBox.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.well.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.beanBox.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.charts.common.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.outer.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.inner.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/page.deployment.css" rel="stylesheet">
	
	<script src="http://code.jquery.com/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/ui/1.10.4/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/ext/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/util.ui.tabber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/page.deployment.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/page.deployment.func.vis.js"></script>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	
	<!-- To be loaded Dynamically -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/reports/vis.appComponent.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/reports/vis.appOverview.js"></script>	
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
	    			Deployment
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
		        			<div class="tileIco"><span class="helper"></span><img src="<%=request.getContextPath()%>/Library/media/deployment.svg"></div>
		        			<div class="tileText"><span>Deployment</span></div>
	        			</a>
	        		</div>
	        	</div>
	        </div>
	      </div>
	    </div>
	    <div class="pageMainContainer navOn deployment" data-tabber-id="innerNavMenu">
		  	<div class="innerNavMenu">
		  		<div class="inner pull-right tabber">
					<div class="tabberNav" data-tabber-nav=true data-tabber-parentId="innerNavMenu">
						<div class="navTile selected" data-tabber-navTile=true data-tabber-ref="generReport"><span>Generated Report</span></div>
						<div class="navTile" data-tabber-navTile=true data-tabber-ref="pastMonitorData"><span>Past Monitoring Data</span></div>
						<div class="navTile" data-tabber-navTile=true data-tabber-ref="liveMonitorData"><span>Live Monitoring Data</span></div>
						<div class="navTile disabled" data-tabber-navTile=true data-tabber-ref="liveCostData"><span>Live Cost Data</span></div>
					</div>
				</div>
			</div>
			<div class="contentMainHolder tabber">
				<div class="tabberPagesWhapper pagesContainer" data-tabber-pages=true data-tabber-parentId="innerNavMenu">
					<div id="generReport" class="tabberPage generatedReportsTabPage" data-tabber-page=true data-tabber-pageId="generReport">
						<div class="topologyLayoutSideBar">
							<div class="appComponentList noDisplay">
		    					<div class="header">
		    						<div class="inner">
		    							<div class="title">
		    								<span>Component List</span>
		    							</div>
		    						</div>
		    					</div>
		    					<div class="body">
		    						<div class="inner">
		    							<div class="row">
		    								<span class="clickable" data-id="overview" data-component="overview" data-template="appOverview">Overview</span>
		    							</div>
		    							<div class="row treeView">
		    								<div style="padding:2px 7px">
			    								<span class="clickable" data-id="loadBalancer" data-component="loadBalancer" data-template="appComponent">Load Balancer</span>
			    							</div>
			    							<div class="" style="padding:2px 7px">
			    								<span  class="clickable" data-id="appServer" data-component="appServer" data-template="appCompositeComponent">App Server</span>			    								
			    							</div>
			    							<div class="" style="padding:2px 7px">
			    								<span class="clickable" data-id="database" data-component="database" data-template="appCompositeComponent">Database</span>
			    							</div>
		    							</div>
		    						</div>
		    					</div>
		    				</div>
						</div>
						<div class="analyticsReports well">
							<div class="wellItemTemplate singleReport contentBox">
								<div class="header">
		    						<div class="inner">
		    							<div class="title">
		    								<span>Overview</span>
		    							</div>
		    							<div class="controls">
		    								<span class="clickable" data-button="expand">Expand</span>
		    								<span class="clickable" data-button="close">Close</span>
		    							</div>
		    						</div>
		    					</div>
		    					<div class="body">
		    						<div class="inner innerReportHolder">
		    							<!-- Here goes the report -->	    							
		    						</div>
		    					</div>
		    				</div>
		    				<div class="analyticsReportHolder wellContentHolder">
		    					<div class="placeHolder msgOn"></div>
		    				</div>
						</div>					
					</div>
					<div id="pastMonitorData" class="tabberPage noDisplay" data-tabber-page=true data-tabber-pageId="pastMonitorData">
						<div class="sidebar noDisplay">
							<div class="contentCol"></div>
							<div class="togglerCol"><span class="control">*</span></div>
						</div>
						<div class="mainCol full">
							<form class="inline" name="displayMetric">
								<div class="formContent">
									<div class="formRow"><label><span>Agent: </span></label><select name="agent">
																									
																							</select></div>
									<div class="formRow"><label><span>Metric: </span></label><select name="metric">
																									
																							</select></div>
									<div class="formRow"><label><span>Start Time: </span></label><input name="timeFrame_start" /></div>
									<div class="formRow"><label><span>End Time: </span></label><input  name="timeFrame_end"/></div>
								</div>
								<div class="formControls">
									<button type="button">Show Chart</button>
								</div>
							</form>
							<div class="chartPool">
							</div>
						</div>
					</div>
					<div id="liveMonitorData" class="tabberPage noDisplay" data-tabber-page=true data-tabber-pageId="liveMonitorData">
						<div class="connectionToolbar">
							<div class="inner">
								<div class="connected noDisplay">
									<span>You are currently connected to the Monitoring System.</span><span class="disconnectBtn anchor">Disconnect</span>
								</div>
								<div class="notConnected">
									<span>You are currently not connected to the Monitoring System. To get live data please </span><span class="connectBtn anchor">Connect</span>
								</div>
							</div>
						</div>
						<div class="frameHolder"></div>
					</div>
					<div id="liveCostData" class="tabberPage noDisplay" data-tabber-page=true data-tabber-pageId="liveCostData"></div>
				</div>
		    </div>
    	</div>
   	</div>
</body>
</html>
