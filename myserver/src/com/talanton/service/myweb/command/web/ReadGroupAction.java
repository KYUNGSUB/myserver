package com.talanton.service.myweb.command.web;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.group.model.GroupInfo;
import com.talanton.service.myweb.group.service.GroupNotFoundException;
import com.talanton.service.myweb.group.service.ReadGroupService;

public class ReadGroupAction implements CommandAction {

	private static final String FORM_VIEW = "/groups/readView.jsp";

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws NamingException, GroupNotFoundException {
		int gid = Integer.parseInt(request.getParameter("gid"));
		String viewPage = null;
		ReadGroupService readService = ReadGroupService.getInstance();
		GroupInfo group = readService.getGroupInfo(gid);
		request.setAttribute("group", group);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 1L);
		viewPage = FORM_VIEW;
		return viewPage;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}