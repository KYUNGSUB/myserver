<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>파라미터 추가</title></head>
<body>

<form action="<c:url value='/addParameter.do' />" method="post">
파라미터명: <input type="text" name="parameterName" size="20"/> <br/>
값: <input type="text" name="value" /> <br/>
<input type="submit" value="전송" />
</form>

</body>