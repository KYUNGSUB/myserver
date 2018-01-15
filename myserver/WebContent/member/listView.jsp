<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head><title>회원 목록 보기</title>
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
		<h2>회원 목록 보기</h2>
		<table border="1">
			<c:if test="${listModel.totalPageCount > 0}">
			<tr>
				<td colspan="6">
				${listModel.startRow}-${listModel.endRow}
				[${listModel.requestPage}/${listModel.totalPageCount}]
				</td>
			</tr>
			</c:if>
			
			<tr>
				<th align="center">아이디</th>
				<th align="center">이름</th>
				<th align="center">유형</th>
				<th align="center">이메일</th>
				<th align="center">생성시간</th>
				<th align="center">변경시간</th>
			</tr>

			<c:choose>
			<c:when test="${listModel.hasMember == false}">
			<tr>
				<td colspan="6">
					가입 회원이 없습니다.
				</td>
			</tr>
			</c:when>
			<c:otherwise>
			<c:forEach var="item" items="${listModel.memberList}">			
			<tr>
				<td><c:set var="query" value="mid=${item.mid}&p=${listModel.requestPage}"/>
					<a href="<c:url value="/member/readMember.do?${query}"/>">
					${item.uid}
					</a>
				</td>
				<td>${item.name}</td>
				<td>${item.login_type}</td>
				<td>${item.email}</td>
				<td><fmt:formatDate value="${item.createdAt}" 
					pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><fmt:formatDate value="${item.modifiedAt}" 
					pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="6">
				<c:if test="${beginPage > 10}">
					<a href="listMember.do?p=${beginPage-1}">이전</a>
				</c:if>
				<c:forEach var="pno" begin="${beginPage}" end="${endPage}">
				<a href="listMember.do?p=${pno}">[${pno}]</a>
				</c:forEach>
				<c:if test="${endPage < listModel.totalPageCount}">
					<a href="listMember.do?p=${endPage + 1}">다음</a>
				</c:if>
				</td>
			</tr>
			</c:otherwise>
		</c:choose>
			
			<tr>
				<td>서브 메뉴</td>
				<td colspan="5">
					<a href="terms.do">회원추가</a>
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