package com.talanton.service.myweb.command.web;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.member.model.Member;
import com.talanton.service.myweb.member.service.LoginService;

public class LoginMemberAction implements CommandAction {
	private static final String FORM_VIEW = "/member/loginForm.jsp";

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		}
		else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		}
		else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest request, HttpServletResponse response) throws NamingException {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws NamingException {
		String viewPage = null;
		String uid = request.getParameter("uid");
		String password = request.getParameter("pw");
		LoginService loginService = LoginService.getInstance();
		Member member = loginService.login(uid, password);
		HttpSession session = request.getSession();
		if(member != null) {
			session.setAttribute("member", member);
			session.setAttribute("uid", uid);
			viewPage = "/index.jsp";
		}
		else {
			request.setAttribute("uid", uid);
			viewPage = "/member/loginForm.jsp";
		}
		return viewPage;
	}
}