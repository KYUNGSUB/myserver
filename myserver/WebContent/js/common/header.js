/**
 * 
 */
var submenu = null;

var headInit = function() {
	var loginLink = document.getElementById("submenu").children;
	//loginLink[0].onclick = loginClicked;

	var gnbArea = document.getElementById("gnb");
	var menuItemAll = gnbArea.getElementsByTagName("li");
	var menuItem = new Array();
	var currentItem, prevItem;
	
	for(i=0;i<menuItemAll.length;i++){
		if((menuItemAll[i].className).indexOf("item") > -1){
			menuItem.push(menuItemAll[i]);
		}
	}
	
	for(i=0;i<menuItem.length;i++){
		var link = menuItem[i].getElementsByTagName("a")[0];
		var layer = menuItem[i].getElementsByTagName("div")[0];
		var subLinks = menuItem[i].getElementsByTagName("div")[0].getElementsByTagName("a");

		link.onmouseover = layer.onmouseover = link.onfocus = function(e){
			currentItem = this.parentNode;
			if(prevItem){
				removeClass(prevItem,"on");
			}
			addClass(currentItem,"on");
			prevItem = currentItem;
		}
		link.onmouseout = layer.onmouseout = function(e){
			removeClass(this.parentNode,"on");
		}
		link.onkeydown = function(e){
			if (event.shiftKey && event.keyCode == 9){
				removeClass(this.parentNode,"on");
			}
		}
		for(j=0;j<subLinks.length;j++){
			subLinks[subLinks.length-1].onblur = function(e){
				removeClass(this.parentNode.parentNode.parentNode.parentNode,"on");
			}
		}
	}

	submenu = document.getElementsByClassName("sitem");
	var mid = sessionStorage.getItem("mid");
	var name = sessionStorage.getItem("name");
	if(mid != null) {	// 세션이 유지되는 상태. 즉, 로그인된 상태
		submenu[3].innerHTML = name;
		sessionOnActivatingState();
	}
	else {
		submenu[3].innerHTML = "MyPage";
		sessionNotActivatingState();
	}
}

// 액션
function addClass(ele, cls){
	var eleCln = ele.className;
	if(eleCln.indexOf(cls) == -1){
		ele.className = eleCln + " " + cls;
	}
}

function removeClass(ele, cls){
	var eleCln = ele.className;
	ele.className = eleCln.split(" " + cls)[0];
}

function sessionOnActivatingState() {
	removeClass(submenu[0], "on");	// Login
	addClass(submenu[1], "on");		// Logout
	removeClass(submenu[2], "on");	// Join
	addClass(submenu[3], "on");		// my page
}

function sessionNotActivatingState() {
	addClass(submenu[0], "on");		// Login
	removeClass(submenu[1], "on");	// Logout
	addClass(submenu[2], "on");		// Join
	removeClass(submenu[3], "on");	// my page
}

/*
 * 헤어의 sub menu에 있는 Login/Logout 버튼을 세션의 상태에 따라 제어 하기 위하여
 */
var loginClicked = function() {
	var loginLink = document.getElementById("submenu").children;
	var conPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	if(loginLink[0].innerHTML == "Login") {	// 세션이 설정되지 않은 경우
		window.location = conPath + "/member/login.jsp";
	}
	else {	// logout 수행
		window.location = conPath + "/member/logout.jsp";
	}
}