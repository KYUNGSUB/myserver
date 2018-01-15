package com.talanton.service.myweb.command.app;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.group.service.CheckGroupService;

public class CheckGroupAction implements CommandAction {

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
		// TODO Auto-generated method stub
		return null;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String groupName = request.getParameter("notification_key_name");
		CheckGroupService checkService = CheckGroupService.getInstance();
		try {
			Gson gson = new Gson();
			JsonObject jsonObj = new JsonObject();
			if(checkService.checkDuplicate(groupName)) {
				jsonObj.addProperty("result", "exist");
			}
			else {
				jsonObj.addProperty("result", "not_exist");
			}
			return gson.toJson(jsonObj);
		} catch (NamingException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return null;
	}
}
