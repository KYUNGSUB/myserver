package com.talanton.service.myweb.token.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.token.model.AccessToken;

public class AccessTokenDao {
	private static AccessTokenDao instance = new AccessTokenDao();
	public static AccessTokenDao getInstance() {
		return instance;
	}
	private AccessTokenDao() { }
	
	public int insert(Connection conn, AccessToken accessToken) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			String query = "insert into access_token (deviceId, token,createdAt) values(?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, accessToken.getDeviceId());
			pstmt.setString(2, accessToken.getToken());
			pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
			result = pstmt.executeUpdate();
		}
		finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return result;
	}
	
	public boolean checkExist(Connection conn, String deviceId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			String query = "select token from access_token where deviceId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, deviceId);
			rs = pstmt.executeQuery();
			if(rs.next()) {	// exist
				result = true;
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return result;
	}
	
	public int update(Connection conn, AccessToken accessToken) throws SQLException {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			String query = "update access_token set token = ? where deviceId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, accessToken.getToken());
			pstmt.setString(2, accessToken.getDeviceId());
			result = pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return result;
	}
	
	public String getAccessToken(Connection conn, String deviceId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String query = "select token from access_token where deviceId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, deviceId);
			rs = pstmt.executeQuery();
			if(rs.next()) {	// exist
				result = rs.getString(1);
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return result;
	}
	
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from access_token");
			rs.next();
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	public List<AccessToken> select(Connection conn, int firstRow, int endRow) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from access_token order by createdAt desc limit ?, ?");
			
			pstmt.setInt(1, firstRow - 1);
			pstmt.setInt(2, endRow - firstRow + 1);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return Collections.emptyList();
			}
			List<AccessToken> itemList = new ArrayList<AccessToken>();
			do {
				AccessToken token = makeAccessTokenFromResultSet(rs);
				itemList.add(token);
			} while (rs.next());
			return itemList;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	private AccessToken makeAccessTokenFromResultSet(ResultSet rs) throws SQLException {
		AccessToken token = new AccessToken();
		token.setDeviceId(rs.getString("deviceId"));
		token.setToken(rs.getString("token"));
		token.setCreatedAt(rs.getTimestamp("createdAt"));
		token.setModifiedAt(rs.getTimestamp("modifiedAt"));
		return token;
	}
	
	public AccessToken selectById(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from access_token "
					+ "where deviceId = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			AccessToken item = makeAccessTokenFromResultSet(rs);
			return item;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public int delete(Connection conn, String deviceId) throws SQLException {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement("delete from access_token "
					+ "where deviceId = ?");
			pstmt.setString(1, deviceId);
			result = pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return result;
	}
}