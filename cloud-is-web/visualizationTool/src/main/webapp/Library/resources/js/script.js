$('document').ready(function ()
{	
	var form = $('#testForm');
	form.submit(function () {
		$.ajax({
			type: form.attr('method'),
			url: form.attr('action'),
			data: form.serialize(),
			success: function(jsonObj){
				console.log(jsonObj);
			}
		});	 
		return false;
	});
});