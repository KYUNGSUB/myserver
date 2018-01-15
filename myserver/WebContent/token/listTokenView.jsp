<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3 style="margin-top:20px;">Access Token 목록 보기</h3>
<table border="1">
	<c:if test="${listModel.totalPageCount > 0}">
	<tr>
		<td colspan="2">
		${listModel.startRow}-${listModel.endRow}
		[${listModel.requestPage}/${listModel.totalPageCount}]
		</td>
	</tr>
	</c:if>
	
	<tr>
		<th align="center">Device ID</th>
		<th>선택</th>
	</tr>
		<c:choose>
	<c:when test="${listModel.hasAccessToken == false}">
	<tr>
		<td colspan="2">등록 Access Token이 없습니다.	</td>
	</tr>
	</c:when>
	<c:otherwise>
	<c:forEach var="item" items="${listModel.tokenList}" varStatus="status">			
	<tr>
		<td>${item.deviceId}</td>
		<td><input class="token_can" type="checkbox" name="candidate" accesskey="${status.count}"></td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="2">
		<c:if test="${beginPage > 10}">
			<a href="javascript:getDeviceList(${beginPage-1})">이전</a>
		</c:if>
		<c:forEach var="pno" begin="${beginPage}" end="${endPage}">
		<a href="javascript:getDeviceList(${pno})">[${pno}]</a>
		</c:forEach>
		<c:if test="${endPage < listModel.totalPageCount}">
			<a href="javascript:getDeviceList(${endPage + 1})">다음</a>
		</c:if>
		</td>
	</tr>
	</c:otherwise>
</c:choose>
</table>