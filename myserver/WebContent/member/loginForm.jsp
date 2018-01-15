<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script type="text/javascript" charset="utf-8" src="../js/common/subcomm.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/member/login.js"></script>
<link rel="stylesheet" href="../css/common/common.css"/>
<link rel="stylesheet" href="../css/member/login.css"/>
</head>
<body>
<header>
	<jsp:include page="../common/header.jsp" flush="true"/>
</header>
<section id="wrap">
	<div id="lnav">
		<jsp:include page="../common/lnav.jsp" flush="true"/>
	</div>
	<div id="content">
		<form action="login.do" method="post" name="loginForm" id="loginForm">
		<fieldset>
			<div>
				<div class="title">로그인</div>
				<div class="input-wrap">
					<label for="id">ID(이메일)</label>
					<div class="input">
						<c:choose>
						<c:when test="${uid == null}">
							<input type="text" maxlength="80" placeholder="영문과 숫자의 조합으로 6자 이상 (중복 비허용)" size="50" autocomplete="off" id="uid" name="uid" autofocus="autofocus" required>
						</c:when>
						<c:otherwise>
							<input type="text" maxlength="80" placeholder="영문과 숫자의 조합으로 6자 이상 (중복 비허용)" size="50" autocomplete="off" id="uid" name="uid" autofocus="autofocus" required value="${uid}">
						</c:otherwise>
						</c:choose>
						<img id="idimg" alt="icon" src="../images/member/eye127.png">
						<label for="idimg" id="idLabel"></label>
					</div>
				</div>
				<div>
					<label for="pw">비밀번호</label>
					<div class="input">
						<input type="password" name="pw" id="pw" placeholder="특수문자, 영문, 숫자의 조합으로 8자 이상" size="50" autocomplete="off" maxlength="80" required>
						<img id="pwimg" alt="icon" src="../images/member/eye127.png">
						<label for="pwimg" id="pwLabel"></label>
					</div>
				</div>
				<p class="sign-link"><a href="#">ID / 비밀번호 찾기</a></p>
				<div class="btn-wrap">
					<input type="submit" value="로그인"/>
					<div id="signup-wrap">
						<p>아직 회원이 아니세요? <a href="terms.do" class="sign-up-btn">회원가입</a></p>
					</div>
				</div>
			</div>
		</fieldset>
		</form>
	</div>
</section>
<footer>
	<jsp:include page="../common/footer.jsp" flush="true"/>
</footer>
</body>
</html>