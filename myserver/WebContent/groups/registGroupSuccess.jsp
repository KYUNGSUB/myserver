<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fcm Group 추가 성공</title>
<link rel="stylesheet" href="<c:url value="/css/common/common.css"/>"/>
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
		<h2>FCM 기기 그룹 추가 성공</h2>
		<p>그룹이 성공적으로 추가 되었습니다.</p>
		<a href="listGroup.do">FCM 그룹 목록 보기</a>
	</section>
</section>
<footer>
	<jsp:include page="../common/footer.jsp" flush="true"/>
</footer>
</body>
</html>