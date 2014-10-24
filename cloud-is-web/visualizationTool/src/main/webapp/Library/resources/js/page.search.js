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

var populateSearchResults = function(jsonData) {
	var resultsPool = $('.searchResultsPanel > .resultsPool');
	if ( jsonData.length == 0 ) {
		// Alter UI to hide the results
		$('.searchResultsPanel > .promptHolder').removeClass('noDisplay');				
		$('.searchResultsPanel > .resultsHolder').addClass('noDisplay');
        return;
    }
	// Clear Result Holder
	$('.searchResultsPanel .well > .wellContentHolder').empty();	
	$.each(jsonData, function(key, sigObj){
		var context = $('.searchResultsPanel');
		var wellHolder = context.find('.well > .wellContentHolder');
		var wellItem = context.find('.well > .wellItemTemplate').clone();
			
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
		wellItem.find('span[data-name="appId"]').off('click');
		wellItem.find('span[data-name="appId"]').on('click', function() {
			$(this).closest('.versInfo').find('.versInfoExp').toggleClass('noDisplay');
		});
		/*		
		wellItem.find('span[data-name="appId"]').off('click');
		wellItem.find('span[data-name="appId"]').on('click', function() {
			$(this).closest('.versInfo').find('.versInfoExp').toggleClass('noDisplay');
		});
		*/
		// Append item to well
		wellHolder.append(wellItem);					
	})
	
	// Alter UI to display the results
	$('.searchResultsPanel > .promptHolder').addClass('noDisplay');				
	$('.searchResultsPanel > .resultsHolder').removeClass('noDisplay');
	
	// Set Subtitle
	$('.searchResultsPanel .titleBar > .subtitle > span').html('Application / Version Information');
};

$(document).ready(function(){
	// Init tabber controls
	var myTabber = Object.create(ui.tabber);
	myTabber.init("searchControl", false);
	
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
	
	
	
	$('form[name="versionSearchForm"]').submit(function(){
		console.log($(this).serialize());
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
				populateSearchResults(jsonResponse);				
			}
		});	 
		/*
		 
		 $.ajax({
						type: form.attr('method'),
						url: form.attr('action'),
						data: form.serialize(),
						success: function(jsonResponse) {
							statusUpdate_parseResponse(jsonResponse); 
						}				
					});	 
		 */
		$('input[name="submitted_start_dp"]').attr('disabled', false);
		$('input[name="submitted_end_dp"]').attr('disabled', false);
		return false;
	});
	
	$('form[name="deploymentSearchForm"]').submit(function(){
		console.log($(this).serialize());
		
		$('input[name="start_time_dp"]').attr('disabled', true);
		$('input[name="start_time"]').val(Math.round(new Date($('input[name="start_time_dp"]').val()).getTime()/1000));
		$('input[name="end_time_dp"]').attr('disabled', true);
		$('input[name="end_time"]').val(Math.round(new Date($('input[name="end_time_dp"]').val()).getTime()/1000));
		
		$('input[name="start_time_dp"]').attr('disabled', false);
		$('input[name="end_time_dp"]').attr('disabled', false);
		return false;
	});
})
