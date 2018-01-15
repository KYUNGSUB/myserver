package com.talanton.service.myweb.settings.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.settings.dao.SettingItemDao;
import com.talanton.service.myweb.settings.model.SettingItem;

public class ReadSettingItemService {

	private static ReadSettingItemService instance = new ReadSettingItemService();

	public static ReadSettingItemService getInstance() {
		return instance;
	}

	private ReadSettingItemService() {
	}

	public SettingItem getSettingItem(int id) throws SettingItemNotFoundException, NamingException {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			SettingItem settingItem = SettingItemDao.getInstance().selectById(conn, id);
			if (settingItem == null) {
				throw new SettingItemNotFoundException("파라미터가 존재하지 않음 :" + id);
			}
			return settingItem;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public SettingItem getSettingItem(String name) throws SettingItemNotFoundException, NamingException {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			SettingItem settingItem = SettingItemDao.getInstance().selectByParameterName(conn, name);
			if (settingItem == null) {
				throw new SettingItemNotFoundException("파라미터가 존재하지 않음 :" + name);
			}
			return settingItem;
		} catch (SQLException e) {
			throw new RuntimeException("DB DB 처리 오류 : " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}