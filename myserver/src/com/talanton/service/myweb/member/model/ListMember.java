package com.talanton.service.myweb.member.model;

import java.util.ArrayList;
import java.util.List;

public class ListMember {
	private List<Member> memberList;
	private int requestPage;
	private int totalPageCount;
	private int startRow;
	private int endRow;

	public ListMember() {
		this(new ArrayList<Member>(), 0, 0, 0, 0);
	}
	
	public ListMember(List<Member> MemberList, int requestPageNumber,
			int totalPageCount, int startRow, int endRow) {
		this.memberList = MemberList;
		this.requestPage = requestPageNumber;
		this.totalPageCount = totalPageCount;
		this.startRow = startRow;
		this.endRow = endRow;
	}

	public List<Member> getMemberList() {
		return memberList;
	}
	
	public boolean isHasMember() {
		return !memberList.isEmpty();
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