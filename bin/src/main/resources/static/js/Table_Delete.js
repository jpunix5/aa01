//row delete
function deletethis(idx, table) {
	var targetidx = idx;
	
	if('test_users' == table){
		urladd = "/ajaxdelete.do";
	}else if('test_board' == table){
		urladd = "/ajaxdeleteboard.do";
	};
	
	$.ajax({		
		type: "GET",
		url: urladd,
		data : "idx=" + targetidx,
		success: function () {
		}
		
	});
}