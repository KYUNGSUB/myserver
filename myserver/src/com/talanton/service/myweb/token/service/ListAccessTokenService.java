package com.talanton.service.myweb.token.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.talanton.service.myweb.common.Constants;
import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.token.dao.AccessTokenDao;
import com.talanton.service.myweb.token.model.ListAccessToken;
import com.talanton.service.myweb.token.model.AccessToken;

public class ListAccessTokenService {
	private static ListAccessTokenService instance = new ListAccessTokenService();
	public static ListAccessTokenService getInstance() {
		return instance;
	}
	private ListAccessTokenService() {
		
	}
	
	private int count_per_page;
	
	public ListAccessToken getAccessTokenList(int pageNumber) throws NamingException {
		if (pageNumber < 0) {
			throw new IllegalArgumentException("page number < 0 : "	+ pageNumber);
		}
		AccessTokenDao tokenDao = AccessTokenDao.getInstance();
		Connection conn = null;
		count_per_page = Integer.valueOf((String)Constants.getParameter("count_per_page"));
		try {
			conn = JdbcUtil.getConnection();
			int totalArticleCount = tokenDao.selectCount(conn);

			if (totalArticleCount == 0) {
				return new ListAccessToken();
			}

			int totalPageCount = calculateTotalPageCount(totalArticleCount);

			int firstRow = (pageNumber - 1) * count_per_page + 1;
			int endRow = firstRow + count_per_page - 1;

			if (endRow > totalArticleCount) {
				endRow = totalArticleCount;
			}
			List<AccessToken> AccessTokenList = tokenDao.select(conn, firstRow,
					endRow);

			ListAccessToken AccessTokenListView = new ListAccessToken(
					AccessTokenList, pageNumber, totalPageCount, firstRow, endRow);
			return AccessTokenListView;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류:" + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private int calculateTotalPageCount(int totalAccessTokenCount) {
		if (totalAccessTokenCount == 0) {
			return 0;
		}
		int pageCount = totalAccessTokenCount / count_per_page;
		if (totalAccessTokenCount % count_per_page > 0) {
			pageCount++;
		}
		return pageCount;
	}
}