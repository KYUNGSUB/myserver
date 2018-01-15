package com.talanton.service.myweb.group.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddGroup {
	private String groupName;
	private List<String> dList = new ArrayList<String>();
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<String> getList() {
		return dList;
	}
	public void setList(List<String> dList) {
		this.dList = dList;
	}
	public void addDevice(String deviceId) {
		dList.add(deviceId);
	}
	
	public void validate(Map<String, Boolean> errors) {
		checkEmpty(errors, groupName, "notification_key_name");
		checkDeviceEmpty(errors, dList, "deviceId");
	}
	
	private void checkDeviceEmpty(Map<String, Boolean> errors, List<String> list, String fieldName) {
		if(list == null || list.isEmpty())
			errors.put(fieldName, Boolean.TRUE);
	}
	
	private void checkEmpty(Map<String, Boolean> errors, String value, String fieldName) {
		if(value == null || value.isEmpty())
			errors.put(fieldName, Boolean.TRUE);
	}
}