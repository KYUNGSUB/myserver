package com.talanton.service.myweb.pds.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.pds.dao.PdsItemDao;

public class IncreaseDownloadCountService {
	private static IncreaseDownloadCountService instance = new IncreaseDownloadCountService();

	public static IncreaseDownloadCountService getInstance() {
		return instance;
	}

	private IncreaseDownloadCountService() {
	}

	public boolean increaseCount(int id) throws NamingException {
		Connection conn = null;
		PdsItemDao pDao = new PdsItemDao();
		try {
			conn = JdbcUtil.getConnection();
			int updatedCount = pDao.increaseCount(conn, id);
			return updatedCount == 0 ? false : true;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}

	}
}