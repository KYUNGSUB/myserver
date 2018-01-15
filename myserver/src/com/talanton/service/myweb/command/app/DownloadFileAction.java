package com.talanton.service.myweb.command.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.pds.file.FileDownloadHelper;
import com.talanton.service.myweb.pds.model.PdsItem;
import com.talanton.service.myweb.pds.service.GetPdsItemService;
import com.talanton.service.myweb.pds.service.IncreaseDownloadCountService;
import com.talanton.service.myweb.pds.service.PdsItemNotFoundException;

public class DownloadFileAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processSubmit(request, response);
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
		return null;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			PdsItem item = GetPdsItemService.getInstance().getPdsItem(id);

			// 응답 헤더 다운로드로 설정
			response.reset();
			
			String fileName = new String(item.getFileName().getBytes("utf-8"), 
					"iso-8859-1");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", 
					"attachment; filename=\"" + fileName+"\"");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setContentLength((int)item.getFileSize());
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");

			FileDownloadHelper.copy(item.getRealPath(), 
					response.getOutputStream());
			
			response.getOutputStream().close();
			
			IncreaseDownloadCountService.getInstance().increaseCount(id);
//			JsonObject jsonObj = new JsonObject();
//			jsonObj.addProperty("result", "success");
			return null;
		} catch (PdsItemNotFoundException ex) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

}