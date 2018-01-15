<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>시스템 파라미터 목록</title></head>
<body>
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
		<td>번호</td>
		<td>파라미터명</td>
		<td>생성시간</td>
		<td>수정시간</td>
	</tr>
	
<c:choose>
	<c:when test="${listModel.hasSettingItem == false}">
	<tr>
		<td colspan="4">
			파라미터가 없습니다.
		</td>
	</tr>
	</c:when>
	<c:otherwise>
	<c:forEach var="item" items="${listModel.settingItemList}">
	<tr>
		<td>${item.id}</td>
		<td>
			<c:set var="query" value="parameterId=${item.id}&p=${listModel.requestPage}"/>
			<a href="<c:url value="/readParameter.do?${query}"/>">
			${item.parameterName}
			</a>
		</td>
		<td>${item.createdAt}</td>
		<td>${item.modifiedAt}</td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="4">
		
		<c:if test="${beginPage > 10}">
			<a href="listParameter.do?p=${beginPage-1}">이전</a>
		</c:if>
		<c:forEach var="pno" begin="${beginPage}" end="${endPage}">
		<a href="listParameter.do?p=${pno}">[${pno}]</a>
		</c:forEach>
		<c:if test="${endPage < listModel.totalPageCount}">
			<a href="listParameter.do?p=${endPage + 1}">다음</a>
		</c:if>
		</td>
	</tr>
	</c:otherwise>
</c:choose>
	
	<tr>
		<td colspan="4">
			<a href="addParameter.do">파라미터 추가</a>
		</td>
	</tr>
	
</table>
</body>
</html>