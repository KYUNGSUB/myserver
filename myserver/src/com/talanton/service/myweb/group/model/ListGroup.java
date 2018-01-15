package com.talanton.service.myweb.group.model;

import java.util.ArrayList;
import java.util.List;

public class ListGroup {
	private List<GroupName> groupList;
	private int requestPage;
	private int totalPageCount;
	private int startRow;
	private int endRow;

	public ListGroup() {
		this(new ArrayList<GroupName>(), 0, 0, 0, 0);
	}
	
	public ListGroup(List<GroupName> GroupList, int requestPageNumber,
			int totalPageCount, int startRow, int endRow) {
		this.groupList = GroupList;
		this.requestPage = requestPageNumber;
		this.totalPageCount = totalPageCount;
		this.startRow = startRow;
		this.endRow = endRow;
	}

	public List<GroupName> getGroupList() {
		return groupList;
	}
	
	public boolean isHasGroup() {
		return !groupList.isEmpty();
	}

	public int getRequestPage() {
		return requestPage;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}
}