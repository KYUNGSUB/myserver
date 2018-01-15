package com.talanton.service.myweb.common;

import com.talanton.service.myweb.listener.CommonParameter;

public class Constants {

	public static final boolean SUCCESS = true;
	public static final String FCM_SERVER_ADDRESS = "https://fcm.googleapis.com/fcm/send";
	public static final String FCM_NOTIFICATION_SERVER_ADDRESS = "https://android.googleapis.com/gcm/notification";

	public static Object getParameter(String name) {
		CommonParameter cp = CommonParameter.getInstance();
		return cp.get(name);
	}

}
