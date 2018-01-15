package com.talanton.service.myweb.token.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.token.dao.AccessTokenDao;
import com.talanton.service.myweb.token.model.AccessToken;

public class ReadAccessTokenService {

	private static ReadAccessTokenService instance = new ReadAccessTokenService();

	public static ReadAccessTokenService getInstance() {
		return instance;
	}

	private ReadAccessTokenService() {
	}

	public AccessToken getAccessToken(String id) throws AccessTokenNotFoundException, NamingException {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			AccessToken accessToken = AccessTokenDao.getInstance().selectById(conn, id);
			if (accessToken == null) {
				throw new AccessTokenNotFoundException("Device가 존재하지 않음 :" + id);
			}
			return accessToken;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	public List<String> getAccessToken(List<String> dList) throws NamingException {
		AccessTokenDao aDao = AccessTokenDao.getInstance();
		Connection conn = null;
		List<String> aList = null;
		try {
			conn = JdbcUtil.getConnection();
			aList = new ArrayList<String>();
			for(String deviceId : dList) {
				String accessToken = aDao.getAccessToken(conn, deviceId);
				aList.add(accessToken);
			}
		} catch(SQLException e) {
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
		return aList;
	}
}