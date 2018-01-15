package com.talanton.service.myweb.settings.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.settings.model.SettingItem;

public class SettingItemDao {
	
	private static SettingItemDao instance = new SettingItemDao();
	public static SettingItemDao getInstance() {
		return instance;
	}
	
	private SettingItemDao() {
	}

	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from setting_item");
			rs.next();
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	public List<SettingItem> select(Connection conn, int firstRow, int endRow) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * "
					+ "from setting_item order by createdAt desc limit ?, ?");
			
			pstmt.setInt(1, firstRow - 1);
			pstmt.setInt(2, endRow - firstRow + 1);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return Collections.emptyList();
			}
			List<SettingItem> itemList = new ArrayList<SettingItem>();
			do {
				SettingItem article = makeItemFromResultSet(rs);
				itemList.add(article);
			} while (rs.next());
			return itemList;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	private SettingItem makeItemFromResultSet(ResultSet rs) throws SQLException {
		SettingItem item = new SettingItem();
		item.setId(rs.getInt("parameterId"));
		item.setParameterName(rs.getString("parameterName"));
		item.setValue(rs.getString("value"));
		item.setCreatedAt(rs.getTimestamp("createdAt"));
		item.setModifiedAt(rs.getTimestamp("modifiedAt"));
		return item;
	}

	public SettingItem selectById(Connection conn, int itemId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from setting_item "
					+ "where parameterId = ?");
			pstmt.setInt(1, itemId);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			SettingItem item = makeItemFromResultSet(rs);
			return item;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}


	public SettingItem selectByParameterName(Connection conn, String parameterName) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from setting_item "
					+ "where parameterName = ?");
			pstmt.setString(1, parameterName);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			SettingItem item = makeItemFromResultSet(rs);
			return item;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	public int insert(Connection conn, SettingItem item) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("insert into setting_item "
					+ "(parameterName, value, createdAt) "
					+ "values (?, ?, now())");
			pstmt.setString(1, item.getParameterName());
			pstmt.setString(2, item.getValue());
			int insertedCount = pstmt.executeUpdate();

			if (insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select last_insert_id() from setting_item");
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
			return -1;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}
	
	public int update(Connection conn, SettingItem settingItem) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update setting_item "
					+ "set parameterName = ?, value = ?, createdAt = ?, modifiedAt = ? where parameterId = ?");
			pstmt.setString(1, settingItem.getParameterName());
			pstmt.setString(2, settingItem.getValue());
			pstmt.setTimestamp(3, settingItem.getCreatedAt());
			pstmt.setTimestamp(4, settingItem.getModifiedAt());
			pstmt.setInt(5, settingItem.getId());
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	public void delete(Connection conn, int itemId) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from setting_item " + "where parameterId = ?");
			pstmt.setInt(1, itemId);
			pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}