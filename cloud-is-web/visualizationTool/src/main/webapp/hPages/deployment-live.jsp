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
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.js"></script>
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
						<div class="navTile" data-tabber-navTile=true data-tabber-ref="liveMonitorData"><span>Live Monitoring Data</span></div>
						<div class="navTile disabled" data-tabber-navTile=true data-tabber-ref="liveCostData"><span>Live Cost Data</span></div>
					</div>
				</div>
			</div>
			<div class="contentMainHolder background lighter tabber">
				<div class="tabberPagesWhapper pagesContainer" data-tabber-pages=true data-tabber-parentId="innerNavMenu">					
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
