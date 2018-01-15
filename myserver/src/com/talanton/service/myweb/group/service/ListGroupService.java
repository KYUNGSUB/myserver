package com.talanton.service.myweb.group.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.talanton.service.myweb.common.Constants;
import com.talanton.service.myweb.group.dao.FcmGroupDao;
import com.talanton.service.myweb.group.model.GroupName;
import com.talanton.service.myweb.group.model.ListGroup;
import com.talanton.service.myweb.jdbc.JdbcUtil;

public class ListGroupService {
	private static ListGroupService instance = new ListGroupService();
	public static ListGroupService getInstance() {
		return instance;
	}
	private ListGroupService() { }
	
	private int count_per_page;
	
	public ListGroup getGroupList(int pageNumber) throws NamingException {
		if (pageNumber < 0) {
			throw new IllegalArgumentException("page number < 0 : "	+ pageNumber);
		}
		FcmGroupDao fDao = FcmGroupDao.getInstance();
		Connection conn = null;
		count_per_page = Integer.valueOf((String)Constants.getParameter("count_per_page"));
		try {
			conn = JdbcUtil.getConnection();
			int totalGroupCount = fDao.selectCount(conn);

			if (totalGroupCount == 0) {
				return new ListGroup();
			}

			int totalPageCount = calculateTotalPageCount(totalGroupCount);

			int firstRow = (pageNumber - 1) * count_per_page + 1;
			int endRow = firstRow + count_per_page - 1;

			if (endRow > totalGroupCount) {
				endRow = totalGroupCount;
			}
			List<GroupName> GroupList = fDao.select(conn, firstRow,
					endRow);

			ListGroup GroupListView = new ListGroup(
					GroupList, pageNumber, totalPageCount, firstRow, endRow);
			return GroupListView;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류:" + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private int calculateTotalPageCount(int totalGroupCount) {
		if (totalGroupCount == 0) {
			return 0;
		}
		int pageCount = totalGroupCount / count_per_page;
		if (totalGroupCount % count_per_page > 0) {
			pageCount++;
		}
		return pageCount;
	}
}