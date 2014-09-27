<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>CELAR IS | Search</title>
	<!--bootstrap script -->
	<script type="text/javascript">
		var isserver = "<%=pageContext.findAttribute("isserver")%>";	
		
		var wcserver = window.location.protocol + "//" + window.location.host + "/";	
		var path = window.location.pathname;
		var dPath = "<%=pageContext.findAttribute("vspath")%>"; // path after the server address showing the webaoo root
		if(path.indexOf(dPath) >= 0)
		{
			wcserver += dPath;
		}
		
		//var wcserver = "http://localhost:8080/webClient";	
	</script>
	<link href="<%=request.getContextPath()%>/Library/resources/js/ext/jquery-ui-timepicker-addon.css" rel="stylesheet">
	<link rel="stylesheet" media="all" type="text/css" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />	
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.tabber.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.form.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.outer.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.inner.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/page.search.css" rel="stylesheet">
	
	<script src="http://code.jquery.com/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/ui/1.10.4/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/ext/jquery-ui-timepicker-addon.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/script.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/util.ui.tabber.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/page.search.js">	</script>
   
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
	    			Search
	    		</div>
	    	</div>
    	</div>
	    <div class="navbar pageNavMenu">
	      <div class="navbar-inner">
	        <div class="nav pull-left">
	        	<div class="navTile selected">
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
	        </div>
	      </div>
	    </div>
	    <div class="pageMainContainer search">
	    	<div class="contentMainHolder">
	    		 <div class="side">
			    	 <div class="inner">
				    	<div class="searchControl">
			    			<div class="titleBar">
			    				<div class="title"><span>App Version / Deployment Search</span></div>
			    			</div>
			    			<div class="content tabber" data-tabber-id="searchControl">
					    		<div class="tabberNav" data-tabber-nav=true data-tabber-parentId="searchControl">
					    			<div class="navTile selected" style="width: 50%;" data-tabber-ref="version" data-tabber-navTile=true ><span>App Version</span></div>
					    			<div class="navTile" style="width: 50%;" data-tabber-ref="deployment" data-tabber-navTile><span>Deployment</span></div>
					    		</div>
					    		<div class="tabberPagesWhapper" data-tabber-pages=true data-tabber-parentId="searchControl">
						    		<div data-tabber-page=true data-tabber-pageId="version">
						    			<form name="versionSearchForm" method="post">
						    				<div class="formContent">
						    					<div>
													<div class="formRow"><label>description</label><input name="description" data-type="string" type="text"/></div>
												</div>					    						
												<div>
													<div>
														<div>
														</div>
														<div>
															<span>Specify "Submittion Time"</span>
														</div>
													</div>
													<div>
							    						<div class="inputGroup">
									    					<div class="formRow"><label>submitted_start</label><input name="submitted_start" data-type="long" type="text"/></div>
									    					<div class="formRow"><label>submitted_end</label><input name="submitted_end" data-type="long" type="text"/></div>
														</div>
													</div>
												</div>	    					
												<div>
													<div>
														<div>
														</div>
														<div>
															<span>Specify Topology Parameters</span>
														</div>
													</div>
													<div>
														<div class="formRow"><label>Module Name</label><input name="module_name" data-type="string" type="text"/></div>
														<div class="formRow"><label>Component Description</label><input name="component_description" data-type="string" type="text"/></div>
						    						</div>
						    					</div>												
						    				</div>
						    				<div class="formControls">
						    					<div><button>Search</button></div>
						    				</div>
										</form>
						    		</div>
						    		<div data-tabber-page=true data-tabber-pageId="deployment" class="noDisplay">
						    			<div class="contextNotigier noDisplay"><span class="prompt">Search In: </span><span class="value"></span></div>
						    			<form name="deploymentSearchForm" method="post">
						    				<input type="hidden" name="appUUID">
						    				<div class="formContent">
								    			<div>
							    					<div class="formRow"><label>start_time</label><input name="start_time" data-type="long" type="text"/></div>
							    					<div class="formRow"><label>end_time</label><input name="end_time" data-type="long" type="text"/></div>
												</div>
												<div class="formRow"><label>status</label><input name="status" data-type="string" type="text"/></div>
											</div>
						    				<div class="formControls">
						    					<div><button type="submit">Search</button></div>
						    				</div>
										</form>
						    		</div>
					    		</div>
				    		</div>
				    	</div>
			    	</div>	    		   	
		    	</div>
    			<div class="searchResultsPanel">
    				<div class="promptHolder">
    					<span>No Results to Display! </span>
    				</div>
    				<div class="resultsHolder noDisplay">
		    			<div class="titleBar">		    				
		    				<div class="">
		    					<div class="title"><span>Search Results</span></div>
		    					<div class="subtitle"><span></span></div>
		    				</div>
		    			</div>
		    			<div class="resultsPool well">
							<div class="wellItemTemplate wellItem noDisplay">
								<div><span>Application Id: </span><span data-name="appId"></span></div>
								<div class="noDisplay"><span>App Description</span><span data-name="appDescription"></span></div>
								<div><span>Version:  </span><span data-name="version"></span></div>
								<div><span>Submited On: </span><span data-name="sumbited"></span></div>
							</div>
							<div class="wellContentHolder"></div>
		    			</div>
		    		</div>
		    	</div>
	    	</div>
	    </div>
    </div>
</body>
</html>
