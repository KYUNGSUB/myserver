package com.talanton.service.myweb.settings.model;

import java.sql.Timestamp;

public class SettingItemAdd {
	private String parameterName;
	private String value;
	private Timestamp createdAt;
	private Timestamp modifiedAt;

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public SettingItem toSettingItem() {
		SettingItem item = new SettingItem();
		item.setParameterName(parameterName);
		item.setValue(value);
		item.setCreatedAt(createdAt);
		item.setModifiedAt(modifiedAt);
		return item;
	}
}