package com.talanton.service.myweb.settings.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.settings.dao.SettingItemDao;
import com.talanton.service.myweb.settings.model.SettingItem;
import com.talanton.service.myweb.settings.model.SettingItemAdd;

public class AddSettingItemService {

	private static AddSettingItemService instance = new AddSettingItemService();
	public static AddSettingItemService getInstance() {
		return instance;
	}
	
	private AddSettingItemService() {
	}
	
	public SettingItem add(SettingItemAdd request) throws NamingException {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			conn.setAutoCommit(false);
			
			SettingItem settingItem = request.toSettingItem();
			
			if(SettingItemDao.getInstance().selectByParameterName(conn, settingItem.getParameterName()) == null) {
				int id = SettingItemDao.getInstance().insert(conn, settingItem);
				if (id == -1) {
					JdbcUtil.rollback(conn);
					throw new RuntimeException("DB 처리 오류 ");
				}
				settingItem.setId(id);
				
				conn.commit();
				
				return settingItem;
			}
			else
				return null;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
				}
			}
			JdbcUtil.close(conn);
		}
	}
}