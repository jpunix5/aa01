//row insert
function userCreate(tableName) {
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	
//	var user = $("#Insert_Form");
//	var user = $("#Insert_Form").serialize();
	var user = $("#Insert_Form").serializeObject();
//	var user = $("#Insert_Form").serializeArray();
//	var user = {"id" : "ddd", "pw" : "1234"};
	user.tableName = tableName;
	
	$.ajax({
		url : "/ajaxInsert",
		type : "post",
//		data : user,
//		data: JSON.stringify(user)+ "&" + "table=" + tableName,
		data: JSON.stringify(user,tableName),
		contentType: "application/json",
		success: function (tableName) {
			console.log(tableName);
		}
//		, error : function(request,status,error){
//			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
//		}
		
	});
};