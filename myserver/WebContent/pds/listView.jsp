<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>자료실 목록</title></head>
<body>
<table border="1">
	<c:if test="${listModel.totalPageCount > 0}">
	<tr>
		<td colspan="5">
		${listModel.startRow}-${listModel.endRow}
		[${listModel.requestPage}/${listModel.totalPageCount}]
		</td>
	</tr>
	</c:if>
	
	<tr>
		<td>번호</td>
		<td>파일명</td>
		<td>파일크기</td>
		<td>다운로드회수</td>
		<td>다운로드</td>
	</tr>
	
<c:choose>
	<c:when test="${listModel.hasPdsItem == false}">
	<tr>
		<td colspan="5">
			게시글이 없습니다.
		</td>
	</tr>
	</c:when>
	<c:otherwise>
	<c:forEach var="item" items="${listModel.pdsItemList}">
	<tr>
		<td>${item.id}</td>
		<td>${item.fileName}</td>
		<td>${item.fileSize}</td>
		<td>${item.downloadCount}</td>
		<td><a href="downloadFile.do?id=${item.id}">다운받기</a></td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="5">
		
		<c:if test="${beginPage > 10}">
			<a href="listFile.do?p=${beginPage-1}">이전</a>
		</c:if>
		<c:forEach var="pno" begin="${beginPage}" end="${endPage}">
		<a href="listFile.do?p=${pno}">[${pno}]</a>
		</c:forEach>
		<c:if test="${endPage < listModel.totalPageCount}">
			<a href="listFile.do?p=${endPage + 1}">다음</a>
		</c:if>
		</td>
	</tr>
	</c:otherwise>
</c:choose>
	
	<tr>
		<td colspan="5">
			<a href="uploadFile.do">파일 첨부</a>
			<a href=searchFile.do>파일 검색</a>
		</td>
	</tr>	
</table>
</body>
</html>