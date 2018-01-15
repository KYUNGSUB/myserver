package com.talanton.service.myweb.command.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.pds.model.PdsItem;
import com.talanton.service.myweb.pds.service.GetPdsItemService;

public class GetUuidAction implements CommandAction {

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

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		String description = request.getParameter("description");
		if(description != null && !description.equals("")) {
			GetPdsItemService getService = GetPdsItemService.getInstance();
			PdsItem pdsItem = getService.getPdsItemByPid(description);
			Gson gson = new Gson();
			JsonObject jsonObj = new JsonObject();
			if(pdsItem == null) {
				jsonObj.addProperty("result", "failure");
			}
			else {
				jsonObj.addProperty("result", "success");
				jsonObj.addProperty("pds_item_id", pdsItem.getId());
				jsonObj.addProperty("filesize", pdsItem.getFileSize());
			}
			return gson.toJson(jsonObj);
		}
		return null;
	}

}