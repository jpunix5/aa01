//row insert
function userCreate(tableName) {
	var user = $("#Insert_Form").serialize();
//	var user = $("#Insert_Form").serializeObject();
//	var user = $("#Insert_Form").serializeArray();
	
//	console.log("111===============");
//	console.log(typeof user);
//	console.log("===============");
//	console.log(user);
//	console.log("111===============");
	
//	if('test_users' == tableName){
//		urladd = "/ajaxwrite.do";
//	}else if('test_board' == tableName){
//		urladd = "/ajaxwriteboard.do";
//	};
	
	$.ajax({
		type: "GET",
		url: "/ajaxInsert",
//		url: urladd,
		data : user,
		success: function (tableName) {
			console.log(tableName);
		}
//		, error : function(request,status,error){
//			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
//		}
		
	});
};