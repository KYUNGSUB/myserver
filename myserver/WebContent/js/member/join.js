/**
 * 
 */
var uid = null;
var pwd = null;
var cPwd = null;
var passId = false;
var passPw1 = false;
var passPw2 = false;
var passEmail = false;
var passValid = false;
var passEmailValid = false;

$(document).ready(function() {
	headInit();
	uid = document.getElementById("uid");
	pwd = document.getElementById("password");
	cpwd = document.getElementById("cPassword");
	uid.onkeyup = chkId;
	pwd.onkeyup = chkPwd1;
	cpwd.onkeyup = chkPwd2;
	email.onkeyup = chkEmail;
	var form = document.getElementsByTagName("form");
	form[0].onsubmit = chkParameter;
	var idCheck = document.getElementById("idCheck");
	idCheck.onclick = chkIdValid;
	var emailCheck = document.getElementById("emailCheck");
	emailCheck.onclick = chkEmailValid;
});

var chkIdValid = function() {
	// ajax
	if(passId == false) {
		alert("아이디를 올바르게 입력 하세요");
		passValid = false;
	}
	else {
		var query = {uid: $("#uid").val()};
		$.ajax({
			type: "POST",
			url: "/idCheck.do",
			data: query,
			success: function(data) {
				var str1 = '<p id="check">';
				var loc = data.indexOf(str1);
				var len = str1.length;
				var check = data.substr(loc + len, 1);
				if(check == "t") {	// id 존재
					alert("이미 존재하는 아이디 입니다.");
					passValid = false;
				}
				else {
					alert("사용 가능한 아이디 입니다.");
					passValid = true;
				}
			}
		});
	}
}

var chkEmailValid = function() {
	// ajax
	if(passEmail == false) {
		alert("이메일을 올바르게 입력 하세요");
		passEmailValid = false;
	}
	else if(passValid != true) {
		alert("아이디 중복확인을 먼저 하세요");
		passEmailValid = false;
	}
	else {
		var query = {email: $("#email").val()};
		$.ajax({
			type: "POST",
			url: "/emailCheck.do",
			data: query,
			success: function(data) {
				var str1 = '<p id="check">';
				var loc = data.indexOf(str1);
				var len = str1.length;
				var check = data.substr(loc + len, 1);
				if(check == "t") {	// id 존재
					alert("이미 존재하는 이메일 입니다.");
					passEmailValid = false;
				}
				else {
					alert("사용 가능한 이메일 입니다.");
					passEmailValid = true;
				}
			}
		});
	}
}

var chkParameter = function() {
	var name = document.getElementById("name");

	if(passId == false) {
		alert("아이디를 올바르게 입력 하세요");
		uid.focus();
		return false;
	}
	else if(passPw1 == false) {
		alert("비밀번호를 올바르게 입력하세요.");
		pwd.focus();
		return false;
	}
	else if(passPw2 == false) {
		alert("비밀번호를 올바르게 입력하세요.");
		cpwd.focus();
		return false;
	}
	else if(passValid == false) {
		alert("아이디 중복확인을 해야 합니다.");
		return false;
	}
	else if(name.value==null || name.value=="") {
		alert("이름을 입력하세요");
		return false;
	}
	else if(pwd.value != cpwd.value) {
		alert("비밀번호 확인을 해 주십시요.");
		cpwd.focus();
		return false;
	}
	else if(passEmailValid == false) {
		alert("이메일 중복확인을 해야 합니다.");
		return false;
	}
	else {
		return true;
	}
}

var chkId = function() {
	var idReg = /^(?=.*[a-zA-Z])(?=.*[0-9]).{5,20}$/;
	var image = document.getElementById("idimg");
	if( !idReg.test(uid.value) ) {
		image.src = "../images/member/eye127.png";
		uid.style.color = "#EE5656";
		passId = false;
	}
	else {
		image.src = "../images/member/user7.png";
		uid.style.color = "blue";
		passId = true;
	}
}

var chkPwd1 = function() {
	var pwReg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{7,16}$/;
	var image = document.getElementsByClassName("pwimg");
	if( !pwReg.test(pwd.value) ) {
		image[0].src = "../images/member/eye127.png";
		pwd.style.color = "#EE5656";
		passPw1 = false;
	}
	else {
		image[0].src = "../images/member/user7.png";
		pwd.style.color = "blue";
		passPw1 = true;
	}
}

var chkPwd2 = function() {
	var pwReg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{7,16}$/;
	var image = document.getElementsByClassName("pwimg");
	if( !pwReg.test(cpwd.value) ) {
		image[1].src = "../images/member/eye127.png";
		cpwd.style.color = "#EE5656";
		passPw2 = false;
	}
	else {
		image[1].src = "../images/member/user7.png";
		cpwd.style.color = "blue";
		passPw2 = true;
	}
}

var chkEmail = function() {
	var emailReg = /^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$/;
	var image = document.getElementById("emailimg");
	if( !emailReg.test(email.value) ) {
		image.src = "../images/member/eye127.png";
		email.style.color = "#EE5656";
		passEmail = false;
	}
	else {
		image.src = "../images/member/user7.png";
		email.style.color = "blue";
		passEmail = true;
	}
}