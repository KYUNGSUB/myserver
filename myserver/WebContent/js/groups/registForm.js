/**
 * 
 */
var subInit = function() {
	var isPass = false;
	getDeviceList(1);
	var list_area = $("#list_area")[0];
	var device = $("#device_area")[0];
	$("#group_name a")[0].onclick = duplicationCheck;
	form[0].onsubmit = createFcmGroup;
	
	function getDeviceList(page) {
		var query = {p : page};
		var ctx = getContextPath();
		var url = ctx + "/listToken.do";
		$.ajax({
			type: "POST",
			url: url,
			data: query,
			success: function(data) {
				var sid = data.trim();
				list_area.innerHTML = sid;
				$(".token_can").change(function() {
					if(this.checked) {
						// 그룹 만들기 목록에 device 정보가 있어야 함.
						addDevice(this);
					}
					else {
						removeDevice(this);
					}
				});
				
			}
		});
	}
	
	function addDevice(token) {
		alert("addDevice : " + token.accessKey);
		var deviceNode = token.parentNode.parentNode.firstElementChild;
		for(var i = 0;i < device.children.length;i++) {
			var candidate = device.children[i].children[1];
			if(deviceNode.textContent == candidate.value)
				return;
		}

		var newNode = document.createElement('div');
		newNode.setAttribute('class', 'device_item');
		newNode.innerHTML = '<label>' + (device.children.length + 1)
			+ '</label>&nbsp;<input type="text" name="deviceId" value="' + deviceNode.textContent
			+ '" size="40">&nbsp;<a>-</a>';
		anchorNode = newNode.children[2];
		anchorNode.onclick = lessItem;
		device.appendChild(newNode);
	}
	
	function removeDevice(token) {
		alert("removeDevice : " + token.accessKey);
		var deviceNode = token.parentNode.parentNode.firstElementChild;
		for(var i = 0;i < device.children.length;i++) {
			var candidate = device.children[i].children[1];
			if(deviceNode.textContent == candidate.value) {
				device.removeChild(device.children[i]);
				return;
			}
		}
	}
	
	function lessItem() {
		var deviceNode = this.parentNode;
		var tokenNodes = $(".token_can");
		for(var i = 0;i < tokenNodes.length;i++) {
			var tokenNode = tokenNodes[i].parentNode.parentNode.firstElementChild;
			var inputNode = tokenNodes[i].parentNode.parentNode.children[1].children[0];
			if(tokenNode.textContent.trim() == deviceNode.children[1].value) {
				if(inputNode.checked == true) {
					inputNode.checked = false;
					device.removeChild(deviceNode);
				}	
			}
		}
		
	}

	function getContextPath() {
    	return sessionStorage.getItem("contextpath");
    }
	
	function duplicationCheck() {
		var groupName = $("#notification_key_name")[0].value;
		if(groupName.length == 0) {
			alert("group name을 입력 하세요.");
			return;
		}
		
		var query = {notification_key_name : groupName};
		var ctx = getContextPath();
		var url = ctx + "/checkGroup.app";
		$.ajax({
			type: "POST",
			url: url,
			data: query,
			success: function(data) {
				var json = eval('(' + data + ')');
				if(json.result == "exist") {
					alert("중복된 그룹 이름 입니다.");
					isPass = false;
				}
				else {
					alert("사용 가능한 그룹명 입니다.");
					isPass = true;
				}
			}
		});
	}
	
	function createFcmGroup() {
		if(isPass == false) {
			alert("그룹 이름에 대한 중복 체크가 필요합니다.");
			return false;
		}
		return true;
	}
}