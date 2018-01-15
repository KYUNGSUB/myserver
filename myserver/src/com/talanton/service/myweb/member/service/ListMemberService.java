package com.talanton.service.myweb.member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.talanton.service.myweb.common.Constants;
import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.member.dao.MemberDao;
import com.talanton.service.myweb.member.model.ListMember;
import com.talanton.service.myweb.member.model.Member;

public class ListMemberService {
	private static ListMemberService instance = new ListMemberService();
	public static ListMemberService getInstance() {
		return instance;
	}
	private ListMemberService() {
		
	}
	
	private int count_per_page;
	
	public ListMember getMemberList(int pageNumber) throws NamingException {
		if (pageNumber < 0) {
			throw new IllegalArgumentException("page number < 0 : "	+ pageNumber);
		}
		MemberDao memberDao = new MemberDao();
		Connection conn = null;
		count_per_page = Integer.valueOf((String)Constants.getParameter("count_per_page"));
		try {
			conn = JdbcUtil.getConnection();
			int totalArticleCount = memberDao.selectCount(conn);

			if (totalArticleCount == 0) {
				return new ListMember();
			}

			int totalPageCount = calculateTotalPageCount(totalArticleCount);

			int firstRow = (pageNumber - 1) * count_per_page + 1;
			int endRow = firstRow + count_per_page - 1;

			if (endRow > totalArticleCount) {
				endRow = totalArticleCount;
			}
			List<Member> MemberList = memberDao.select(conn, firstRow,
					endRow);

			ListMember MemberListView = new ListMember(
					MemberList, pageNumber, totalPageCount, firstRow, endRow);
			return MemberListView;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류:" + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private int calculateTotalPageCount(int totalMemberCount) {
		if (totalMemberCount == 0) {
			return 0;
		}
		int pageCount = totalMemberCount / count_per_page;
		if (totalMemberCount % count_per_page > 0) {
			pageCount++;
		}
		return pageCount;
	}
}