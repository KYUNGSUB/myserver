<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head><title>FCM Group 보기</title>
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
		<h2>FCM Group 보기</h2>
		<table>
			<tr>
				<td>Group Id</td>
				<td>${group.groupName.gid}</td>
			</tr>
			<tr>
				<td>Group Name</td>
				<td>${group.groupName.notification_key_name}</td>
			</tr>
			<tr>
				<td>Group Key</td>
				<td style="max-width:640px;word-break:break-all">${group.groupName.notification_key}</td>
			</tr>
			<tr>
				<td>생성 시간</td>
				<td><fmt:formatDate value="${group.groupName.createdAt}" 
					pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td>수정 시간</td>
				<td><fmt:formatDate value="${group.groupName.modifiedAt}" 
					pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<c:forEach var="device" items="${group.dList}">
				<tr>
					<td>Device ID</td>
					<td>${device}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="2">
				<a href="listGroup.do?p=${param.p}">목록보기</a>
				<a href="updateGroup.do?gid=${group.groupName.gid}&p=${param.p}">수정하기</a>
				<a href="deleteGroup.do?did=${group.groupName.gid}">삭제하기</a>
				<a href="sendFcmGroup.do?gid=${group.groupName.gid}">FCM 그룹기기에 전송</a>
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