//view Table
	function createtable(tableName, nowPageNo, perPageCn) {
		$.ajax({
			type: "GET",
			url: "/viewtable",
			data : "table=" + tableName + "&" + "nowPageNo=" + nowPageNo + "&" + "perPageCn=" + perPageCn,
			success: function (tableData) {
				//table column data
				var tableHeader = tableData.tableinfo;
				//table row_list data
				var tableBody = tableData.users;
				//this table_name
				var tableName = tableHeader[0].tablename;
				//paging parameter
				var totalCN = tableData.totalCN; //전체 레코드 수
				var perPage = tableData.perPageCn; //페이지당 레코드 수
				var pageSize = tableData.pageSize; //블록당 페이지 수 pageObject의 pageSize에서 수정가능 현재는 3.
				
				var totalPage = parseInt((totalCN-1)/perPage)+1;    //페이지의 총 개수
				
				var firstPage = (parseInt((nowPageNo-1)/pageSize))*pageSize+1;    //블록의 첫번째 페이지를 가리키는 변수
				
				var lastPage =  firstPage+pageSize-1;  //블록의 마지막페이지를 가리키는 변수
				if(lastPage>totalPage){
					lastPage=totalPage;
				}
				
				var hasPreviousPage=0; //이전페이지 유무. 기본은 false
				if(firstPage!==1){		//fisrtPage가 1이 아닌경우 == 블록의 2번째 이상인 경우 임으로 
					hasPreviousPage=1; //true가 되도록 설정
				}
				
				var hasNextPage=0;	//이후 페이지 유무. 기본은 false
				if(lastPage*perPageCn<totalCN){ //nowPage와 lastPage를 비교하지 않은 이유는 마지막블록에서 lastPage가 아닌 다른페이지를 골랐을때도 뒷블록으로 갈수있는 li태그 생성을 피하기위함
					hasNextPage=1;
				}
				
				
				//table_header column info
				TableHeader(tableHeader);
				
				//table_body 
				TableBody(tableHeader, tableBody, tableName);
				
				//insert_form
				InsertForm(tableHeader, tableName);
				
				//pageing process
				str = "";
				if(hasPreviousPage==true){ //1페이지로
					str += "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick=\"createtable('" + tableName + "', 1, " + perPage + ")\"><<</a></li>" //<<1페이지로 가는 li
					str += "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick=\"createtable('" + tableName + "', '" + (firstPage-1) + "', " + perPage + ")\"><</a></li>" //이전 블록으로 가는 li. ex)456에서 이걸누르면 123이 나옴
				}
				
	            for(var i=firstPage; i<=lastPage; i++){ //페이지를 설정하는 구간
	            	
	            	if(i==nowPageNo){
	            		str += "<li class=\"page-item\" id=\"pageLi"+i+"\"><a class=\"page-link\" href=\"#\" style=\"color:red\" onclick=\"createtable('" + tableName + "', " + i + ", " + perPage + ")\">" + i + "</a></li>"
	            	} else {
	            		str += "<li class=\"page-item\" id=\"pageLi"+i+"\"><a class=\"page-link\" href=\"#\" onclick=\"createtable('" + tableName + "', " + i + ", " + perPage + ")\">" + i + "</a></li>"
	            	}
	            }
	            
	            if(hasNextPage==true){ //마지막페이지로
	            	str += "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick=\"createtable('" + tableName + "', '" + (lastPage+1) + "', " + perPage + ")\">></a></li>" //다음 블록으로 가는 li. ex)456에서 789
	            	str += "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick=\"createtable('" + tableName + "', '" + totalPage + "', " + perPage + ")\">>></a></li>" //마지막페이지로가는 li
	            } 
	            
	            $("#page-num").html(str);
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
			}else if(tableHeader[k].columnname == "user_email"){
			strbt += "<td><input type='text' id='email'></input></td>";
			strbt += "<td>@</td>";
			strbt += "<td><input type='text' id='domain' value= ''></input></td>";
			strbt += "<td><select id='selectEmail'>"
			strbt += "<option value='1' selected>직접입력</option>"
			strbt += "<option value='naver.com' >네이버</option>"
			strbt += "<option value='gmail.com'>구글</option>"		
			strbt += "</select></td>";

//			strbt += "<td><customtag id='selectEmail' display='직접입력,네이버,구글' domain='1,naver.com,gmail.com'></customtag></td>";

			strbt += "<td><input type=" + tableHeader[k].columntype + " id=" + tableHeader[k].columnname + " name=" + tableHeader[k].columnname + " hidden></input></td>";
			}else{
				strbt += "<td><input type=" + tableHeader[k].columntype + " placeholder=" + tableHeader[k].columnname + " name=" + tableHeader[k].columnname + "></input></td>";
			}
		};
		
		strbt += "<td><button type=\"button\" onclick=\"userCreate('" + tableName + "')\">save</button></td>";
		
		var strbt2 = "";
		strbt2 += "<td><select name=\"페이지당 글개수\" onchange=\"hyper_select(this,'"+tableName+"')\">";  //드롭다운 리스트 생성구간 hyper_select()함수 실행. this,tablename이 있는데, this는 select태그의 option을 전부 가져간다. 
		strbt2 += "<option value=\"0\" selected>줄선택</option>";
		strbt2 += "<option value=\"3\">3줄씩</option>";
		strbt2 += "<option value=\"5\">5줄씩</option>";
		strbt2 += "<option value=\"10\">10줄씩</option>";
		strbt2 += "</select></td>";
	
		$("#Insert_Form").html(strbt);
		$("#lineSelect").html(strbt2);
		
		$("#selectEmail").change(function(){
			$("#selectEmail option:selected").each(function () {
						if($(this).val()== '1'){ 						//직접입력일 경우
							 $("#domain").val('');                 //값 초기화
							 $("#domain").attr("disabled",false); 	//활성화
						}else{ 											//직접입력이 아닐경우
							 $("#domain").val($(this).val());     //선택값 입력
							 $("#domain").attr("disabled",true); 	//비활성화
						}
				   });

		});
		
				
		$("button").focus(function(){
			var my_email = "";
				my_email += document.getElementById("email").value;
				my_email += '@';
				my_email += document.getElementById("domain").value;
			
			$("#user_email").val(my_email); 			
		});
	};
	
	function hyper_select(optional,tableName) {  //위에서 쓰일 hyper_select 함수.  여기선 위에서 받아온 option전부와 tableName을 가져왔다.
	    createtable(tableName,1,optional.options[optional.selectedIndex].value); //createtable함수를 실행한다. 순서대로 테이블이름, 현재페이지넘버, 페이지당 레코드개수.
	    //마지막 optional.options[optional.selectedIndex].value은 받아온 optional(select태그의 자식전부)중. option's'태그들'중'에 선택된 option의 value를 가져온다.
	}