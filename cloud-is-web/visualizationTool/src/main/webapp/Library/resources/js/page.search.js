var urlParams;
(window.onpopstate = function () {
    var match,
        pl     = /\+/g,  // Regex for replacing addition symbol with a space
        search = /([^&=]+)=?([^&]*)/g,
        decode = function (s) { return decodeURIComponent(s.replace(pl, " ")); },
        query  = window.location.search.substring(1);

    urlParams = {};
    while (match = search.exec(query))
       urlParams[decode(match[1])] = decode(match[2]);
})();

var validateDate = function(d)
{
	if ( Object.prototype.toString.call(d) === "[object Date]" ) {
		  // it is a date
		  if ( isNaN( d.getTime() ) ) {  // d.valueOf() could also work
		    // date is not valid
			  return false;
		  }
		  else {
		    // date is valid
			  return true;
		  }
		}
		else {
		  // not a date
			return false;
		}
};

var populateApplicationSearchResults = function(jsonData) {
	if ( jsonData.length == 0 ) {
		// Alter UI to hide the results
		$('.searchResultsPanel > .promptHolder').removeClass('noDisplay');				
		$('.searchResultsPanel > .resultsHolder').addClass('noDisplay');
        return;
    }
	
	var poolSelector = 'raw';
	$.each($('.multiWell > .resultsPool'), function(index, pool){
		if(!$(pool).hasClass(poolSelector))
			$(pool).addClass('noDisplay');
	});
	$('.multiWell').find('.' + poolSelector).removeClass('noDisplay');
	var context = $('.multiWell').find('.' + poolSelector);
	var wellHolder = context.find('.wellContentHolder');
	
	// Clear Result Holder
	wellHolder.empty();	
	$.each(jsonData, function(key, sigObj){
		var wellItem = context.find('.wellItemTemplate').clone();
			
		// remove unwanted classes
		wellItem.removeClass('noDisplay');
		wellItem.removeClass('wellItemTemplate');				
		// Fill item properties
		wellItem.find('span[data-name="appId"]').html(sigObj.id);
		var href = wcserver + '/application/version/' + '?appId=' + sigObj.id;
		wellItem.find('a[data-ref="appId"]').attr('href', href);
		wellItem.find('span[data-name="appDescription"]').html(sigObj.description);
		// Convert unix timestamp to readable time
		//	Math.floor is a tiny hack to ensure	that date is parsed correctly
	    var myDate = new Date(Math.floor(sigObj.sTime));	
		wellItem.find('span[data-name="sumbited"]').html(myDate.toLocaleString());
		wellItem.find('span[data-name="uid"]').html(sigObj.uid);
		wellItem.find('span[data-name="vMajor"]').html(sigObj.vMajor);
		wellItem.find('span[data-name="vMinnor"]').html(sigObj.vMinnor);
		
		// Assign events
		/*		
		wellItem.find('span[data-name="appId"]').off('click');
		wellItem.find('span[data-name="appId"]').on('click', function() {
			$(this).closest('.versInfo').find('.versInfoExp').toggleClass('noDisplay');
		});
		*/
		
		/*
		wellItem.find('span[data-button="showDepl"]').off('click');
		wellItem.find('span[data-button="showDepl"]').on('click', function() {
			window.location.href = wcserver + '/application/version/?appId=' + sigObj.id + '&tab=' + 'deployments';
		    return false;
		});
		*/
			
		wellItem.find('span[data-role="button"][data-action="searchDeployments"]').off('click');
		wellItem.find('span[data-role="button"][data-action="searchDeployments"]').on('click', function() {
			window.location.href = wcserver + '/search/?appId=' + sigObj.id;
		    return false;
		});
		
		// Append item to well
		wellHolder.append(wellItem);					
	})
	
	// Alter UI to display the results
	$('.searchResultsPanel > .promptHolder').addClass('noDisplay');				
	$('.searchResultsPanel > .resultsHolder').removeClass('noDisplay');
	
	// Set Subtitle
	$('.searchResultsPanel .titleBar > .subtitle > span').html('Application / Version Information');
};

var populateDeploymentSearchResults = function(jsonData) {
	if ( jsonData.length == 0 ) {
		// Alter UI to hide the results
		$('.searchResultsPanel > .promptHolder').removeClass('noDisplay');				
		$('.searchResultsPanel > .resultsHolder').addClass('noDisplay');
        return;
    }
	
	var poolSelector = 'deployments';
	$.each($('.multiWell > .resultsPool'), function(index, pool){
		
		console.log(pool)
		
		if(!$(pool).hasClass(poolSelector))
			$(pool).addClass('noDisplay');
	});
	$('.multiWell').find('.' + poolSelector).removeClass('noDisplay');
	var context = $('.multiWell').find('.' + poolSelector);
	var wellHolder = context.find('.wellContentHolder');
	
	// Clear Result Holder
	wellHolder.empty();	
	$.each(jsonData, function(key, sigObj){
		var wellItem = context.find('.wellItemTemplate').clone();
			
		// remove unwanted classes
		wellItem.removeClass('noDisplay');
		wellItem.removeClass('wellItemTemplate');				
		// Fill item properties
		wellItem.find('span[data-name="appId"]').html(sigObj.id);
		var href = wcserver + '/application/version/deployment/' + '?deplID=' + sigObj.id + '&tab=' + 'analysis';
		clean_url = href.replace(/([^:]\/)\/+/g, "$1");
		wellItem.find('a[data-ref="deplId"]').attr('href', clean_url);
		wellItem.find('span[data-name="deploymentId"]').html(sigObj.id);
		// -
		// Convert unix timestamp to readable time
		//	Math.floor is a tiny hack to ensure	that date is parsed correctly
	    var myDate = new Date(Math.floor(sigObj.sTime));	
		wellItem.find('span[data-name="startTime"]').html(myDate.toLocaleString());
		// -
		if(sigObj.eTime != null && sigObj.eTime != '') {
			// Convert unix timestamp to readable time
			//	Math.floor is a tiny hack to ensure	that date is parsed correctly
		    var myDate = new Date(Math.floor(sigObj.eTime));	
			wellItem.find('span[data-name="endTime"]').html(myDate.toLocaleString());
		}
		else
		{
			wellItem.find('span[data-name="endTime"]').html('--');
		}		
		// -
		wellItem.find('span[data-name="status"]').html(sigObj.status);
		
		// Assign events
		wellItem.find('span[data-name="appId"]').off('click');
		wellItem.find('span[data-name="appId"]').on('click', function() {
			
		});
		
		// Append item to well
		wellHolder.append(wellItem);					
	})
	
	// Alter UI to display the results
	$('.searchResultsPanel > .promptHolder').addClass('noDisplay');				
	$('.searchResultsPanel > .resultsHolder').removeClass('noDisplay');
	
	// Set Subtitle
	$('.searchResultsPanel .titleBar > .subtitle > span').html('Application / Version Information');
};

/**
 *	To search deployments for a specific application version
 *	a scripts alter the search control to insert application version
 *	id parameter and alter the UI.
 *	This function resets those change, and brings the 
 *	deployment search control to the initial state.
 */
var clearAppSpecificDeplSearch = function() {
	$('form[name="deploymentSearchForm"] span[data-name="appId"]').html('');
	$('form[name="deploymentSearchForm"] span[data-name="appId"]').closest('div').addClass('noDisplay');
	$('form[name="deploymentSearchForm"] input[name="appId"][type="hidden"]').val('');
	
	// TODO
	// Remove appId parameter from Browser url
	var state = wcserver + "/search/";
	window.history.pushState(Date.now(), "Title", state);
};

/**
 *	Catch ui.tabber tab changed event, in order
 *	To reset the search controls
 */
$(document).off('ui.tabber.change');
$(document).on('ui.tabber.change', function(ev, data) {
	if((data.tabberId == 'searchControl') && (data.targetId == 'version'))
	{
		clearAppSpecificDeplSearch();
	}
});


$(document).ready(function(){
	// Init tabber controls
	var myTabber = Object.create(ui.tabber);
	myTabber.init("searchControl", false);
	
	//-
	var tab;
	var appId = 0;
	if(("appId" in urlParams))
	{
		appId = urlParams.appId;
		// Open deployment tab
		// Hacky approach
		// TODO
		$('[data-tabber-ref="deployment"] > span').trigger('click');
		// Ingest appId to deployments search form
		$('form[name="deploymentSearchForm"] input[name="application_id"][type="hidden"]').val(appId);
		$('form[name="deploymentSearchForm"] span[data-name="appId"]').html(appId);
		$('form[name="deploymentSearchForm"] span[data-name="appId"]').closest('div').removeClass('noDisplay');
		
		// Set clear event
		$('form[name="deploymentSearchForm"] span[data-button="clearAppIdParam"]').off('click');
		$('form[name="deploymentSearchForm"] span[data-button="clearAppIdParam"]').on('click', clearAppSpecificDeplSearch);
	}
	
	
	//-
	$('input[name="submitted_start_dp"]').datetimepicker({
		timeFormat: 'HH:mm:ss',
	    beforeShow: function(input, inst)
	    {
	        //inst.dpDiv.css({marginTop: -input.offsetHeight + 'px', marginLeft: input.offsetWidth + 'px'});
	    	var rect = input.getBoundingClientRect();
	    	//console.log(rect.top, rect.right, rect.bottom, rect.left);
	    	inst.dpDiv.css({marginTop: rect.top /2  + 'px', marginLeft: input.offsetWidth + 'px'});
	    }
	});
	
	$('input[name="submitted_end_dp"]').datetimepicker({
		timeFormat: 'HH:mm:ss',
	    beforeShow: function(input, inst)
	    {
	        //inst.dpDiv.css({marginTop: -input.offsetHeight + 'px', marginLeft: input.offsetWidth + 'px'});
	    	var rect = input.getBoundingClientRect();
	    	//console.log(rect.top, rect.right, rect.bottom, rect.left);
	    	inst.dpDiv.css({marginTop: rect.top /2  + 'px', marginLeft: input.offsetWidth + 'px'});
	    }
	});
	
	//-
	
	$('input[name="start_time_dp"]').datetimepicker({
		timeFormat: 'HH:mm:ss',
	    beforeShow: function(input, inst)
	    {
	        //inst.dpDiv.css({marginTop: -input.offsetHeight + 'px', marginLeft: input.offsetWidth + 'px'});
	    	var rect = input.getBoundingClientRect();
	    	//console.log(rect.top, rect.right, rect.bottom, rect.left);
	    	inst.dpDiv.css({marginTop: rect.top /2  + 'px', marginLeft: input.offsetWidth + 'px'});
	    }
	});
	
	$('input[name="end_time_dp"]').datetimepicker({
		timeFormat: 'HH:mm:ss',
	    beforeShow: function(input, inst)
	    {
	        //inst.dpDiv.css({marginTop: -input.offsetHeight + 'px', marginLeft: input.offsetWidth + 'px'});
	    	var rect = input.getBoundingClientRect();
	    	//console.log(rect.top, rect.right, rect.bottom, rect.left);
	    	inst.dpDiv.css({marginTop: rect.top /2  + 'px', marginLeft: input.offsetWidth + 'px'});
	    }
	});
	
	
	// Set close result button event
	//$('.searchResultsPanel .resultsCloseBtn').off('click');
	//$('.searchResultsPanel .resultsCloseBtn').on('click', newSearch);
	
	
	
	$('form[name="versionSearchForm"]').submit(function() {
		// Parse date to unix time stamp
		// not parsing may break form serialization
		// submitted_start_dp
		$('input[name="submitted_start_dp"]').attr('disabled', true);
		d = new Date($('input[name="submitted_start_dp"]').val());
		if(validateDate(d))
			v = Math.round(d.getTime()/1000)
		else
			v ='';			
		$('input[name="submitted_start"]').val(v);
		
		// submitted_end_dp
		$('input[name="submitted_end_dp"]').attr('disabled', true);
		d = new Date($('input[name="submitted_end_dp"]').val());
		if(validateDate(d))
			v = Math.round(d.getTime()/1000)
		else
			v ='';			
		$('input[name="submitted_end"]').val(v);
		
		// Ajax Post form
		$.ajax({
			type: 'get',
			dataype: "application/json",
			url: isserver + '/rest/application/search',
			data:$(this).serialize(),
			success: function(jsonResponse) {
				populateApplicationSearchResults(jsonResponse);				
			}
		});	 
		
		
		$('input[name="submitted_start_dp"]').attr('disabled', false);
		$('input[name="submitted_end_dp"]').attr('disabled', false);
		return false;
	});
	
	$('form[name="deploymentSearchForm"]').submit(function(){
		// Parse date to unix time stamp
		// not parsing may break form serialization
		
		$('input[name="submitted_start_dp"]').attr('disabled', true);
		d = new Date($('input[name="submitted_start_dp"]').val());
		if(validateDate(d))
			v = Math.round(d.getTime()/1000)
		else
			v ='';			
		$('input[name="submitted_start"]').val(v);
		
		// start_time_dp
		$('input[name="start_time_dp"]').attr('disabled', true);
		d = new Date($('input[name="start_time_dp"]').val());
		if(validateDate(d))
			v = Math.round(d.getTime()/1000)
		else
			v ='';			
		$('input[name="start_time"]').val(v);
		
		// end_time_dp
		$('input[name="end_time_dp"]').attr('disabled', true);
		d = new Date($('input[name="end_time_dp"]').val());
		if(validateDate(d))
			v = Math.round(d.getTime()/1000)
		else
			v ='';			
		$('input[name="end_time"]').val(v);
		
		// Ajax Post form
		$.ajax({
			type: 'get',
			dataype: "application/json",
			url: isserver + '/rest/deployment/search',
			data:$(this).serialize(),
			success: function(jsonResponse) {
				console.log(jsonResponse);
				populateDeploymentSearchResults(jsonResponse);				
			}
		});
		
		
		$('input[name="start_time_dp"]').attr('disabled', false);
		$('input[name="end_time_dp"]').attr('disabled', false);
		return false;
	});
})
