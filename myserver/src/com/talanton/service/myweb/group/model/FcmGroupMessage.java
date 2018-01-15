package com.talanton.service.myweb.group.model;

import java.util.Map;

public class FcmGroupMessage {
	private int id;
	private String groupName;
	private String method;
	private Map<String, String> data;
	private String xmpp;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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