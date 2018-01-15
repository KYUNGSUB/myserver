<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head><title>파라미터 읽기</title></head>
<body>
<table>
<tr>
	<td>파라미터명</td>
	<td>${setting_item.parameterName}</td>
</tr>
<tr>
	<td>값</td>
	<td>${setting_item.value}</td>
</tr>
<tr>
	<td>생성 시간</td>
	<td><fmt:formatDate value="${setting_item.createdAt}" 
		pattern="yyyy-MM-dd HH:mm:ss" /></td>
</tr>
<tr>
	<td>수정 시간</td>
	<td><fmt:formatDate value="${setting_item.modifiedAt}" 
		pattern="yyyy-MM-dd HH:mm:ss" /></td>
</tr>
<tr>
	<td colspan="2">
	<a href="listParameter.do?p=${param.p}">목록보기</a>
	<a href="updateParameter.do?parameterId=${setting_item.id}&p=${param.p}">수정하기</a>
	<a href="deleteParameter.do?parameterId=${setting_item.id}">삭제하기</a>
	</td>
</tr>
</table>
</body>
</html>