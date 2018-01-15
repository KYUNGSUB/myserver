package com.talanton.service.myweb.command.app;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.settings.model.SettingItem;
import com.talanton.service.myweb.settings.service.ReadSettingItemService;
import com.talanton.service.myweb.settings.service.SettingItemNotFoundException;

public class ReadParameterAction implements CommandAction {
	
	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		}
		else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processForm(request, response);
		}
		else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("parameterName");
		String result = null;
		try {
			SettingItem setting_item = ReadSettingItemService.getInstance().getSettingItem(name);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 1L);
			result = setting_item.getValue();
		} catch(SettingItemNotFoundException ex) {
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}