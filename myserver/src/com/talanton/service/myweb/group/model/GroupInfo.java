package com.talanton.service.myweb.group.model;

import java.util.ArrayList;
import java.util.List;

public class GroupInfo {
	private GroupName groupName;
	private List<String> dList = new ArrayList<String>();
	
	public GroupName getGroupName() {
		return groupName;
	}
	public void setGroupName(GroupName groupName) {
		this.groupName = groupName;
	}
	public List<String> getdList() {
		return dList;
	}
	public void setdList(List<String> dList) {
		this.dList = dList;
	}
}