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