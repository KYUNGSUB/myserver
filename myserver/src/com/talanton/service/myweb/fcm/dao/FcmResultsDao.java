package com.talanton.service.myweb.fcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.talanton.service.myweb.jdbc.JdbcUtil;

public class FcmResultsDao {
	private static FcmResultsDao instance = new FcmResultsDao();
	public static FcmResultsDao getInstance() {
		return instance;
	}
	private FcmResultsDao() { }
	
	public int insert(Connection conn, long multicast_id, String message_id) throws SQLException {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			String query = "insert into fcm_results values(?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, multicast_id);
			pstmt.setString(2, message_id);
			result = pstmt.executeUpdate();
		}
		finally {
			JdbcUtil.close(pstmt);
		}
		return result;
	}
}