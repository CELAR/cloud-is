<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>CELAR IS | Search</title>
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
	<link href="<%=request.getContextPath()%>/Library/resources/css/ext/jquery-ui-timepicker-addon.css" rel="stylesheet">
	<link rel="stylesheet" media="all" type="text/css" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />	
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.tabber.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/util.ui.form.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.outer.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/webapp.shared.inner.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/Library/resources/css/page.search.css" rel="stylesheet">
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/ui/1.10.4/jquery-ui.min.js"></script>
	<script type="application/javascript" src="<%=request.getContextPath()%>/Library/resources/js/util.ajax.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/ext/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/util.ui.tabber.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/Library/resources/js/page.search.js">	</script>
   
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
	    <div class="pageMainContainer background light search">
	    	<div class="contentMainHolder background lighter">
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
												<div class="searchParamsGroup">
													<div class="groupTitle">
														<span>Application Metadata Parameters</span>
													</div>
													<div class="groupContent">
														<div class="formRow"><label>Description</label><input name="description" data-type="string" type="text"/></div>
													</div>
						    					</div>		    						
												<div class="searchParamsGroup">
													<div class="groupTitle">
														<span>Specify "Submittion Time" Range</span>
													</div>
													<div class="groupContent">
							    						<div class="inputGroup">
									    					<div class="formRow"><label>Start Point (date/time)</label><input name="submitted_start_dp" data-type="long" type="text"/>
									    						<input name="submitted_start" data-type="long" type="hidden"/></div>
									    					<div class="formRow"><label>End Point (date/time)</label><input name="submitted_end_dp" data-type="long" type="text"/>
									    						<input name="submitted_end" data-type="long" type="hidden"/></div>
														</div>
													</div>
												</div>	    					
												<div class="searchParamsGroup">
													<div class="groupTitle">
														<span>Specify Topology Parameters</span>
													</div>
													<div class="groupContent">
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
						    			<div class="contextNotifier noDisplay"><span class="prompt">Search In: </span><span class="value"></span></div>
						    			<form name="deploymentSearchForm" method="post">
						    				<input type="hidden" name="application_id">
						    				<div class="formContent">
						    					<div class="appIdRow noDisplay">
						    						<span class="title">Application ID:</span>
						    						<span class="title" data-name="appId"></span>
						    						<span class="closeXBtn clickable" data-button="clearAppIdParam">
						    							<img src="<%=request.getContextPath()%>/Library/media/closeX_1.svg"></span>
				    							</div>
						    					<div class="searchParamsGroup">
													<div class="groupTitle">
														<span>Specify Time</span>
													</div>
													<div class="groupContent">
														<div class="inputGroup">
									    					<div class="formRow"><label>Start Point (date/time)</label><input name="start_time_dp" data-type="long" type="text"/>
									    						<input name="start_time" data-type="long" type="hidden"/></div>
									    					<div class="formRow"><label>End Point (date/time)</label><input name="end_time_dp" data-type="long" type="text"/>
									    						<input name="end_time" data-type="long" type="hidden"/></div>
														</div>
						    						</div>
						    					</div>						    					
						    					<div class="searchParamsGroup">
													<div class="groupTitle">
														<span>Specify Status Parameter</span>
													</div>
													<div class="groupContent">
														<div class="formRow"><label>Status</label>
															<!--<input name="status" data-type="string" type="text"/>-->
															<select name="status" data-type="string">
																<option value="" selected></option>
																<option value="Initializing">Initializing</option>
																<option value="Provisioning">Provisioning</option>
																<option value="Executing">Executing</option>
																<option value="SendingReports">SendingReports</option>
																<option value="Ready">Ready</option>
																<option value="Finalizing">Finalizing</option>
																<option value="Done">Done</option>
																<option value="Cancelled">Cancelled</option>
																<option value="Aborted">Aborted</option>
																<option value="Unknown">Unknown</option>
															</select>
														</div>
						    						</div>
						    					</div>
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
		    				<div class="filters optsMenu" style="float:right">
		    					<div class="mnTitle"><span>Group: </span></div>
		    					<div class="mnItem selected"><span class="clickable">none</span></div>
				    			<div class="mnItem"><span class="clickable">application</span></div>
		    				</div>
		    			</div>
		    			<div class="multiWell">
		    				<div class="resultsPool well raw noDisplay">
								<div class="wellItemTemplate wellItem sResAppRaw noDisplay">
									<div class="titleRow">
										<a class="spanify" data-ref="appId" href=""><span data-name="appDescription"></span></a>
									</div>
									<div class="infoRow">
										<div class="versInfo">
											<div><span>Application Version</span></div>
											<div><span class="clickable" data-name="appId"></span></div>
											<div class="versInfoExp noDisplay">
												<div><span>App UID: </span><span data-name="uid"></span></div>
												<div><span>Major</span><span data-name="vMajor"></span></div>
												<div><span>Minnor</span><span data-name="vMinnor"></span></div>
											</div>
										</div>								
										<div class="subInfo">
											<div><span>Submited On</span></div>
											<div><span data-name="sumbited"></span></div>
										</div>							
									</div>
									<div class="controlsRow">
										<div class="pull-right"><span class="clickable btnControl" data-role="button" data-action="searchDeployments">Search Deployments</span></div>
									</div>
								</div>
								<div class="wellContentHolder"></div>
			    			</div>
			    			<div class="resultsPool well appGrouped noDisplay">
								<div class="wellItemTemplate wellItem sResAppRaw noDisplay">
									<div class="titleRow">
										<a class="spanify" data-ref="appId" href=""><span data-name="appDescription"></span></a>
										<div class="compareSwitch"></div>
									</div>
									<div class="subVersions well">
										<div class="wellItemTemplate wellItem versionBean noDisplay">
											<div class="infoRow">
												<div class="versInfo">
													<div><span>Application Version</span></div>
													<div><span data-name="vMajor"></span><span data-name="vMinnor"></span></div>
												</div>								
												<div class="subInfo">
													<div><span>Submited On</span></div>
													<div><span data-name="sumbited"></span></div>
												</div>							
											</div>
											<div class="controlsRow">
												<div><span class="clickable" data-button="showDepl">Show Deployments</span></div>
												<div><span class="clickable" data-button="searchDepl">Search Deployments</span></div>
											</div>
										</div>										
									</div>
								</div>
								<div class="wellContentHolder"></div>
			    			</div>
			    			<div class="resultsPool well deployments noDisplay">
								<div class="wellItemTemplate wellItem noDisplay">
									<div class="titleRow">
										<span>Deployment: </span><a class="spanify" data-ref="deplId" href=""><span data-name="deploymentId"></span></a>
									</div>
									<div class="infoRow">
										<div class="">
											<div><span>Started  At</span></div>
											<div><span data-name="startTime"></span></div>
										</div>
										<div class="">
											<div><span>Terminated  At</span></div>
											<div><span data-name="endTime"></span></div>
										</div>
										<div class="">
											<div><span>Status</span></div>
											<div><span data-name="status"></span></div>
										</div>																
									</div>
								</div>
								<div class="wellContentHolder"></div>
			    			</div>
			    		</div>
			    		<div class="paginator background lighter"></div>
		    		</div>
		    	</div>
	    	</div>
	    </div>
    </div>
</body>
</html>
