//row insert
function userCreate(tableName) {
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	
	var user = $("#Insert_Form").serializeObject();

	user.tableName = tableName;
	
	$.ajax({
		url : "/ajaxInsert",
		type : "post",
		data: JSON.stringify(user,tableName),
		contentType: "application/json",
		success: function (tableName) {
			window.location.reload();
		}		 
	});
				
};