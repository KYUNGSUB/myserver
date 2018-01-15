/**
 * 
 */
window.onload = function() {
	var form = document.getElementsByTagName("form");
	form[0].onsubmit = checkAgree;
}

var checkAgree = function() {
	var check1 = document.getElementsByName("terms1");
	var check2 = document.getElementsByName("terms2");
	if(check1[0].checked==true && check2[0].checked==true) {
		return true;
	}
	else if(check1[0].checked == false) {
		alert("이용약관에 동의하셔야 합니다.");
		return false;
	}
	else if(check2[0].checked == false) {
		alert("개인정보 수집 및 이용에 동의하셔야 합니다.");
		return false;
	}
}