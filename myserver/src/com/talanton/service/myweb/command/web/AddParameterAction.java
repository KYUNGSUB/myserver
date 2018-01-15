package com.talanton.service.myweb.command.web;

import java.sql.Timestamp;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.settings.model.SettingItem;
import com.talanton.service.myweb.settings.model.SettingItemAdd;
import com.talanton.service.myweb.settings.service.AddSettingItemService;

public class AddParameterAction implements CommandAction {
	private static final String FORM_VIEW = "/settings/addSettingForm.jsp";

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

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws NamingException {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		SettingItemAdd settingItemAdd = new SettingItemAdd();
		settingItemAdd.setParameterName(request.getParameter("parameterName"));
		settingItemAdd.setValue(request.getParameter("value"));
		settingItemAdd.setCreatedAt(now);
		
		SettingItem addedParameter = 
				AddSettingItemService.getInstance().add(settingItemAdd);
		request.setAttribute("addedParameter", addedParameter);
		return "/listParameter.do";
	}

}
