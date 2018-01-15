package com.talanton.service.myweb.command.app;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.JsonObject;
import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.common.Constants;
import com.talanton.service.myweb.pds.file.FileSaveHelper;
import com.talanton.service.myweb.pds.model.AddRequest;
import com.talanton.service.myweb.pds.model.PdsItem;
import com.talanton.service.myweb.pds.service.AddPdsItemService;

public class UploadFileAction implements CommandAction {

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
		return null;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();

		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> items = upload.parseRequest(request);
			AddRequest addRequest = new AddRequest();
			Iterator<FileItem> iter = items.iterator();	// 각 파라미터에 대하여
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (item.isFormField()) {
					String name = item.getFieldName();
					if (name.equals("description")) {
						String value = item.getString();
						addRequest.setDescription(value);
					}
					else if (name.equals("articleId")) {
						String value = item.getString();
						addRequest.setArticleId(Integer.valueOf(value));
					}
					else if (name.equals("kind")) {
						String value = item.getString();
						addRequest.setKind(value);
					}
				} else {
					String name = item.getFieldName();
					if (name.equals("file")) {
						String realPath = FileSaveHelper.save((String)Constants.getParameter("upload_path"), item.getInputStream());
//						String realPath = FileSaveHelper.save("/media/usb/tomcat-server", item.getInputStream());
//						String realPath = FileSaveHelper.save("D:\\pds", item.getInputStream());
						addRequest.setFileName(item.getName());
						addRequest.setFileSize(item.getSize());
						addRequest.setRealPath(realPath);
					}
				}
			}
			PdsItem pdsItem = AddPdsItemService.getInstance().add(addRequest);
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("result", "success");
			return jsonObj.toString();

		} catch (FileUploadException | IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
