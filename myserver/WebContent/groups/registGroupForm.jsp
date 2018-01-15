<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FCM Group 등록</title>
<link rel="stylesheet" href="<c:url value="/css/common/common.css"/>"/>
<link rel="stylesheet" href="<c:url value="/css/groups/registForm.css"/>"/>
<script type="text/javascript" charset="utf-8" src="<c:url value="/js/jquery/jquery-1.11.3.min.js"/>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/js/groups/registForm.js"/>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/js/common/subcomm.js"/>"></script>
</head>
<body>
<header>
	<jsp:include page="../common/header.jsp" flush="true"/>
</header>
<section id="wrap">
	<div id="lnav">
		<jsp:include page="../common/lnav.jsp" flush="true"/>
	</div>
	
	<section id="content">
		<h2>FCM 기기 그룹 만들기</h2>
		<div id="member_area">
			<form action="registGroup.do" method="post">
				<div id="group_name">
					그룹 이름 : <input type="text" id="notification_key_name" name="notification_key_name" placeholder="고유값을 입력해 주세요." size="30" required>
					&nbsp;<a href="#">중복 체크</a><br>
				</div>
				<label>번호</label>&nbsp;<label style="width:350px;text-align:center;">디바이스 아이디</label><br>
				<div id="device_area">
					<!-- 
					<div class="device_item">
						<label>1</label>&nbsp;<input type="text" name="deviceId" size="40">&nbsp;<a href="javascript:lessItem()">-</a>
					</div>
					-->
				</div>

				<br>
				<input type="submit" value="그룹 만들기">
			</form>
		</div>
		<div id="list_area">
		
		</div>
	</section>
</section>
<footer>
	<jsp:include page="../common/footer.jsp" flush="true"/>
</footer>
</body>
</html>