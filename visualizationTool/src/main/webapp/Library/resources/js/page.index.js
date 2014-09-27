$(document).ready(function(){
     $.each($('.paneHolder .pane'), function(){
    	 var objHeight = 0;
    	 $.each($(this).children(), function(){
            objHeight += $(this).height();
    	 });
    	 $(this).height(objHeight);
     });
     
     
     jQuery.ajax({
 		type: 'get',
 		dataype: "json",
 		url: isserver + '/rest/application/deployments/recent',
 		success: recentDeployments
 	});
     
});


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
	        	 var thisPane = $(this).closest('.pane');
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
	   	var thisPane = itemHolder.closest('.pane');
	   	var objHeight = 0;
	   	$.each(thisPane.children(), function(){
	          objHeight += $(this).height();
	   	});
	   	thisPane.height(objHeight);
	});
}
