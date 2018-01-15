package com.talanton.service.myweb.fcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.talanton.service.myweb.fcm.model.FcmHistory;
import com.talanton.service.myweb.jdbc.JdbcUtil;

public class FcmHistoryDao {
	private static FcmHistoryDao instance = new FcmHistoryDao();
	public static FcmHistoryDao getInstance() {
		return instance;
	}
	private FcmHistoryDao() { }
	
	public int insert(Connection conn, FcmHistory fh) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			String query = "insert into fcm_history (mid, multicast_id, success, failure, canonical_ids) values(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fh.getMid());
			pstmt.setLong(2, fh.getMulticast_id());
			pstmt.setInt(3, fh.getSuccess());
			pstmt.setInt(4, fh.getFailure());
			pstmt.setInt(5, fh.getCanonical_ids());
			result = pstmt.executeUpdate();
		}
		finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return result;
	}
}