<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="../css/common/common.css"/>
<link rel="stylesheet" href="../css/member/join.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/member/join.js"></script>
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
		<h1>회원가입</h1>
		<form action="join.do" method="post">
			<input type="hidden" name="login_type" value="3">
			<input type="hidden" name="robot" value="not">
			<table>
				<tr>
					<td>아이디 : </td>
					<td>
						<input type="text" id="uid" name="uid" placeholder="영문과 숫자의 조합으로 6자 이상(중복 비허용)" size="50" autofocus="autofocus" required>
						<input type="button" id="idCheck" name="idCheck" value="중복 확인버튼">
						<img id="idimg" alt="icon" src="../images/member/eye127.png">
					</td>
				</tr>
				<tr>
					<td>비밀번호 : </td>
					<td>
						<input type="password" id="password" name="password" placeholder="특수문자, 영문, 숫자의 조합으로 8자 이상" size="50" required>
						<img class="pwimg" alt="icon" src="../images/member/eye127.png">
					</td>
				</tr>
				<tr>
					<td>비밀번호 확인 : </td>
					<td>
						<input type="password" id="cPassword" name="cPassword" placeholder="특수문자, 영문, 숫자의 조합으로 8자 이상" size="50" required>
						<img class="pwimg" alt="icon" src="../images/member/eye127.png">
					</td>
				</tr>
				<tr>
					<td>이름 : </td>
					<td><input type="text" id="name" name="name" size="50" placeholder="예) 홍길동" required></td>
				</tr>
				<tr>
					<td>이메일 : </td>
					<td>
						<input type="email" id="email" name="email" size="50" placeholder="예) kdhong@korea.com">
						<input type="button" id="emailCheck" name="emailCheck" value="중복 확인버튼">
						<img id="emailimg" alt="icon" src="../images/member/eye127.png">
					</td>
				</tr>
			</table>
			<div id="btnSub">
				<input type="submit" value="회원가입" onclick="return checkParameter()"> <input type="reset">
			</div>
		</form>
	</section>
</section>
<footer>
	<jsp:include page="../common/footer.jsp" flush="true"/>
</footer>
</body>
</html>