package com.talanton.service.myweb.command.web;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.token.model.AccessToken;
import com.talanton.service.myweb.token.service.AccessTokenNotFoundException;
import com.talanton.service.myweb.token.service.ReadAccessTokenService;

public class ReadTokenAction implements CommandAction {

	private static final String FORM_VIEW = "/token/readView.jsp";

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
		String id = request.getParameter("did");
		String viewPage = null;
		try {
			AccessToken access_token = ReadAccessTokenService.getInstance().getAccessToken(id);
			request.setAttribute("access_token", access_token);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 1L);
			viewPage = FORM_VIEW;
		} catch(AccessTokenNotFoundException ex) {
			viewPage = "/token/access_token_not_found.jsp";
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return viewPage;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}