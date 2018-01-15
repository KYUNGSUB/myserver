package com.talanton.service.myweb.fcm.model;

import java.util.Map;

public class FcmMessage {
	private int id;
	private String method;
	private String deviceId;
	private Notification notification;
	private Map<String, String> data;
	private String xmpp;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getXmpp() {
		return xmpp;
	}
	public void setXmpp(String xmpp) {
		this.xmpp = xmpp;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	public void putItem(String key, String value) {
		data.put(key, value);
	}
	public Object getItem(String key) {
		return data.get(key);
	}
}