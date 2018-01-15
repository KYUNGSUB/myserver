package com.talanton.service.myweb.pds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.talanton.service.myweb.common.Constants;
import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.pds.dao.PdsItemDao;
import com.talanton.service.myweb.pds.model.ListPdsItem;
import com.talanton.service.myweb.pds.model.PdsItem;

public class ListPdsItemService {
	private static ListPdsItemService instance = new ListPdsItemService();
	public static ListPdsItemService getInstance() {
		return instance;
	}
	private ListPdsItemService() {
		count_per_page = Integer.valueOf((String)Constants.getParameter("count_per_page"));
	}
	
	private int count_per_page;
	
	public ListPdsItem getPdsItemList(int pageNumber) throws NamingException {
		if (pageNumber < 0) {
			throw new IllegalArgumentException("page number < 0 : "	+ pageNumber);
		}
		PdsItemDao pDao = new PdsItemDao();
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			int totalArticleCount = pDao.selectCount(conn);

			if (totalArticleCount == 0) {
				return new ListPdsItem();
			}

			int totalPageCount = calculateTotalPageCount(totalArticleCount);

			int firstRow = (pageNumber - 1) * count_per_page + 1;
			int endRow = firstRow + count_per_page - 1;

			if (endRow > totalArticleCount) {
				endRow = totalArticleCount;
			}
			List<PdsItem> pList = pDao.select(conn, firstRow, endRow);

			ListPdsItem PdsItemListView = new ListPdsItem(
					pList, pageNumber, totalPageCount, firstRow, endRow);
			return PdsItemListView;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류:" + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private int calculateTotalPageCount(int totalPdsItemCount) {
		if (totalPdsItemCount == 0) {
			return 0;
		}
		int pageCount = totalPdsItemCount / count_per_page;
		if (totalPdsItemCount % count_per_page > 0) {
			pageCount++;
		}
		return pageCount;
	}
	
	public int getTotalPageNumber() throws NamingException {
		PdsItemDao pDao = new PdsItemDao();
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			int totalArticleCount = pDao.selectCount(conn);
			return calculateTotalPageCount(totalArticleCount);
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류:" + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}