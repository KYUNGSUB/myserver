package com.talanton.service.myweb.command.web;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.pds.model.ListPdsItem;
import com.talanton.service.myweb.pds.model.PdsItem;
import com.talanton.service.myweb.pds.service.ListPdsItemService;
import com.talanton.service.myweb.pds.service.ModifyPdsItemService;

public class ModifyFileAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		}
		else if(request.getMethod().equalsIgnoreCase("POST")) {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
		else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws NamingException {
		ListPdsItemService listService = ListPdsItemService.getInstance();
		ModifyPdsItemService modifyService = ModifyPdsItemService.getInstance();
		int totalPageNumber = listService.getTotalPageNumber();
		ListPdsItem itemListModel = null;
		for(int pageNumber = totalPageNumber;pageNumber > 0;pageNumber--) {
			itemListModel = listService.getPdsItemList(pageNumber);
			for(PdsItem pdsItem : itemListModel.getPdsItemList()) {
				modifyService.modifyPdsItem(pdsItem);
			}
		}

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
		return "/pds/listView.jsp";
	}
}