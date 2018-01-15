<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head><title>파일 등록</title></head>
<body>
<form action="uploadFile.do" method="post" enctype="multipart/form-data">
파일: <input type="file" name="file" /> <br/>
대분류 : <input type="text" name="description" /> <br/>
중분류 : <input type="number" name="articleId" placeholder="1234"> <br/>
소분류 : <input type="text" name="kind" placeholder=""> <br/>
<input type="submit" value="업로드" />
</form>
</body>
</html>