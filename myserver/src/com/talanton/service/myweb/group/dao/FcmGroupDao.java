package com.talanton.service.myweb.group.dao;

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

import com.talanton.service.myweb.group.model.GroupName;
import com.talanton.service.myweb.jdbc.JdbcUtil;

public class FcmGroupDao {
	private static FcmGroupDao instance = new FcmGroupDao();
	public static FcmGroupDao getInstance() {
		return instance;
	}
	private FcmGroupDao() { }
	
	public boolean checkDuplicateGroup(Connection conn, String groupName) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			pstmt = conn.prepareStatement("select gid from fcm_group_name where notification_key_name = ?");
			pstmt.setString(1, groupName);
			rs = pstmt.executeQuery();
			if(rs.next()) {	// 그룹 중복
				result = true;
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return result;
	}
	
	public int addGroupName(Connection conn, String groupName, String notification_key) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			pstmt = conn.prepareStatement("insert into fcm_group_name (notification_key_name, notification_key, createdAt) "
					+ "values (?, ?, ?)");
			pstmt.setString(1, groupName);
			pstmt.setString(2, notification_key);
			pstmt.setTimestamp(3, new Timestamp((new Date()).getTime()));
			int insertedCount = pstmt.executeUpdate();
			if(insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select last_insert_id() from fcm_group_name");
				if(rs.next()) {
					return rs.getInt(1);
				}
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
		return result;
	}
	
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from fcm_group_name");
			rs.next();
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	public List<GroupName> select(Connection conn, int firstRow, int endRow) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from fcm_group_name order by createdAt desc limit ?, ?");
			
			pstmt.setInt(1, firstRow - 1);
			pstmt.setInt(2, endRow - firstRow + 1);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return Collections.emptyList();
			}
			List<GroupName> itemList = new ArrayList<GroupName>();
			do {
				GroupName group = makeGroupNameFromResultSet(rs);
				itemList.add(group);
			} while (rs.next());
			return itemList;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	private GroupName makeGroupNameFromResultSet(ResultSet rs) throws SQLException {
		GroupName group = new GroupName();
		group.setGid(rs.getInt("gid"));
		group.setNotification_key_name(rs.getString("notification_key_name"));
		group.setNotification_key(rs.getString("notification_key"));
		group.setCreatedAt(rs.getTimestamp("createdAt"));
		group.setModifiedAt(rs.getTimestamp("modifiedAt"));
		return group;
	}
	
	public GroupName getGroupName(Connection conn, int gid) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GroupName group = null;
		try {
			pstmt = conn.prepareStatement("select * from fcm_group_name where gid = ?");
			
			pstmt.setInt(1, gid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				group = makeGroupNameFromResultSet(rs);
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return group;
	}
	
	public String getNotificationKey(Connection conn, int gid) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String token = null;
		try {
			pstmt = conn.prepareStatement("select notification_key from fcm_group_name where gid = ?");
			pstmt.setInt(1, gid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				token = rs.getString(1);
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return token;
	}
}