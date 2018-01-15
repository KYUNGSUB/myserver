package com.talanton.service.myweb.command.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.group.model.AddGroup;
import com.talanton.service.myweb.group.service.RegistGroupService;
import com.talanton.service.myweb.token.service.ReadAccessTokenService;

public class RegistGroupAction implements CommandAction {
	private static final String FORM_VIEW = "/groups/registGroupForm.jsp";

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

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws NamingException, IOException {
		AddGroup addGroup = new AddGroup();
		String notification_key_name = request.getParameter("notification_key_name");
		addGroup.setGroupName(notification_key_name);
		String deviceId[] = request.getParameterValues("deviceId");
		for(int i = 0;i < deviceId.length;i++) {
			addGroup.addDevice(deviceId[i]);
		}
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		addGroup.validate(errors);
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		ReadAccessTokenService readService = ReadAccessTokenService.getInstance();
		List<String> tList = readService.getAccessToken(addGroup.getList());
					
		RegistGroupService regService = RegistGroupService.getInstance();
		regService.addGroup(addGroup, tList);
		
		return "/groups/registGroupSuccess.jsp";
	}
	
}