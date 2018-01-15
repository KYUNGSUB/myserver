package com.talanton.service.myweb.fcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.talanton.service.myweb.fcm.model.FcmMessage;
import com.talanton.service.myweb.group.model.FcmGroupMessage;
import com.talanton.service.myweb.jdbc.JdbcUtil;

public class FcmMessageDao {
	private static FcmMessageDao instance = new FcmMessageDao();
	public static FcmMessageDao getInstance() {
		return instance;
	}
	private FcmMessageDao() { }
	
	public int insert(Connection conn, FcmMessage msg) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			String query;
			if(msg.getNotification() != null && msg.getData() != null) {
				query = "insert into fcm_message (destination, method, title, body, data) values(?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, msg.getDeviceId());
				pstmt.setString(2, msg.getMethod());
				pstmt.setString(3, msg.getNotification().getTitle());
				pstmt.setString(4, msg.getNotification().getBody());
				StringBuilder input = new StringBuilder();
				input.append("{");
				for(Map.Entry<String, String> entry : msg.getData().entrySet()) {
	        		input.append("\"").append(entry.getKey()).append("\": ");
	        		input.append("\"").append(entry.getValue()).append("\",");
	        	}
				input.deleteCharAt(input.length() - 1);
	        	input.append("}");
	        	pstmt.setString(5, input.toString());
			}
			else if(msg.getNotification() == null && msg.getData() != null) {
				query = "insert into fcm_message (destination, method, data) values(?, ?, ?)";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, msg.getDeviceId());
				pstmt.setString(2, msg.getMethod());
				StringBuilder input = new StringBuilder();
				input.append("{");
				for(Map.Entry<String, String> entry : msg.getData().entrySet()) {
	        		input.append("\"").append(entry.getKey()).append("\": ");
	        		input.append("\"").append(entry.getValue()).append("\",");
	        	}
				input.deleteCharAt(input.length() - 1);
	        	input.append("}");
	        	pstmt.setString(3, input.toString());
			}
			else if(msg.getNotification() != null && msg.getData() == null) {
				query = "insert into fcm_message (destination, method, title, body) values(?, ?, ?, ?)";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, msg.getDeviceId());
				pstmt.setString(2, msg.getMethod());
				pstmt.setString(3, msg.getNotification().getTitle());
				pstmt.setString(4, msg.getNotification().getBody());
			}
			else {
				query = "insert into fcm_message (destination, method) values(?, ?)";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, msg.getDeviceId());
				pstmt.setString(2, msg.getMethod());
			}
			
			int insertedCount = pstmt.executeUpdate();
			if (insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select last_insert_id() from fcm_message");
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
		return result;
	}
	
	public int insert(Connection conn, FcmGroupMessage msg) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			String query;
			query = "insert into fcm_message (destination, method, data) values(?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, msg.getGroupName());
			pstmt.setString(2, msg.getMethod());
			StringBuilder input = new StringBuilder();
			input.append("{");
			for(Map.Entry<String, String> entry : msg.getData().entrySet()) {
	        	input.append("\"").append(entry.getKey()).append("\": ");
	        	input.append("\"").append(entry.getValue()).append("\",");
	        }
			input.deleteCharAt(input.length() - 1);
	        input.append("}");
	        pstmt.setString(3, input.toString());
	        	
			int insertedCount = pstmt.executeUpdate();
			if (insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select last_insert_id() from fcm_message");
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
		return result;
	}
	
	public int getLastId(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select max(id) from fcm_message");
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		return result;
	}

	public int updateXmppResult(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement("update fcm_message set xmpp='C' where id = ?");
			pstmt.setInt(1, messageId);
			result = pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return result;
	}
}