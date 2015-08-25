/*
 * 	Validates that the IS service is accessible from the web browser
 * 	In rare cases a mis-configuration may occur where the client point to localhost
 * 	while the actual IS server is located elsewhere.
 * 	In these cases we assume that IS server is located at the same host as the
 * 	Client's webapp. This script update the reference url and informs the webapp
 * 	backend for the change.
 */
var isServerValidation = function() {
	// Check if isserver url points to local host	
	if(isserver.indexOf("localhost") >= 0 || isserver.indexOf("127.0.0.1") >= 0)
	{
		// While Client's webapp url points elsewhere
		if(wcserver.indexOf("localhost") <= 0 && wcserver.indexOf("127.0.0.1") <= 0)
		{	
			// Create the correct url
			var parser = document.createElement('a');
			parser.href = isserver;
			if (parser.host == "") {
				parser.href = parser.href;
			}			
			parser.hostname = window.location.hostname;
			
			// Set the global variable
			isserver = parser.protocol + "//" + parser.host + parser.pathname;
			
			// Inform the backend scripts
			
			/*
			$.ajax({
				async:false,
			      cache: false,
			      type: 'get',
			      dataype: "json",
			      url: isserver + '/rest/deployment/recent',
			      success: function(data) {
			        if (data != 'alive') {
			        	console.log('yes')
			        } else {
			        	console.log('no')
			        }
			      }
			    });
			*/
		}
	}
}

/*
 * 'Guesses' the css with of a string, when this will be display through a browser	
 * 
 * 	Originally inspired by http://stackoverflow.com/a/5047712/2279200
 */
String.prototype.rendered_width = function(font) {
	  var f = font || '12px arial',
	      o = $('<div>' + this + '</div>')
	            .css({'position': 'absolute', 'float': 'left', 'white-space': 'nowrap', 'visibility': 'hidden', 'font': f})
	            .appendTo($('body')),
	      w = o.width();
	
	  o.remove();
	
	  return w;
	}

/*
*	Content Loading Indicaton
*	UI - 
*	Logic - 
*/
var contentLoading_set = function(enable)
{
    if(enable)
    {
        $('html').addClass('loading');
        $('.loadingIndicator').removeClass('noDisplay');
    }
    else
    {
        $('html').removeClass('loading');
        $('.loadingIndicator').addClass('noDisplay');
    }
}
var contentLoading_get = function()
{
    return $('html').hasClass('loading');
}

/*
*	Notification(s)
*	UI - Show a notification alert according the object received
*	Logic - 
*   	data['code'] -> for Notification Severity Indicator {error, warning, info, success}
*   	data['msg'] -> message
*/
function showNotification(data, duration) {

	var notificationContainer = $(".globalNotification");
    var notification = notificationContainer.find(".ntf");
    var notificationMessage = notification.find(".ntf_message");

    // Set Notification Severity Indicator
    switch(data['code']) {
	    case 1:
	    	notification.addClass('error');
	        break;
	    case 2:
	    	notification.addClass('warning');
	        break;
	    case 3:
	    	notification.addClass('info');
	        break;
	    case 4:
	    	notification.addClass('success');
	        break;
	    default:
	    	console.log('Invailid Notification Severity Indicator');
	        return;
    }
    
    // Set Notification Message
    notificationMessage.html(data['msg']);

    //Show notification
    notificationContainer.removeClass('noDisplay');

    // Set Notification width ~equal to Notification Message width
    msg_width = data['msg'].rendered_width('sans-serif');
    var width = msg_width + parseInt(notificationMessage.css("padding-left")) + parseInt(notificationMessage.css("padding-right"));
    notification.css({width:width});
    
    // Set notification timeout delay
    window.setTimeout(function () {
        clearNotification();
    }, duration);
}

/*
 * Clears the Notification
 */
function clearNotification() {
	var notificationContainer = $(".globalNotification");
	var notification = notificationContainer.find(".ntf");
    var notificationMessage = notification.find(".ntf_message");
    
    notificationContainer.fadeOut("slow", function() {
    	// Hide Notification
        notificationContainer.addClass("noDisplay");
        // Remove 'fadeOut' leftovers
        notificationContainer.removeAttr("style");        
        // Remove Notification Message
        notificationMessage.html('');
        // Hide Notification Severity Indicator / Class
        notification.removeClass("success");
        notification.removeClass("info");
        notification.removeClass("error");
        notification.removeClass("warning");
        // Remove Positioning leftovers
        notification.removeAttr("style");  
	});
}







