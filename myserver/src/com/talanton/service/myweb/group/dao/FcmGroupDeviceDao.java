package com.talanton.service.myweb.group.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talanton.service.myweb.jdbc.JdbcUtil;

public class FcmGroupDeviceDao {
	private static FcmGroupDeviceDao instance = new FcmGroupDeviceDao();
	public static FcmGroupDeviceDao getInstance() {
		return instance;
	}
	private FcmGroupDeviceDao() { }
	
	public int addGroupDevice(Connection conn, int gid, String deviceId) throws SQLException {
		PreparedStatement pstmt = null;
		int result = -1;
		
		try {
			pstmt = conn.prepareStatement("insert into fcm_group_device (gid, deviceId) "
					+ "values (?, ?)");
			pstmt.setInt(1, gid);
			pstmt.setString(2, deviceId);
			result = pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return result;
	}
	
	public List<String> getDeviceList(Connection conn, int gid) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = null;
		
		try {
			pstmt = conn.prepareStatement("select * from fcm_group_device where gid = ?");
			pstmt.setInt(1, gid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<String>();
				do {
					list.add(rs.getString("deviceId"));
				} while(rs.next());
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return list;
	}
}