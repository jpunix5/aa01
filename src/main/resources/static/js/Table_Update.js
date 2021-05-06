//update
function updatePopupOpen(idx, tableName){
	$.ajax({
		type: "GET",
		url: "/ajaxtable.do",
		data : "table=" + tableName,
		success: function (tableData) {
			//table column data
			var tableHeader = tableData.tableinfo;
			//table row_list data
			var tableBody = tableData.users;
			//this table_name
			var tableName = tableHeader[0].tablename;

			//this row update
			updateData(idx, tableHeader, tableBody, tableName);
		}
	});
};

function updateData(idx, tableHeader, tableBody, tableName){
	//-----------------------------------------------
	str = "<div id=\"updatePopup\" class=\"layerpop\">";
	//str += "<div class=\"updatePopuptitle\" text=\"" + idx + "번 User update Popup\"></div>";
	
	for (var n in tableBody) {
		for (var k in tableHeader) {		
			if(idx == tableBody[n][tableHeader[0].columnname]){
				
				str += "<label>" + tableHeader[k].columnname + "</label>";
				
				if('idx' == tableHeader[k].columnname){
					str += "<input type='" + tableHeader[k].columntype + "' value= '" + tableBody[n][tableHeader[k].columnname] + "' disabled /><br>";
				}else if('Date' == tableHeader[k].columntype){
					
					var targetStr = tableBody[n][tableHeader[k].columnname];
						var calendarB = /\d{4}-\d{2}-\d{2}/.exec(targetStr);
						var calendarA = calendarB[0];
						
					str += "<input type='" + tableHeader[k].columntype + "' value= '" + calendarA + "'/><br>";
				}else{
					str += "<input type='" + tableHeader[k].columntype + "' value= '" + tableBody[n][tableHeader[k].columnname] + "'/><br>";
				}
			}
		}
	};
	str += "<button type=\"button\" id=\"popupClose\" onclick=\"popUpClose()\">close</button>";
	str += "<button type=\"button\" id=\"popupUpdate\" onclick=\"userUpdate(" + idx + ")\">update</button>";
	str += "</div>";
	//-----------------------------------------------
	$("#Update_Form").html(str);
	//-----------------------------------------------
	$('.layerpop').css("background", "green");
	$('.layerpop').css("position", "absolute");
	$('.layerpop').css(
			"top",
			(($(window).height() - $('.layerpop')
					.outerHeight()) / 2)
					+ $(window).scrollTop());
	$('.layerpop').css(
			"left",
			(($(window).width() - $('.layerpop')
					.outerWidth()) / 2)
					+ $(window).scrollLeft());
	$("#updatePopup").show();
	//-----------------------------------------------
};

function popUpClose(){
	$("#updatePopup").remove();
};

function userUpdate(idx){	
	$("#updatePopup").remove();
};