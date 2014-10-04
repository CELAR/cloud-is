$(window).on('resize', function() {
    var md = $('body > .page .versionInfoHolder');
    var h = parseInt(md.height());
    // Set the left content new height var top = 0;    
    var tp = md.find('.header');
    var top = parseInt(tp.height());
    
    //var topbar = parseInt(md.find('.right > .topbar').height());
    nh = h - top;
    $('.versionInfoHolder .pagesContainer').height(nh.toString());
    
});



$('document').ready(function ()
{
	// Init tabber controls
	var myTabber = Object.create(ui.tabber);
	myTabber.init("innerNavMenu", false);
	
	
	// Init parameters from helber
	var appID = $('.pageHelper').find('span[data-appID]').html();
	
	console.log("isserver: " + isserver);
	console.log("wcserver: " + wcserver);
	
	// Define event function
	var versWellItemEv = {
			onSimpleClick : function(event) {
				wellItem = $(this);
				
				wellHolder = wellItem.closest('.wellContentHolder');
				// Remove Previously
				wellHolder.find('.wellItem').each(function(){
					$(this).removeClass('selected');					
				});
				// Add Selected
				wellItem.addClass('selected');
				// TODO 
				// Clear and the .versionInfoHolder
				var versionInfoHolder = $('.versionInfoHolder');
				
				var data = Object();
				data['appId'] = '0';
				data['versId'] = wellItem.find('span[data-name="versId"]').html();				
				
				// Populate version description
				jQuery.ajax({
					type: 'get',
					url: wcserver + '/ajax/application/info',
					data: data,
					dataType: "xml", 
					success: function (responseText) {
						var svgDoc = responseText;
						//document.write(xmlDoc.xml);
						
						//import contents of the svg document into this document
						var importedSVGRootElement = document.importNode(svgDoc.documentElement,true);
						//append the imported SVG root element to the appropriate HTML element
						//  $("#svg").append(importedSVGRootElement);
						
						var context = $('#versDescription');
						//context.find('.appTopology').html(xmlDoc.xml);
						context.find('.appTopology').html(importedSVGRootElement);
						//console.log(context.find('.appTopology'));
						//console.log(xmlDoc);
					}
				});
				
				
				
				// Populate Version Overview
				buildOverviewPane();				
				
				// Populate Performance Overview
				buildPerformanceAnalysisPane();
				
				// Populate Performance Overview
				buildDeploymentsPane();
				
				// if needs toggle placeHolder
				if(!versionInfoHolder.find('.placeholder').hasClass('noDisplay'))
				{
					versionInfoHolder.find('.placeholder').addClass('noDisplay');
					versionInfoHolder.find('.placeholder').siblings('div').removeClass('noDisplay');
				}
				// Fix height
				$(window).trigger('resize');
			},
			onCompareSelect : function(event) {
				$(this).addClass("toCompare");
			}			
	};
	
	
	// Get user applications
	/*
	jQuery.ajax({
		type: 'get',
		dataype: "json",
		url: isserver + '/rest/application/',
		success: function(jsonObj) {
			if(jQuery.type(jsonObj) === "string")
				jsonObj = eval(jsonObj);
			jQuery.each(jsonObj, function (index, app)
	        {
				var context = $('.applicationsHolder');
				var wellHolder = context.find('.well > .wellContentHolder');
				var wellItem = context.find('.well > .wellItemTemplate').clone();				
				// remove unwanted classes
				wellItem.removeClass('noDisplay');
				wellItem.removeClass('wellItemTemplate');				
				// Fill item properties
				wellItem.find('span[data-name="appId"]').html(index);
				wellItem.find('span[data-name="appName"]').html(app.appName);
				wellItem.find('span[data-name="sumbited"]').html(app.sumbited);
				
				// Add events
				wellItem.on('click', function() {
					// Add Selected
					wellHolder.find('.wellItem').each(function() {
						$(this).removeClass('selected');
						wellItem.addClass('selected');
						// Empty the holder first
						$('.versionsHolder .well > .wellContentHolder').html('');
					});
				});
				});
				
				// Append item to well
				wellHolder.append(wellItem);

			}
            });
					
		*/
					
	jQuery.ajax({
		type: 'get',
		dataype: "json",
		url: isserver + '/rest/application/' + appID +'/version',
		success: function(jsonObj) {
			var holder = $(".dashboard");
			
			
			if(jQuery.type(jsonObj) === "string")
				jsonObj = eval(jsonObj);
			jQuery.each(jsonObj, function (index, vers)
	        {
				var context = $('.versionsHolder');
				var wellHolder = context.find('.well > .wellContentHolder');
				var wellItem = context.find('.well > .wellItemTemplate').clone();				
				// remove unwanted classes
				wellItem.removeClass('noDisplay');
				wellItem.removeClass('wellItemTemplate');				
				// Fill item properties
				wellItem.find('span[data-name="versId"]').html(vers.versId);
				wellItem.find('span[data-name="versName"]').html(vers.versName);
				wellItem.find('span[data-name="sumbited"]').html(vers.sumbited);
				
				// Add events
				wellItem.on('click', versWellItemEv.onSimpleClick);
				
				// Append item to well
				wellHolder.append(wellItem);
	        });
		}
	});		
				
		
	
	if ($('.onoffswitch > .onoffswitch-checkbox').is(':checked'))
	{
		// dirty hack to reset check box onload
		console.log("onoffswitch::reset");
		$('.onoffswitch > .onoffswitch-checkbox').trigger('click');
	}
	$('.onoffswitch > .onoffswitch-checkbox').change(function(){
		var chBx = $(this);
		var context = $('.versionsHolder');
		var wellHolder = context.find('.well > .wellContentHolder');
	    // $this will contain a reference to the checkbox   
	    if (chBx.is(':checked')) {
	        // the checkbox was checked 
	    	console.log("onoffswitch::checked");	    		    	
	    	wellHolder.find('.wellItem').off('click').on('click', versWellItemEv.onCompareSelect);
	    	

			$('[data-btn="compare"]').addClass("clickable");
			$('[data-btn="compare"]').on('click', function() {
				///application/version/compare
				var form = $("<form/>");
				form.attr('action', wcserver + '/application/version/compare');
				form.attr('method', 'post');
				
				var versions = "";
				wellHolder.find('.wellItem').each(function(){
					if($(this).hasClass('toCompare'))
					{
						versions +=  $(this).find('span[data-name="versId"]').html() + ",";
					}
				});
				versions.slice(0,-1);
				
				$('<input/>').attr('type', 'hidden').attr('name', 'appID').val('').appendTo(form);
				$('<input/>').attr('type', 'hidden').attr('name', 'versID').val(versions).appendTo(form);

				console.log(form);
				form.appendTo('body').submit();
			});
	    } else {
	        // the checkbox was unchecked
	    	console.log("onoffswitch::unchecked");
	    	wellHolder.find('.wellItem').each(function() {	    		
	    		$(this).off('click');
	    		$(this).on('click', versWellItemEv.onSimpleClick);
	    	});
	    	$('[data-btn="compare"]').removeClass("clickable");
			$('[data-btn="compare"]').off('click');
	    }
	});
	
	
	
});