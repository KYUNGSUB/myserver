package com.talanton.service.myweb.command.app;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.token.model.AccessToken;
import com.talanton.service.myweb.token.service.AddAccessTokenService;

public class AddFcmTokenAction implements CommandAction {

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AccessToken accessToken = new AccessToken();
		accessToken.setToken(request.getParameter("Token"));
		accessToken.setDeviceId(request.getParameter("DeviceId"));
		AddAccessTokenService addService = AddAccessTokenService.getInstance();
		try {
			addService.addAccessToken(accessToken);
			Gson gson = new Gson();
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("result", "success");
			return gson.toJson(jsonObj);
		} catch(NamingException e) {
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		}
		return null;
	}

}
