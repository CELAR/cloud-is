
var DEBUG_AJAX = true;
var DELAY_AJAX_ERROR = 2000; // 2s
/*
 * Send AJAX request with json data, using
 */
var ajaxRequest = function(url, method, data, dataType, successCallback, errorCallback, callbackParams) {
    //var fullHost = window.location.host;
    //url = "http://" + fullHost + "/" + FOLDER + url;

    //window.previewing = 1;
    //setContentLoading(true);
	contentLoading_set(true);

    $.ajax({
        url : url,
        data: data,
        type: method,
        dataType: dataType,
        cache: true,        
        statusCode: {
            404: function(responseObject, textStatus, jqXHR) {
            	printConsoleInfo(responseObject, textStatus, url);
            	setNotification(1, responseObject['responseText']);
            },
            500: function(result, textStatus, errorThrown) {
            	printConsoleInfo(responseObject, textStatus, url);
            	setNotification(0, responseObject['responseText']);
            },
            503: function(result, textStatus, errorThrown) {
            	printConsoleInfo(responseObject, textStatus, url);
            	setNotification(0, responseObject['responseText']);
            }           
        }
    })
    .done(function(result, textStatus, jqXHR) {
        // run successCallback function, if any
        if (typeof successCallback == 'function') {
            successCallback.call(undefined, result, callbackParams);
        }
    })
    .fail(function(result, textStatus, errorThrown, callbackParams){
        // run errorCallback function, if any
        if (typeof errorCallback == 'function') {
            errorCallback.call(undefined, result, callbackParams);
        }
        else
        {
        	setNotification(0, 'Ajax Call Failed to return results');
            console.log(url + " -- Failed to return results -- "  + result);
        }
    })
    .always(function(jqXHR, textStatus) {
        //window.previewing = 0;
        //setContentLoading(false);
    	contentLoading_set(false);
    });
    // Ensure that 'animation' will close
    //contentLoading_set(false);
};


var printConsoleInfo = function(responseObject, textStatus, url) {
	if(DEBUG_AJAX)
		console.log("[" + textStatus +"] " + 
				"[" + responseObject['status'] + ": " + responseObject['statusText'] +"] " + 
				responseObject['responseText'] + " " +
				"( " + url + " )"		
		);
}

var setNotification = function(code, msg) {
	var errObject = new Object();
    errObject['code'] = code;
    errObject['msg'] = msg;
    // Display Ajax error
    showNotification(errObject, DELAY_AJAX_ERROR);
}




