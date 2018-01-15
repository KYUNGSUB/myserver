package com.talanton.service.myweb.group.model;

import java.sql.Timestamp;

public class GroupName {
	private int gid;
	private String notification_key_name;
	private String notification_key;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getNotification_key_name() {
		return notification_key_name;
	}
	public void setNotification_key_name(String notification_key_name) {
		this.notification_key_name = notification_key_name;
	}
	public String getNotification_key() {
		return notification_key;
	}
	public void setNotification_key(String notification_key) {
		this.notification_key = notification_key;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
}