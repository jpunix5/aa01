//view Table
function createtable(tableName) {
	$.ajax({
		type: "GET",
		url: "/viewtable",
		data : "table=" + tableName,
		success: function (tableData) {
			//table column data
			var tableHeader = tableData.tableinfo;
			//table row_list data
			var tableBody = tableData.users;
			//this table_name
			var tableName = tableHeader[0].tablename;

			//table_header column info
			TableHeader(tableHeader);
			
			//table_body 
			TableBody(tableHeader, tableBody, tableName);
			
			//insert_form
			InsertForm(tableHeader, tableName);
		}
	});
};

function TableHeader(tableHeader) {
	str = "<tr>";
	for (var i in tableHeader) {
		str += "<th>" + tableHeader[i].columnname + "</th>";
	}
	str += "</tr>";
	
	$("#Table_Header").html(str);
};

function TableBody(tableHeader, tableBody, tableName) {
	var str = "";
	
	for (var n in tableBody) {
		str += "<tr>";
		for (var k in tableHeader) {
			if('Date' == tableHeader[k].columntype){
				var targetStr = tableBody[n][k];
				console.log(targetStr);
				var date = new Date(targetStr);
				console.log(date);
//				var calendarB = /\d{4}-\d{2}-\d{2}/.exec(targetStr);
//				var calendarA = new date(targetStr * 1000);
//				console.log(calendarA);	
				str += "<td>" + date + "</td>";
			}else{
				str += "<td>" + tableBody[n][k] + "</td>";
			}
		};
		str += "<td><button type=\"button\" id=\"updatebutton\" onclick=\"updatePopupOpen(" + tableBody[n][0] +  ", '" + tableName + "' )\">update</button></td>"
		str += "<td><button type=\"button\" id=\"deletebutton\" onclick=\"deletethis(" + tableBody[n][0] + ", '" + tableName + "' )\">delete</button></td>"
        str += "</tr>";
	};
	
	$("#Table_Body").html(str);
};

function InsertForm(tableHeader, tableName){
	var strbt = "";
	
	for (var k in tableHeader) {
		if('idx' == tableHeader[k].columnname){
			strbt += "<td><input type=" + tableHeader[k].columntype + " placeholder=" + tableHeader[k].columnname + " name=" + tableHeader[k].columnname + " hidden ></input></td>";
		}else{
			strbt += "<td><input type=" + tableHeader[k].columntype + " placeholder=" + tableHeader[k].columnname + " name=" + tableHeader[k].columnname + "></input></td>";
		}
	};

	strbt += "<td><button type=\"button\" onclick=\"userCreate('" + tableName + "')\">save</button></td>"

	$("#Insert_Form").html(strbt);
};