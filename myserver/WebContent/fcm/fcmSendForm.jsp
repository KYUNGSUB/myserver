<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>푸시 메시지 전송</title>
<script type="text/javascript" src="<c:url value="/js/fcm/fcmSendForm.js"/>"></script>
</head>
<body>
<h3>푸시 메시지 전송</h3>
<form action="sendFcm.do" method="post">
	To : <input type="text" name="deviceId" placeholder="+821095303135" value="${deviceId}"><br>
	전송방법 : 
		<input type="radio" name="tx_method" value="h" checked>HTTP&nbsp;
		<input type="radio" name="tx_method" value="x">XMPP<br>
	<input id="noti_cb" type="checkbox" name="options" value="notification">&nbsp;Notification<br>
	<div id="noti_id" style="display:none">
		Title : <input type="text" name="title" placeholder="제목"><br>
		Body :<br>
		<textarea rows="5" cols="80" name="body" placeholder="내용"></textarea><br>
	</div>
	<input id="data_cb" type="checkbox" name="options" value="data">&nbsp;Data<br>
	<div id="data_id" style="display:none">
		<div class="data_item">
			name: <input type="text" name="data_name">&nbsp;value: <input type="text" name="data_value">&nbsp;<a>+</a>&nbsp;<a>-</a>
		</div>
	</div>
	<input type="submit" value="전송">
</form>
<c:if test="${result != null}">
<div id="result" style="display:none">
${result}
</div>
</c:if>
</body>
</html>