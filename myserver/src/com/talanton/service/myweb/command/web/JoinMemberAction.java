package com.talanton.service.myweb.command.web;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.member.model.AddMember;
import com.talanton.service.myweb.member.model.Member;
import com.talanton.service.myweb.member.service.AddMemberService;

public class JoinMemberAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
		else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		}
		else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws NamingException {
		String viewPage = null;
		AddMember addMember = new AddMember();
		addMember.setUid(request.getParameter("uid"));
		addMember.setPassword(request.getParameter("password"));
		addMember.setcPassword(request.getParameter("cPassword"));
		addMember.setName(request.getParameter("name"));
		addMember.setEmail(request.getParameter("email"));
		addMember.setLogin_type(Integer.parseInt(request.getParameter("login_type")));
		addMember.setRobot(request.getParameter("robot"));
		
		if(addMember.getRobot().isEmpty()) {
			request.setAttribute("reason", "robot");
			viewPage = "/member/joinError.jsp";
		}
		else {
			AddMemberService addMemberService = AddMemberService.getInstance();
			Member member = addMember.toMember();
			if(addMemberService.add(member) > 0) {
				request.setAttribute("uid", member.getUid());
				viewPage = "/member/loginForm.jsp";
			}
			else {
				request.setAttribute("reason", "failure");
				viewPage = "/member/joinError.jsp";
			}
		}
		
		return viewPage;
	}
}