package com.talanton.service.myweb.token.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.token.dao.AccessTokenDao;
import com.talanton.service.myweb.token.model.AccessToken;

public class AddAccessTokenService {
	private static AddAccessTokenService instance = new AddAccessTokenService();
	public static AddAccessTokenService getInstance() {
		return instance;
	}
	private AddAccessTokenService() { }
	
	public int addAccessToken(AccessToken accessToken) throws NamingException {
		AccessTokenDao aDao = AccessTokenDao.getInstance();
		Connection conn = null;
		int result = -1;
		try {
			conn = JdbcUtil.getConnection();
			if(aDao.checkExist(conn, accessToken.getDeviceId())) {
				result = aDao.update(conn, accessToken);
			}
			else {
				result = aDao.insert(conn, accessToken);
			}
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
		return result;
	}
}