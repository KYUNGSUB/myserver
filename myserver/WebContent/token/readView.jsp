<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head><title>Access Token 읽기</title>
<script type="text/javascript" charset="utf-8" src="<c:url value="/js/common/subcomm.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/common/common.css"/>"/>
<link rel="stylesheet" href="<c:url value="/css/member/listMember.css"/>"/>
<script type="text/javascript">
var subInit = function() { }
</script>
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
		<h2>Access Token 보기</h2>
		<table>
			<tr>
				<td>Device Id</td>
				<td>${access_token.deviceId}</td>
			</tr>
			<tr>
				<td>Access Token</td>
				<td style="max-width:640px;word-break:break-all">${access_token.token}</td>
			</tr>
			<tr>
				<td>생성 시간</td>
				<td><fmt:formatDate value="${access_token.createdAt}" 
					pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td>수정 시간</td>
				<td><fmt:formatDate value="${access_token.modifiedAt}" 
					pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td colspan="2">
				<a href="listToken.do?p=${param.p}">목록보기</a>
				<a href="updateToken.do?did=${access_token.deviceId}&p=${param.p}">수정하기</a>
				<a href="deleteToken.do?did=${access_token.deviceId}">삭제하기</a>
				<a href="sendFcm.do?deviceId=${access_token.deviceId}">FCM 전송</a>
				</td>
			</tr>
		</table>
	</section>
</section>
<footer>
	<jsp:include page="../common/footer.jsp" flush="true"/>
</footer>
</body>
</html>