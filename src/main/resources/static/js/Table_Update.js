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
	/*	str += "<button type=\"submit\" id=\"popupUpdate\" onclick=\"userUpdate(" + idx + ")\">update</button>";*/
	str += "<button type=\"button\" onclick=\"checkAll()\">update</button>";
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


function checkAll() {

	/*	console.log($("#Update_Form").serializeArray().length);*/

	if ($("#Update_Form").serializeArray().length == 6) {

		if (!checkUserId(Update_Form.user_id.value)) {
			return false;
		} else if (!checkUserPw(Update_Form.user_pw.value)) {
			return false;
		} else if (!checkUserName(Update_Form.user_name.value)) {
			return false;
		} else if (!checkUserEmail(Update_Form.user_email.value)) {
			return false;
		}
		return true;
	} else if ($("#Update_Form").serializeArray().length == 4) {

		if (!checkTitle(Update_Form.title.value)) {
			return false;
		} else if (!checkContant(Update_Form.contant.value)) {
			return false;
		} else if (!checkWriter(Update_Form.writer.value)) {
			return false;
		}
		return true;
	}


	// 공백확인 함수
	function checkExistData(value, dataName) {
		if (value == "") {
			alert(dataName + " 입력해주세요!");
			return false;
		}
		return true;
	}

	//아이디확인 함수
	function checkUserId(id) {
		//id가 입력되었는지 확인
		if (!checkExistData(id, "아이디를"))
			return false;

		var idRegExp = /^[a-zA-z0-9]{4,12}$/; //아이디 유효성 검사
		if (!idRegExp.test(id)) {
			alert("아이디는 영문 대소문자와 숫자 4~12자리로 입력해야합니다!");
			return false;
		}
		return true; //확인이 완료되었을 때
	};

	//비밀번호확인 함수
	function checkUserPw(pw) {
		//pw가 입력되었는지 확인
		if (!checkExistData(pw, "비밀번호를"))
			return false;

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
		if (!checkExistData(name, "이름을"))
			return false;

		var nameRegExp = /^[가-힣]{2,5}$/;
		if (!nameRegExp.test(name)) {
			alert("이름이 올바르지 않습니다.");
			return false;
		}
		return true; //확인이 완료되었을 때
	}

	//이메일확인 함수
	function checkUserEmail(mail) {
		//mail이 입력되었는지 확인하기
		if (!checkExistData(mail, "이메일을"))
			return false;

		var emailRegExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;
		if (!emailRegExp.test(mail)) {
			alert("이메일 형식이 올바르지 않습니다!");
			form.mail.value = "";
			form.mail.focus();
			return false;
		}
		return true; //확인이 완료되었을 때
	}
	//타이틀확인 함수
	function checkTitle(title) {
		if (!checkExistData(title, "타이틀을"))
			return false;
		return true; //확인이 완료되었을 때
	}
	//내용확인 함수
	function checkContant(contant) {
		if (!checkExistData(contant, "내용을"))
			return false;

		return true; //확인이 완료되었을 때
	}

	//이름확인 함수
	function checkWriter(writer) {
		if (!checkExistData(writer, "이름을"))
			return false;
		return true; //확인이 완료되었을 때
	}
};