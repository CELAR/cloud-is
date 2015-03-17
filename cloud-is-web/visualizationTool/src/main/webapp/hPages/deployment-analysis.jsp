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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/ext/iThing.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/media/fonts/flaticon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/util.ui.tabber.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/util.ui.form.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/util.ui.contentBox.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/util.ui.well.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/util.ui.beanBox.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/util.ui.charts.common.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.outer.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.inner.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Library/resources/css/page.deployment.css"/>
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/ui/1.10.4/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/util.ui.tabber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/page.deployment.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/page.deployment.func.vis.js"></script>
	<script type="application/javascript" src="<%=request.getContextPath()%>/Library/resources/js/ext/dom.jsPlumb-1.7.2.js"></script>	
	<script type="application/javascript" src="<%=request.getContextPath()%>/Library/resources/js/ext/jQDateRangeSlider-min.js"></script>
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
	    	<div class="infoContent background dark">
	    	
	    	</div>
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
	    <div class="pageMainContainer background light navOn deployment" data-tabber-id="innerNavMenu">
		  	<div class="innerNavMenu">
		  		<div class="inner pull-right tabber">
					<div class="tabberNav" data-tabber-nav=true data-tabber-parentId="innerNavMenu">
						<div class="navTile selected" data-tabber-navTile=true data-tabber-ref="generReport"><span>Automatic Reports</span></div>
						<div class="navTile" data-tabber-navTile=true data-tabber-ref="topologyExplorer"><span>Topology Explorer</span></div>
					</div>
				</div>
			</div>
			<div class="contentMainHolder background lighter tabber">
				<div class="tabberPagesWhapper pagesContainer" data-tabber-pages=true data-tabber-parentId="innerNavMenu">
					<div id="generReport" class="tabberPage generatedReportsTabPage" data-tabber-page=true data-tabber-pageId="generReport">
						<div class="topologyLayoutSideBar background light"> <!-- side > inner -->
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
		    								<span class="clickable" data-id="overview" data-component data-templtype="overview" data-template="appOverview">Overview</span>
		    							</div>
		    							<div class="row treeView well">
			    							<div class="wellItem wellItemTemplate" style="padding:2px 7px">
			    								<span class="clickable" data-id="" data-templtype="default" data-template="appComponent" ></span>			    								
			    							</div>
			    							
			    							<!-- 
			    								<div style="padding:2px 7px">
				    								<span class="clickable" data-id="loadBalancer" data-component="loadBalancer" data-templtype="loadBalancer" data-template="appComponent" >Load Balancer</span>
				    							</div>
				    							<div class="" style="padding:2px 7px">
				    								<span  class="clickable" data-id="appServer" data-component="appServer" data-templtype="appServer" data-template="appCompositeComponent">App Server</span>			    								
				    							</div>
				    							<div class="" style="padding:2px 7px">
				    								<span class="clickable" data-id="database" data-component="database" data-templtype="database" data-template="appCompositeComponent">Database</span>
				    							</div>
			    								 -->
			    							
		    								<div class="wellContentHolder">
			    								
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
					<div id="topologyExplorer" class="tabberPage noDisplay initTopologyExplorerTabPage" data-tabber-page=true data-tabber-pageId="topologyExplorer">
						<div class="timeControlBar background light">
							<div class="promptWrapper"><span>Define time range: </span></div>							
							<div id="slider" class="sliderContainer"></div>				
							<div class="applyBtn">
								<div class="commons-controll positive" role="button" data-action="analyze"><span>Apply</span></div>
							</div>
						</div>
						<div class="timeRange background light">
						</div>					
						<div class="topologyCanvasWrapper">
							<div class="noDisplay templatePool">
								<div class="component" data-type="template">
									<div class="title"><span data-name="name"></span></div>
									<div class="connect"></div>
									<div class="metadata noDisplay"></div>
									<div class="assignedMetrics well">
										<div class="wellItemTemplate singleMetric"><span></span></div>
										<div class="assignedMetricsHolder wellContentHolder"></div>
									</div>
								</div>
							</div>
							<div class="topologyCanvas"></div>
							<div class="nodeInfoPanel">
								<div class="panelControlBar background light">
									<div class="inner">
										<div class="panelCBarItem"><div class="commons-controll positive" role="button" data-action="save"><span>Save</span></div></div>
										<div class="panelCBarItem"><div class="commons-controll positive" role="button" data-action="saveClose"><span>Save &amp; Close</span></div></div>
										<div class="panelCBarItem"><div class="commons-controll negative" role="button" data-action="close"><span>Close</span></div></div>
									</div>
								</div>
								<div class="panelContent">
									<div class="nodeInfoSection">
										<div class="title"><span> Name / Description: </span><span data-name="name"></span></div>
									</div>
									<div class="metricsSelectorWrapper">
										<div class="metricsListWrapper">
											<div class="inner">
												<div class="header">
													<div class="title"><span>Available Metrics</span></div>
													<div class="controllWrapper"><div class="commons-controll" role="button" data-action="moveToSelected"  data-id="btn_moveToSelected"><span>Move All</span></div></div>
												</div>
											    <select id="metricsList" class="selectList metricsList" multiple="multiple"></select>
											</div>
										</div>														
										<div class="metricsListWrapper">
											<div class="inner">											
												<div class="header">
													<div class="title"><span>Selected Metrics</span></div>
													<div class="controllWrapper"><div class="commons-controll" role="button" data-action="moveToAvailable"  data-id="btn_moveToAvailable"><span>Move All</span></div></div>
												</div>
											    <select id="selectedMetricsList" class="selectList metricsList" multiple="multiple"></select>
										    </div>
										</div>
									</div>										
								</div>
							</div>
						</div>
						
						
						
						<!-- 
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
						-->
					</div>
				</div>
		    </div>
    	</div>
   	</div>
	
</body>
</html>
