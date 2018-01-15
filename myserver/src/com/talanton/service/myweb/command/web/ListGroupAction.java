package com.talanton.service.myweb.command.web;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.group.model.ListGroup;
import com.talanton.service.myweb.group.service.ListGroupService;

public class ListGroupAction implements CommandAction {

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
		String pageNumberString = request.getParameter("p");
		int pageNumber = 1;
		if(pageNumberString != null && pageNumberString.length() > 0) {
			pageNumber = Integer.parseInt(pageNumberString);
		}
		ListGroupService listSerivce = ListGroupService.getInstance();
		ListGroup itemListModel = listSerivce.getGroupList(pageNumber);
		request.setAttribute("listModel", itemListModel);
		if (itemListModel.getTotalPageCount() > 0) {
			int beginPageNumber = (itemListModel.getRequestPage() - 1) / 10 * 10 + 1;
			int endPageNumber = beginPageNumber + 9;
			if (endPageNumber > itemListModel.getTotalPageCount()) {
				endPageNumber = itemListModel.getTotalPageCount();
			}
			request.setAttribute("beginPage", beginPageNumber);
			request.setAttribute("endPage", endPageNumber);
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 1L);
		return "/groups/listView.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}