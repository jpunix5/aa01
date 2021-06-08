//row delete
function deletethis(idx, table) {
	var targetidx = idx;
	
	$.ajax({		
		type: "GET",
		url : "/deleteTable", 
		data : "table=" + table + "&" + "idx=" + targetidx,
		success: function () {
			
		}
		
	});
	window.location.reload();
}