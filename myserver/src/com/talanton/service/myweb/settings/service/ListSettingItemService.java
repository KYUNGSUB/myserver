package com.talanton.service.myweb.settings.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.talanton.service.myweb.common.Constants;
import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.settings.dao.SettingItemDao;
import com.talanton.service.myweb.settings.model.SettingItem;
import com.talanton.service.myweb.settings.model.SettingItemList;

public class ListSettingItemService {

	private static ListSettingItemService instance = new ListSettingItemService();

	public static ListSettingItemService getInstance() {
		return instance;
	}

	private ListSettingItemService() {
		count_per_page = Integer.valueOf((String)Constants.getParameter("count_per_page"));
	}

	private int count_per_page;

	public SettingItemList getSettingItemList(int pageNumber) throws NamingException {
		if (pageNumber < 0) {
			throw new IllegalArgumentException("page number < 0 : "
					+ pageNumber);
		}
		SettingItemDao settingItemDao = SettingItemDao.getInstance();
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			int totalArticleCount = settingItemDao.selectCount(conn);

			if (totalArticleCount == 0) {
				return new SettingItemList();
			}

			int totalPageCount = calculateTotalPageCount(totalArticleCount);

			int firstRow = (pageNumber - 1) * count_per_page + 1;
			int endRow = firstRow + count_per_page - 1;

			if (endRow > totalArticleCount) {
				endRow = totalArticleCount;
			}
			List<SettingItem> SettingItemList = settingItemDao.select(conn, firstRow,
					endRow);

			SettingItemList SettingItemListView = new SettingItemList(
					SettingItemList, pageNumber, totalPageCount, firstRow, endRow);
			return SettingItemListView;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 :" + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private int calculateTotalPageCount(int totalSettingItemCount) {
		if (totalSettingItemCount == 0) {
			return 0;
		}
		int pageCount = totalSettingItemCount / count_per_page;
		if (totalSettingItemCount % count_per_page > 0) {
			pageCount++;
		}
		return pageCount;
	}
}