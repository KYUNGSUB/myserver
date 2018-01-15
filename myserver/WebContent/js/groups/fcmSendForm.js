/**
 * 
 */
var subInit = function() {
	var result = document.getElementById("result");
	if(result != null) {
		alert("푸시 메시지 전송요청: " + result.innerHTML);
	}
	var noti_cb = document.getElementById("noti_cb");
	noti_cb.onchange = changeNotificationCheckbox;
	var data_cb = document.getElementById("data_cb");
	data_cb.onchange = changeDataCheckbox;
	var data_id = document.getElementById("data_id");
	var data_item = document.getElementsByClassName("data_item");
	for(var i = 0;i < data_item.length;i++) {
		var item = data_item[i].children;
		item[2].onclick = moreItems;
		item[2].title = i;
		item[3].onclick = lessItems;
		item[3].title = i;
	}
	
	function lessItems() {
		if(data_item.length > 1) {
			this.parentNode.remove();
			var temp = data_item[data_item.length - 1].children;
			temp[2].style.display = 'inline';
			temp[3].style.display = 'inline';
		}
	}

	function moreItems() {
		// alert(this.title);
		var iDiv = document.createElement('div');
		iDiv.className = 'data_item';
		iDiv.innerHTML = 'name: <input type="text" name="data_name">&nbsp;value: <input type="text" name="data_value">&nbsp;<a>+</a>&nbsp;<a>-</a>';
		var item = iDiv.children;
		item[2].onclick = moreItems;
		item[2].title = parseInt(this.title) + 1;
		item[3].onclick = lessItems;
		item[3].title = parseInt(this.title) + 1;
		data_id.appendChild(iDiv);
		this.style.display = 'none';
		this.parentNode.children[3].style.display = 'none';
	}
	
	function changeNotificationCheckbox() {
		var noti_id = document.getElementById("noti_id");
		if(noti_cb.checked == true) {
			noti_id.style.display = "block";
		}
		else {
			noti_id.style.display = "none";
		}
	}
	
	function changeDataCheckbox() {
		var data_id = document.getElementById("data_id");
		if(data_cb.checked == true) {
			data_id.style.display = "block";
		}
		else {
			data_id.style.display = "none";
		}
	}
}