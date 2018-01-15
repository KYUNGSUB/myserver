package com.talanton.service.myweb.group.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.group.dao.FcmGroupDao;
import com.talanton.service.myweb.jdbc.JdbcUtil;

public class CheckGroupService {
	private static CheckGroupService instance = new CheckGroupService();
	public static CheckGroupService getInstance() {
		return instance;
	}
	private CheckGroupService() { }
	
	public boolean checkDuplicate(String groupName) throws NamingException {
		FcmGroupDao fDao = FcmGroupDao.getInstance();
		Connection conn = null;
		boolean result = false;
		try {
			conn = JdbcUtil.getConnection();
			result = fDao.checkDuplicateGroup(conn, groupName);
		} catch(SQLException ex) {
			throw new RuntimeException("DB 처리 오류 : " + ex.getMessage(), ex);
		} finally {
			JdbcUtil.close(conn);
		}
		return result;
	}
}