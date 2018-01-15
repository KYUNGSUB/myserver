<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/common/lnav.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/lnav.css"/>
<div class="subPage">
	<div id="sub_title1" class="l_title">
		<h2>제공 서비스</h2>
	</div>
	<div id="sub_menu1" class="l_menu">
		<ul>
			<li><h5><a href="#">한국인이 좋아하는 명시</a></h5></li>
			<li><h5><a href="#">태교 육아 도우미</a></h5></li>
			<li><h5><a href="#">고전음악 듣기</a></h5></li>
			<li><h5><a href="#">백두산</a></h5></li>
			<li><h5><a href="#">한라산</a></h5></li>
			<li><h5><a href="#">백두산</a></h5></li>
			<li><h5><a href="#">금강산</a></h5></li>
			<li><h5><a href="#">지리산</a></h5></li>
			<li><h5><a href="#">설악산</a></h5></li>
		</ul>
	</div>
</div>
<div class="subPage">
	<div id="sub_title2" class="l_title">
		<h2>비지니스</h2>
	</div>
	<div id="sub_menu2" class="l_menu">
		<ul>
			<li><h5><a href="${pageContext.request.contextPath}/s/2/2-1.jsp">앱 개발(용역)</a></h5></li>
			<li><h5><a href="${pageContext.request.contextPath}/s/2/2-2.jsp">웹사이트 구축</a></h5></li>
			<li><h5><a href="#">학생/기업 멘토링</a></h5></li>
			<li><h5><a href="#">ICT 융합건설팅</a></h5></li>
		</ul>
	</div>
</div>
<div class="subPage">
	<div id="sub_title3" class="l_title">
		<h2>지식창고</h2>
	</div>
	<div id="sub_menu3" class="l_menu">
		<ul>
			<li><h5><a href="#">시 모음</a></h5></li>
			<li><h5><a href="#">육아 정보</a></h5></li>
			<li><h5><a href="#">도시농업</a></h5></li>
			<li><h5><a href="#">Android</a></h5></li>
			<li><h5><a href="#">Web(자바 기반)</a></h5></li>
			<li><h5><a href="#">임베디드</a></h5></li>
			<li><h5><a href="#">함께 만들어요</a></h5></li>
		</ul>
	</div>
</div>
<div class="subPage">
	<div id="sub_title4" class="l_title">
		<h2>고객지원</h2>
	</div>
	<div id="sub_menu4" class="l_menu">
		<ul>
			<li><h5><a href="#">공지사항</a></h5></li>
			<li><h5><a href="#">Q&amp;A</a></h5></li>
			<li><h5><a href="#">방명록</a></h5></li>
		</ul>
	</div>
</div>
<div class="subPage">
	<div id="sub_title5" class="l_title">
		<h2>회사소개</h2>
	</div>
	<div id="sub_menu5" class="l_menu">
		<ul>
			<li><h5><a href="#">인사말</a></h5></li>
			<li><h5><a href="#">경영이념</a></h5></li>
			<li><h5><a href="#">회사연혁</a></h5></li>
			<li><h5><a href="#">포트폴리오</a></h5></li>
			<li><h5><a href="#">찾아오시는 길</a></h5></li>
		</ul>
	</div>
</div>
<div class="subPage">
	<div id="sub_title6" class="l_title">
		<h2>관리자 메인</h2>
	</div>
	<div id="sub_menu6" class="l_menu">
		<ul>
			<li><h5><a href="${pageContext.request.contextPath}/admin/mgr_main.jsp">관리자 메인</a></h5></li>
			<li><h5><a href="${pageContext.request.contextPath}/admin/user_grade/mgr_listGrade.jsp">사용자 등급 관리</a></h5></li>
			<li><h5><a href="${pageContext.request.contextPath}/member/listPro.jsp">사용자 관리</a></h5>	</li>
			<li><h5><a href="${pageContext.request.contextPath}/admin/board/mgr_listBoard.jsp">게시판 관리</a></h5></li>
			<li><h5><a href="${pageContext.request.contextPath}/admin/poem/mgr_listToday.jsp">오늘의 시 게시판</a></h5></li>
			<li><h5><a href="${pageContext.request.contextPath}/download/list.jsp">Download 관리</a></h5></li>
			<li><h5><a href="${pageContext.request.contextPath}/play/list.jsp">Play 관리</a></h5></li>
			<li><h5><a href="${pageContext.request.contextPath}/music/list.jsp">음악 관리</a></h5></li>
			<li><h5><a href="${pageContext.request.contextPath}/admin/measure/mgr_measureMain.jsp">고전음악 듣기 통계관리</a></h5></li>
			<li><h5><a href="${pageContext.request.contextPath}/admin/test/1.jsp">분기 통계내기</a></h5></li>
		</ul>
	</div>
</div>
<div class="subPage">
	<div id="sub_title7" class="l_title">
		<h2>My Page</h2>
	</div>
	<div id="sub_menu7" class="l_menu">
		<ul>
			<li><h5><a href="${pageContext.request.contextPath}/member/readPro.jsp?id=${sessionScope.member.mid}&g=u">회원정보 보기</a></h5></li>
			<li><h5><a href="${pageContext.request.contextPath}/member/deleteForm.jsp?id=${sessionScope.member.mid}&g=u">회원 탈퇴</a></h5></li>
		</ul>
	</div>
</div>