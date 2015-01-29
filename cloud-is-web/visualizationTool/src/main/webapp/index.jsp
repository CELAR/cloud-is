<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>CELAR IS | DashBoard</title>
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
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.contentBox.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.beanBox.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.well.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.outer.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.inner.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/page.index.css" rel="stylesheet">
	
	<script src="http://code.jquery.com/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/page.index.js"></script>   
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
	    		<div class="appHeader noDisplay">
	    			<img class="appLogo" src="<%=request.getContextPath()%>/Library/media/cellar_small_logo.png">
	    			<span class="appTitle"> > </span><span class="appTitle">Information System</span>
		    	</div>
		    	<div class="appAbout mn  noDisplay" style="float:right">
	    			<div class="mnItem"><a class="plain" href="#"><span>Help</span></a></div>
	    			<div class="mnItem seperator"><span>|</span></div>
	    			<div class="mnItem"><a class="plain" href="#"><span>About</span></a></div>
		    	</div>
	    	</div>
    	</div>
	    <div class="navbar pageNavMenu background dark">
	      <div class="navbar-inner">
	        <div class="nav pull-left">
	        	<div class="nav pull-left">
	        	<div class="navTile">
	        		<div class="navTile-inner">
	        			<a href="<%=request.getContextPath()%>/search/" target="_self">
		        			<div class="tileIco"><span class="helper"></span><img src="<%=request.getContextPath()%>/Library/media/search.svg"></div>
		        			<div class="tileText"><span>Search</span></div>
	        			</a>
	        		</div>
	        	</div>
	        	<div class="navTile selected">
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
	        </div>
	        </div>
	      </div>
	    </div>
	    <div class="pageMainContainer background light index">
	    	<div class="contentMainHolder background lighter">   			
		    	<div class="dashboardMain">
	    			<div class="paneHolder contentBoxHolder scrollY">
	    				<div class="dashPaneRow">
		    				<div class="pane contentBox" style="width:300px">
		    					<div class="header">
		    						<div class="inner">
		    							<div class="title">
		    								<span>Recent Activity</span>
		    							</div>
		    						</div>
		    					</div>
		    					<div class="body">
		    						<div class="inner">
		    							<span><i>No Recent Activity</i></span>
		    						</div>
		    					</div>
		    				</div>
		    				<div class="pane contentBox rcDeployments" style="width:300px">
		    					<div class="header">
		    						<div class="inner">
		    							<div class="title">
		    								<span>Recent Deployments</span>
		    							</div>
		    						</div>
		    					</div>
		    					<div class="body">
		    						<div class="inner well">
		    							<div class="wellItemTemplate deploymentBean">
		    								<div class="meta noDisplay">
		    									<span data-name="id">1</span>
		    								</div>
		    								<div class="content">
		    									<div class="statusBar inlineChilds">
		    										<div class="status online"><span>Online</span></div>
			    									<div class="control pull-right" data-type="button" data-name="inspect"><span class="clickable">Inspect</span></div>
		    									</div>		    								
		    									<div class="title inlineChilds">
		    										<div class="label"><span>Deployment</span></div>
		    										<div class="value pull-right"><span>(123456789)</span></div>
		    									</div>
		    									<div class="info inlineChilds">
		    										<div class="appInfo inlineChilds">
			    										<div class="label"><span>Application: </span></div>
			    										<div class="text"><span>567</span></div>
		    										</div>
		    										<div class="versInfo inlineChilds  pull-right">
			    										<div class="label"><span>Version: </span></div>
			    										<div class="text"><span>98765</span></div>
			    									</div>
		    									</div>		    									
		    								</div>
		    								<div class="expander clps">
		    									<div class="expanderContent">
		    										<div class="beanBoxHolder">
						    							<div class="beanBox inline" style="width:130px;">
							    							<div class="header">
									    						<div class="inner">
									    							<div class="title">
									    								<span>Instances Running</span>
									    							</div>
									    						</div>
									    					</div>
									    					<div class="body">
						    									<div class="inner">
						    										<span>10</span>
						    									</div>
						    								</div>
							    						</div>
							    						<div class="beanBox inline" style="width:130px;">
							    							<div class="header">
									    						<div class="inner">
									    							<div class="title">
									    								<span>Max Instances</span>
									    							</div>
									    						</div>
									    					</div>
									    					<div class="body">
						    									<div class="inner">
						    										<span>26</span>
						    									</div>
						    								</div>
							    						</div>
							    					</div>
							    					<div class="beanBoxHolder">
							    						<div class="beanBox inline" style="width:130px;">
							    							<div class="header">
									    						<div class="inner">
									    							<div class="title">
									    								<span>Total Cost</span>
									    							</div>
									    						</div>
									    					</div>
									    					<div class="body">
						    									<div class="inner">
						    										<span>100</span><span> $</span>
						    									</div>
						    								</div>
							    						</div>
							    						<div class="beanBox inline" style="width:130px;">
							    							<div class="header">
									    						<div class="inner">
									    							<div class="title">
									    								<span>Cost/h</span>
									    							</div>
									    						</div>
									    					</div>
									    					<div class="body">
						    									<div class="inner">
						    										<span>7</span><span> $</span>
						    									</div>
						    								</div>
							    						</div>
							    					</div>	    										
		    									</div>	    										
		    									<div class="expanderControl">
		    										<div class="button">
		    											<span class="expand clickable">More Details</span>
		    											<span class="collapse clickable">Less Details</span>
		    										</div>
		    									</div>   								
		    								</div>
		    								<div class="separator"></div>	
		    							</div>
		    							<div class="wellContentHolder">
		    							</div>
		    						</div>
		    					</div>
		    				</div>
		    				<div>
			    				<div class="pane contentBox" style="width: 330px;">
			    					<div class="header">
			    						<div class="inner">
			    							<div class="title">
			    								<span>Current Expenses</span>
			    								<span class= "noDisplay"> Cost info / total money i have spent until now </span>
			    							</div>
			    						</div>
			    					</div>
			    					<div class="body">
			    						<div class="inner beanBoxHolder">
			    							<div class="beanBox inline" style="width:150px;">
				    							<div class="header">
						    						<div class="inner">
						    							<div class="title">
						    								<span>Total Expenses</span>
						    							</div>
						    						</div>
						    					</div>
						    					<div class="body">
			    									<div class="inner">
			    										<span>355</span><span> $</span>
			    									</div>
			    								</div>
				    						</div>
				    						<div class="beanBox inline" style="width:150px;">
				    							<div class="header">
						    						<div class="inner">
						    							<div class="title">
						    								<span>Expenses per Hr</span>
						    							</div>
						    						</div>
						    					</div>
						    					<div class="body">
			    									<div class="inner">
			    										<span>7</span><span> $/H</span>
			    									</div>
			    								</div>
				    						</div>
			    						</div>
			    					</div>
			    				</div>
			    				<div class="pane contentBox" style="width: 330px;">
			    					<div class="header">
			    						<div class="inner">
			    							<div class="title">
			    								<span>Current Running</span>
			    							</div>
			    						</div>
			    					</div>
			    					<div class="body">
			    						<div class="inner beanBoxHolder">
			    							<div class="beanBox inline" style="width:150px;">
				    							<div class="header">
						    						<div class="inner">
						    							<div class="title">
						    								<span>Total Deployments</span>
						    							</div>
						    						</div>
						    					</div>
						    					<div class="body">
			    									<div class="inner">
			    										<span>1</span>
			    									</div>
			    								</div>
				    						</div>
				    						<div class="beanBox inline" style="width:150px;">
				    							<div class="header">
						    						<div class="inner">
						    							<div class="title">
						    								<span>Total Instances</span>
						    							</div>
						    						</div>
						    					</div>
						    					<div class="body">
			    									<div class="inner">
			    										<span>10</span>
			    									</div>
			    								</div>
				    						</div>
			    						</div>
			    					</div>
			    				</div>
		    				</div>		    					    				
	    				</div>
				    </div>
				    <div class="configPane">
					    <div class="togglerBar">
					    	<div class="togglerIco">
					    	
					    	</div>
					    </div>
					     <div class="config">
					    </div>
				    </div>			    		
			   </div>
	    	</div>
	    </div>
    </div>
</body>
</html>
