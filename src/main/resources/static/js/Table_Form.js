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
	str = "<tr class=\"bg-test01 text-test01\">";
	for (var i in tableHeader) {
		str += "<th>" + tableHeader[i].columnname + "</th>";
	}
	str += "</tr>";
	
	$("#Table_Header").html(str);
};

function TableBody(tableHeader, tableBody, tableName) {
	var str = "";
	
	for (var n in tableBody) {
		str += "<tr class=\"bg-test02\">";
		for (var k in tableHeader) {
			if('Date' == tableHeader[k].columntype){
				var targetStr = tableBody[n][k];
				var date = new Date(targetStr);
				var dateformat = date.getFullYear()+"년"+(date.getMonth()+1)+"월"+date.getDate()+"일"
				
				str += "<td>" + dateformat + "</td>";
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