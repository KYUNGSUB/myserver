<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/common/header.js"></script>
<script type="text/javascript" charset="utf-8">
	sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
</script>
<c:if test="${sessionScope.uid != null}">
	<script type="text/javascript" charset="utf-8">
		sessionStorage.setItem("mid", "${sessionScope.member.mid}");
		sessionStorage.setItem("uid", "${sessionScope.uid}");
		sessionStorage.setItem("name", "${sessionScope.member.name}");
	</script>
</c:if>
<c:if test="${sessionScope.uid == null}">
	<script type="text/javascript" charset="utf-8" >
		sessionStorage.removeItem("mid");
		sessionStorage.removeItem("uid");
		sessionStorage.removeItem("name");
	</script>
</c:if>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css"/>
<div id="logo">
	<a href="${pageContext.request.contextPath}/index.jsp">
		<img alt="logo" src="${pageContext.request.contextPath}/images/common/logo.png"/>
	</a>
</div>
<section id="hd_sec1">
	<div id="submenu">
		<a href="${pageContext.request.contextPath}/login.do" class="sitem">로그인</a>
		<a href="${pageContext.request.contextPath}/logout.do" class="sitem">로그아웃</a>
		<a href="${pageContext.request.contextPath}/terms.do" class="sitem">회원가입</a>
<!-- g = u(member), a(admin) -->
		<a href="${pageContext.request.contextPath}/profile.do?mid=${sessionScope.member.mid}" class="sitem">MyPage</a>
		
		<a href="#">Contact</a>
		<a href="#">Sitemap</a>
	</div>
	<nav>
		<div id="gnbMenu">
			<ul id="gnb">
				<li class="item" id="item1">
					<a href="${pageContext.request.contextPath}/s/1/1-1.jsp" class="menu"><span></span>제공 서비스</a>
					<div class="sub" id="sub1">
						<ul>
							<li><a href="${pageContext.request.contextPath}/s/1/1-1.jsp">한국인이 좋아하는 명시</a></li>
							<li><a href="${pageContext.request.contextPath}/s/1/1-2.jsp">태교 육아 도우미</a></li>
							<li><a href="${pageContext.request.contextPath}/s/1/1-3.jsp">고전음악 듣기</a></li>
							<li><a href="${pageContext.request.contextPath}/s/1/1-4.jsp">백두산</a></li>
							<li><a href="${pageContext.request.contextPath}/s/1/1-5.jsp">한라산</a></li>
							<li><a href="${pageContext.request.contextPath}/s/1/1-6.jsp">금강산</a></li>
							<li><a href="${pageContext.request.contextPath}/s/1/1-7.jsp">지리산</a></li>
							<li><a href="${pageContext.request.contextPath}/s/1/1-8.jsp">설악산</a></li>
						</ul>
					</div>
				</li>
				<li class="item" id="item2">
					<a href="${pageContext.request.contextPath}/s/2/2-1.jsp" class="menu"><span></span>비지니스</a>
					<div class="sub" id="sub2">
						<ul>
							<li><a href="${pageContext.request.contextPath}/s/2/2-1.jsp">앱 개발(용역)</a></li>
							<li><a href="${pageContext.request.contextPath}/s/2/2-2.jsp">웹 사이트 구축 (자반 기반)</a></li>
							<li><a href="${pageContext.request.contextPath}/s/2/2-3.jsp">학생/기업 멘토링(ICT)</a></li>
							<li><a href="${pageContext.request.contextPath}/s/2/2-4.jsp">ICT 융합 컨설팅</a></li>
						</ul>
					</div>
				</li>
				<li class="item" id="item3">
					<a href="${pageContext.request.contextPath}/pboard/list.jsp?boardId=1&p=1" class="menu"><span></span>지식창고</a>
					<div class="sub" id="sub3">
						<ul>
							<li><a href="${pageContext.request.contextPath}/pboard/list.jsp?boardId=1&p=1">시 모음</a></li>
							<li><a href="${pageContext.request.contextPath}/board/list.jsp?boardId=2&p=1">육아 정보</a></li>
							<li><a href="${pageContext.request.contextPath}/board/list.jsp?boardId=3&p=1">도시농업</a></li>
							<li><a href="${pageContext.request.contextPath}/board/list.jsp?boardId=4&p=1">Android</a></li>
							<li><a href="${pageContext.request.contextPath}/board/list.jsp?boardId=5&p=1">Web(자바기반)</a></li>
							<li><a href="${pageContext.request.contextPath}/board/list.jsp?boardId=6&p=1">임베디드</a></li>
							<li><a href="${pageContext.request.contextPath}/board/list.jsp?boardId=7&p=1">함께 만들어요</a></li>
						</ul>
					</div>
				</li>
				<li class="item" id="item14">
					<a href="${pageContext.request.contextPath}/board/list.jsp?boardId=8&p=1" class="menu"><span></span>고객지원</a>
					<div class="sub" id="sub4">
						<ul>
							<li><a href="${pageContext.request.contextPath}/board/list.jsp?boardId=8&p=1">공지사항</a></li>
							<li><a href="${pageContext.request.contextPath}/qna/list.jsp?boardId=9&p=1">Q&amp;A</a></li>
							<li><a href="${pageContext.request.contextPath}/guestbook/list.jsp?boardId=10&p=1">방명록</a></li>
						</ul>
					</div>
				</li>
				<li class="item" id="item5">
					<a href="${pageContext.request.contextPath}/s/5/5-1.jsp" class="menu"><span></span>회사소개</a>
					<div class="sub" id="sub5">
						<ul>
							<li><a href="${pageContext.request.contextPath}/s/5/5-1.jsp">인사말</a></li>
							<li><a href="${pageContext.request.contextPath}/s/5/5-2.jsp">경영이념</a></li>
							<li><a href="${pageContext.request.contextPath}/s/5/5-3.jsp">회사연혁</a></li>
							<li><a href="${pageContext.request.contextPath}/s/5/5-4.jsp">포트포리오</a></li>
							<li><a href="${pageContext.request.contextPath}/s/5/5-5.jsp">찾아오시는 길</a></li>
						</ul>
					</div>
				</li>
			</ul>
		</div>
	</nav>
</section>