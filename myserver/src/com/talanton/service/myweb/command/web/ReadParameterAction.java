package com.talanton.service.myweb.command.web;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.settings.model.SettingItem;
import com.talanton.service.myweb.settings.service.ReadSettingItemService;
import com.talanton.service.myweb.settings.service.SettingItemNotFoundException;

public class ReadParameterAction implements CommandAction {

	private static final String FORM_VIEW = "/settings/readView.jsp";

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
		int id = Integer.parseInt(request.getParameter("parameterId"));
		String viewPage = null;
		try {
			SettingItem setting_item = ReadSettingItemService.getInstance().getSettingItem(id);
			request.setAttribute("setting_item", setting_item);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 1L);
			viewPage = "/settings/readView.jsp";
		} catch(SettingItemNotFoundException ex) {
			viewPage = "/settings/setting_item_not_found.jsp";
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}