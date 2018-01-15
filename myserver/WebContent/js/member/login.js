/**
 * 
 */
var passId = false;
var passPw = false;
var uid = null;
var pwd = null;

window.onload = function() {
	headInit();
	uid = document.getElementById("uid");
	if(uid.value != "") {
		chkId();
	}
	pwd = document.getElementById("pw");
	var form = document.getElementsByTagName("form");
	uid.onkeyup = chkId;
	pwd.onkeyup = chkPw;
	form[0].onsubmit = chkP;
}

var chkId = function() {
	var idReg = /^(?=.*[a-zA-Z])(?=.*[0-9]).{5,20}$/;
	var idLabel = document.getElementById("idLabel");
	if( !idReg.test(uid.value) ) {
		uid.style.color = "#EE5656";
		idLabel.style.color = "#EE5656";
		idLabel.innerHTML = "id는 영문과 숫자의 조합으로 8자 이상(중복 비허용)을 입력하세요";
		passId = false;
	}
	else {
		var image = document.getElementById("idimg");
		image.src = "../images/member/user7.png";
		uid.style.color = "blue";
		idLabel.style.color = "blue";
		idLabel.innerHTML = "사용 가능한 id 입니다.";
		passId = true;
	}
}

var chkPw = function() {
//	var pw = document.getElementById("pw");
	var pwReg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{7,16}$/;
	var pwLabel = document.getElementById("pwLabel");
	if( !pwReg.test(pwd.value) ) {
		pw.style.color = "#EE5656";
		pwLabel.style.color = "#EE5656";
		pwLabel.innerHTML = "암호는 영문자와 1자 이상의 숫자특수문자. 8~16";
		passPw = false;
	}
	else {
		var image = document.getElementById("pwimg");
		image.src = "../images/member/user7.png";
		pw.style.color = "blue";
		pwLabel.style.color = "blue";
		pwLabel.innerHTML = "사용 가능한 암호 입니다.";
		passPw = true;
	}
}

var chkP = function() {
	if(passId == true && passPw == true) {
		return true;
	}
	else if(passId == false){
		alert("입력값을 확인하십시요.");
		id.focus();
		return false;
	}
	else if(passPw == false) {
		alert("입력값을 확인하십시요.");
		pwd.focus();
		return false;
	}
}