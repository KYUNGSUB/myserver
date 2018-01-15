<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head><title>FCM Group 목록 보기</title>
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
		<h2>FCM Group 목록 보기</h2>
		<table border="1">
			<c:if test="${listModel.totalPageCount > 0}">
			<tr>
				<td colspan="4">
				${listModel.startRow}-${listModel.endRow}
				[${listModel.requestPage}/${listModel.totalPageCount}]
				</td>
			</tr>
			</c:if>
			
			<tr>
				<th align="center">Group ID</th>
				<th align="center">Group Name</th>
				<th align="center">생성시간</th>
				<th align="center">변경시간</th>
			</tr>

			<c:choose>
			<c:when test="${listModel.hasGroup == false}">
			<tr>
				<td colspan="4">
					등록 Group이 없습니다.
				</td>
			</tr>
			</c:when>
			<c:otherwise>
			<c:forEach var="item" items="${listModel.groupList}">			
			<tr>
				<td><c:set var="query" value="gid=${item.gid}&p=${listModel.requestPage}"/>
					<a href="<c:url value="/readGroup.do?${query}"/>">
					${item.gid}
					</a>
				</td>
				<td>${item.notification_key_name}</td>
				<td><fmt:formatDate value="${item.createdAt}" 
					pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><fmt:formatDate value="${item.modifiedAt}" 
					pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="4">
				<c:if test="${beginPage > 10}">
					<a href="listGroup.do?p=${beginPage-1}">이전</a>
				</c:if>
				<c:forEach var="pno" begin="${beginPage}" end="${endPage}">
				<a href="listGroup.do?p=${pno}">[${pno}]</a>
				</c:forEach>
				<c:if test="${endPage < listModel.totalPageCount}">
					<a href="listGroup.do?p=${endPage + 1}">다음</a>
				</c:if>
				</td>
			</tr>
			</c:otherwise>
		</c:choose>
			
			<tr>
				<td>서브 메뉴</td>
				<td colspan="3">
					<a href="registGroup.do">FCM Group 추가</a>
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