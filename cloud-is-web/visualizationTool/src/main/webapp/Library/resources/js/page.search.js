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
		
		$('input[name="submitted_start_dp"]').attr('disabled', true);
		$('input[name="submitted_start"]').val(Math.round(new Date($('input[name="submitted_start_dp"]').val()).getTime()/1000));
		$('input[name="submitted_end_dp"]').attr('disabled', true);
		$('input[name="submitted_end"]').val(Math.round(new Date($('input[name="submitted_end_dp"]').val()).getTime()/1000));
		
		$.ajax({
			type: 'get',
			dataype: "application/json",
			url: isserver + '/rest/application/search',
			data:$(this).serialize(),
			success: function(jsonResponse) {
				var resultsPool = $('.singleTabber > .searchResultsPanel > .resultsPool');
				$.each(jsonResponse, function(key, sigObj){
					
					var context = $('.singleTabber > .searchResultsPanel');
					var wellHolder = context.find('.well > .wellContentHolder');
					var wellItem = context.find('.well > .wellItemTemplate').clone();				
					// remove unwanted classes
					wellItem.removeClass('noDisplay');
					wellItem.removeClass('wellItemTemplate');				
					// Fill item properties
					wellItem.find('span[data-name="appId"]').html(sigObj.id);
					wellItem.find('span[data-name="appDescription"]').html(sigObj.description);
					wellItem.find('span[data-name="sumbited"]').html(sigObj.submitted);
					wellItem.find('span[data-name="version"]').html(sigObj.version);
					
					
					
					// Append item to well
					wellHolder.append(wellItem);					
				})
				
				// Alter UI to display the results
				$('.searchResultsPanel > .promptHolder').addClass('noDisplay');				
				$('.searchResultsPanel > .resultsHolder').removeClass('noDisplay');
				
				// Set Subtitle
				$('.searchResultsPanel .titleBar > .subtitle > span').html('Application / Version Information');
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
