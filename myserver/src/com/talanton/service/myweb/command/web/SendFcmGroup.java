package com.talanton.service.myweb.command.web;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.fcm.model.FcmMessage;
import com.talanton.service.myweb.fcm.model.Notification;
import com.talanton.service.myweb.group.model.GroupName;
import com.talanton.service.myweb.group.service.GroupNotFoundException;
import com.talanton.service.myweb.group.service.ReadGroupService;
import com.talanton.service.myweb.group.service.SendGroupFcmService;

public class SendFcmGroup implements CommandAction {
	private static final String FORM_VIEW = "/groups/fcmSendForm.jsp";

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws NamingException, GroupNotFoundException {
		int gid = Integer.parseInt(request.getParameter("gid"));
		ReadGroupService readService = ReadGroupService.getInstance();
		GroupName group = readService.getGroupName(gid);
		request.setAttribute("group", group);
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FcmMessage msg = new FcmMessage();
		msg.setDeviceId(request.getParameter("deviceId"));
		msg.setMethod(request.getParameter("tx_method"));
		String options[] = request.getParameterValues("options");
		for(int i =0;i < options.length;i++) {
			if(options[i].equals("notification")) {
				Notification noti = new Notification();
				noti.setBody(request.getParameter("body"));
				noti.setTitle(request.getParameter("title"));
				msg.setNotification(noti);
			}
			else if(options[i].equals("data")) {
				String dataName[] = request.getParameterValues("data_name");
				String dataValue[] = request.getParameterValues("data_value");
				Map<String, String> data = new HashMap<String, String>();
				for(int j = 0;j < dataName.length;j++) {
					data.put(dataName[j], dataValue[j]);
				}
				msg.setData(data);
			}
		}

		SendGroupFcmService sendService = SendGroupFcmService.getInstance();
		if(msg.getMethod().equals("h")) {	// HTTP ('h')
			if(sendService.sendFcmByHttp(msg)) {	// FCM 전송 성공
				request.setAttribute("result", "success");
			}
			else {	// FCM 전송 실패
				request.setAttribute("result", "failure");
			}
		}
		else {	// XMPP ('x')
			if(sendService.sendFcmByXmpp(msg)) {	// FCM 전송 성공
				request.setAttribute("result", "success");
			}
			else {	// FCM 전송 실패
				request.setAttribute("result", "failure");
			}
		}
		
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 1L);
		return FORM_VIEW;
	}
}