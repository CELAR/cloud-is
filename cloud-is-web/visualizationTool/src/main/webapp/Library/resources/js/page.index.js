$(document).ready(function(){
     $.each($('.paneHolder .tile[data-adjust="tileHeight"]'), function(){
    	 var objHeight = 0;
    	 $.each($(this).children(), function(){
            objHeight += $(this).height();
    	 });
    	 $(this).height(objHeight);
     });
     
     /*
      * Fill up Head Bar
      */
     setHeadBarBeanInfo('[data-type="runningInstances"]', "6$");
     setHeadBarBeanInfo('[data-type="currentCost"]', "7$ / H");
     setHeadBarBeanInfo('[data-type="applications"]', "4");
     setHeadBarBeanInfo('[data-type="deployments"]', "12");
     
     
     
     /*
      * Fill up Running Deployments
      */
     ajaxRequest(
    	isserver + '/rest/deployment/recent',
        'get',
        null,
        'json',
        recentDeployments,
		function(jsonObj) {
			// error
			
		},
        null
    );
    /*     
    jQuery.ajax({
 		type: 'get',
 		dataype: "json",
 		url: isserver + '/rest/deployment/recent',
 		success: recentDeployments
 	});
     */
     
     /*
      * Fill up Latest Actions
      */
     
     
     /*
      * Fill up Latest (Stopped) Deployments
      */
     
});

var setHeadBarBeanInfo = function(selector, value) {
	var insertedValue;
	if(value == null || value == '')
		insertedValue = '--';
	else
		insertedValue = value;
	
	$(selector).find('span[data-type="value"]').html(insertedValue);
}

var recentDeployments = function(jsonObj) {
	//jsonObj = $.parseJSON(jsonObj);
	var well = $('.rcDeployments .well');
	var itemHolder = well.find('.wellContentHolder');
	var itemTemplate = well.find('.wellItemTemplate');
	
	$.each(jsonObj, function(index, deployment) {
		var item = itemTemplate.clone();
		item.removeClass('wellItemTemplate');
		item.addClass('wellItem');
		//
		item.find('.statusBar > .status > span').html(deployment.status);
		item.find('.statusBar > .status').addClass(deployment.status);
		item.find('.title > .value > span').html(deployment.id);
		item.find('.versInfo > .text > span').html(deployment.version);
		item.find('.appInfo > .text > span').html(deployment.application);		
  	    	 
    	var baseUrl = "/application/version/deployment/";
    	var deplID = deployment.id;
    	if(deployment.status == 'online')
    		var tab = "monitoring";
    	else
    		var tab = "analysis";    		
    	var url = wcserver + baseUrl + "?deplID=" + deplID + "&tab=" + tab;
    	 
    	var btn = item.find('[data-type="button"][data-name="inspect"]');
    	btn.off('click');
    	btn.on('click', function() {
    		 window.open(url, '_blank');    		 
    	});
    	
    	if(deployment.status == 'online')
    	{
	    	item.find('.expander > .expanderControl .button').off('click');
	    	item.find('.expander > .expanderControl .button').on('click', function() {
	        	 $(this).closest('.expander').toggleClass('clps').toggleClass('expd');
	         
	        	 //Resize pane
	        	 var thisPane = $(this).closest('.tile[data-adjust="tileHeight"]');
	        	 var objHeight = 0;
	        	 $.each(thisPane.children(), function(){
	        		 objHeight += $(this).height();
	        	 });
	        	 thisPane.height(objHeight);
	        });
    	}
    	else
    	{
    		item.find('.expander').remove();
    	}
		//
		itemHolder.append(item);
		//Resize pane
	   	var thisPane = itemHolder.closest('.tile[data-adjust="tileHeight"]');
	   	var objHeight = 0;
	   	$.each(thisPane.children(), function(){
	          objHeight += $(this).height();
	   	});
	   	thisPane.height(objHeight);
	});
}
