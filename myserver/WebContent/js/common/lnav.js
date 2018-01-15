/** s/n/n-*.jsp : n th (1, 2, 5)
 *  boardId 1~4 -> 3, boardId 5~7 -> 4
 *  mgr_*.jsp -> 6 (manager)
 *  other -> 7 (my page)
 *  header.jsp에서 메뉴 nameing과 연계가 있음.
 *  게시판을 같은 jsp 파일로 공유하기 때문에 생기는 것으로 정확한 방법은 추후 연구가 필요하나 우선적으로 사용함.
 */
var lnavInit = function() {
	var subPage = getSubPageCategory() - 1;	// offset: 1
	var con = document.getElementsByClassName("subPage");
	var currentItem, prevItem;
	for(var i = 0;i < con.length;i++) {
		if(subPage == i) {
			currentItem = con[i];
			addClass(currentItem, "on");
		}
	}
}

function getPageAddress() {
	var thisUrl = document.URL;
	var index = thisUrl.lastIndexOf("/");
	return thisUrl.substring(index + 1, thisUrl.length - 4);
}

function getSubPageCategory() {
	var subAddress = getPageAddress();
	var data = null;
	if(subAddress.match("-")) {			// sub page
		data = subAddress.split("-");
		return data[0];
	}
	else if(subAddress.match("boardId")) {	// 게시판
		var bdId = subAddress[subAddress.length - 1];
		if(bdId >= '1' && bdId <= '7')
			return(3);
		else
			return(4);
	}
	else if(subAddress.match("articleId")) {
		return(4);;
	}
	else {
		var category = "" + sessionStorage.getItem("category");
		if(category == 'admin') {
			return(6);
		}
		else {
			return(7);
		}
	}
}

//액션
function addClass(ele, cls){
	var eleCln = ele.className;
	if(eleCln.indexOf(cls) == -1){
		ele.className = eleCln + " " + cls;
	}
}