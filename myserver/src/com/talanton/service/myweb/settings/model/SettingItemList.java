package com.talanton.service.myweb.settings.model;

import java.util.ArrayList;
import java.util.List;

public class SettingItemList {
	private List<SettingItem> settingItemList;
	private int requestPage;
	private int totalPageCount;
	private int startRow;
	private int endRow;

	public SettingItemList() {
		this(new ArrayList<SettingItem>(), 0, 0, 0, 0);
	}
	
	public SettingItemList(List<SettingItem> SettingItemList, int requestPageNumber,
			int totalPageCount, int startRow, int endRow) {
		this.settingItemList = SettingItemList;
		this.requestPage = requestPageNumber;
		this.totalPageCount = totalPageCount;
		this.startRow = startRow;
		this.endRow = endRow;
	}

	public List<SettingItem> getSettingItemList() {
		return settingItemList;
	}
	
	public boolean isHasSettingItem() {
		return ! settingItemList.isEmpty();
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