package com.talanton.service.myweb.token.model;

import java.util.ArrayList;
import java.util.List;

public class ListAccessToken {
	private List<AccessToken> tokenList;
	private int requestPage;
	private int totalPageCount;
	private int startRow;
	private int endRow;

	public ListAccessToken() {
		this(new ArrayList<AccessToken>(), 0, 0, 0, 0);
	}
	
	public ListAccessToken(List<AccessToken> AccessTokenList, int requestPageNumber,
			int totalPageCount, int startRow, int endRow) {
		this.tokenList = AccessTokenList;
		this.requestPage = requestPageNumber;
		this.totalPageCount = totalPageCount;
		this.startRow = startRow;
		this.endRow = endRow;
	}

	public List<AccessToken> getTokenList() {
		return tokenList;
	}
	
	public boolean isHasAccessToken() {
		return !tokenList.isEmpty();
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