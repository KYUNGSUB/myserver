package com.talanton.service.myweb.command.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.talanton.service.myweb.command.CommandAction;

public class TermsAction implements CommandAction {

	private static final String FORM_VIEW = "/member/termsForm.jsp";

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		String viewPage = null;
		String terms1 = request.getParameter("terms1");
		String terms2 = request.getParameter("terms2");
		
		if(terms1==null || terms1.equals("")) {
			viewPage = "/member/terms_agreement.jsp";
		}
		else if(terms2== null || terms2.equals("")) {
			viewPage = "/member/terms_indivisual.jsp";
		}
		else if(terms1.equals("동의") && terms2.equals("동의")) {
			HttpSession session = request.getSession();
			session.setAttribute("term", true);
			viewPage = "/member/joinForm.jsp";
		}
		else if(terms1.equals("비동의")) {
			viewPage = "/member/terms_agreement.jsp";
		}
		else if(terms2.equals("비동의")) {
			viewPage = "/member/terms_indivisual.jsp";
		}
		return viewPage;
	}

}