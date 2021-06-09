//update
function updatePopupOpen(idx, tableName) {
   $.ajax({
      type: "GET",
      url: "/ajaxtable.do",
      data: "table=" + tableName,
      success: function(tableData) {
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

function updateData(idx, tableHeader, tableBody, tableName) {

   str = "<form>";
   str = "<div id=\"updatePopup\" class=\"layerpop\" method=\"post\">";

   for (var n in tableBody) {
      for (var k in tableHeader) {
         if (idx == tableBody[n][tableHeader[0].columnname]) {

            str += "<label>" + tableHeader[k].columnname + "</label>";

            if ('idx' == tableHeader[k].columnname) {
               str += "<input type='" + tableHeader[k].columntype + "' name= '" + tableHeader[k].columnname + "' value= '" + tableBody[n][tableHeader[k].columnname] + "' disabled /><br>";
            } else if ('Date' == tableHeader[k].columntype) {

               var targetStr = tableBody[n][tableHeader[k].columnname];
               var calendarB = /\d{4}-\d{2}-\d{2}/.exec(targetStr);
               var calendarA = calendarB[0];

               str += "<input type='" + tableHeader[k].columntype + "' name= '" + tableHeader[k].columnname + "' value= '" + calendarA + "'/><br>";
            } else {
               str += "<input type='" + tableHeader[k].columntype + "' name= '" + tableHeader[k].columnname + "' value= '" + tableBody[n][tableHeader[k].columnname] + "'/><br>";
            }
         }
      }
   };
   str += "<button type=\"button\" id=\"popupClose\" onclick=\"popUpClose()\">close</button>";
   str += "<button type=\"button\" onclick=\"checkAll(" + idx + ", '" + tableName + "')\">update</button>";
   str += "</div>";
   str += "</form>";


   $("#Update_Form").html(str);

   $('.layerpop').css("background", "white");
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

};

function popUpClose() {
   $("#updatePopup").remove();
};


function checkAll(idx,tableName) {

   var param = $('#Update_Form').serializeArray();
   for (var i = 0; i < param.length; i++) {
      var param_name = param[i].name;
      var param_value = param[i].value;

      //공통 벨리데이션
      if (param_value == "" || param_value == null) {
         alert(param_name + "를(을) 입력해주세요!");
         return false;
      }
      if (param_name == "user_email") {
         if (!checkUserEmail(Update_Form.user_email.value)) {
            return false;
         }
      } 

      //개별 벨리데이션
      if (param_name == "user_id") {
         if (!checkUserId(Update_Form.user_id.value)) {
            return false;
         }
      } else if (param_name == "user_pw") {
         if (!checkUserPw(Update_Form.user_pw.value)) {
            return false;
         }
      } else if (param_name == "user_name") {
         if (!checkUserName(Update_Form.user_name.value)) {
            return false;
         }
      
      }

   }
   
   //update 수행
   userUpdate(idx,tableName);

   //아이디확인 함
   function checkUserId(id) {
      var idRegExp = /^[a-zA-z0-9]{4,12}$/; //아이디 유효성 검사
      if (!idRegExp.test(id)) {
         alert("아이디는 영문 대소문자와 숫자 4~12자리로 입력해야합니다!");
         return false;
      }
      return true; //확인이 완료되었을 때
   };

   //비밀번호확인 함수
   function checkUserPw(pw) {

      var pwRegExp = /^[a-zA-z0-9]{4,12}$/; //비밀번호 유효성 검사
      if (!pwRegExp.test(pw)) {
         alert("비밀번호는 영문 대소문자와 숫자 4~12자리로 입력해야합니다!");
         return false;
      }

      //아이디와 비밀번호가 같을 때

      if (Update_Form.user_id.value == pw) {
         alert("아이디와 비밀번호는 같을 수 없습니다!");
         return false;
      }
      return true; //확인이 완료되었을 때
   };

   //이름확인 함수
   function checkUserName(name) {
      var nameRegExp = /^[가-힣]{2,5}$/;
      if (!nameRegExp.test(name)) {
         alert("이름이 올바르지 않습니다.");
         return false;
      }
      return true; //확인이 완료되었을 때
   }

   //이메일확인 함수
   function checkUserEmail(mail) {
      var emailRegExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;
      if (!emailRegExp.test(mail)) {
         alert("이메일 형식이 올바르지 않습니다!");
         return false;
      }
      return true; //확인이 완료되었을 때
   }
};

// update 수행
function userUpdate(idx,tableName){
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	
	var updateValue = $("#Update_Form").serializeObject();
	updateValue.tableName = tableName;
	updateValue.idx=Update_Form.idx.value;
	console.log(updateValue);
	
	$.ajax({
	      type: "POST",
	      url: "/ajaxUpdate",
	      data: JSON.stringify(updateValue),
	      contentType: "application/json",
	      success: function (tName) {
	    	  console.log(tName);
	    	  $("#updatePopup").remove();
	      }
	});
};