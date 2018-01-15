package com.talanton.service.myweb.pds.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.pds.dao.PdsItemDao;
import com.talanton.service.myweb.pds.model.PdsItem;

public class ModifyPdsItemService {
	private static ModifyPdsItemService instance = new ModifyPdsItemService();
	public static ModifyPdsItemService getInstance() {
		return instance;
	}
	private ModifyPdsItemService() { }
	
	public int modifyPdsItem(PdsItem pdsItem) throws NamingException {
		int result = -1;
		PdsItemDao pDao = new PdsItemDao();
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			convertPdsItemInfo(pdsItem);
			result = pDao.updatePdsItem(conn, pdsItem);
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류:" + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
		return result;
	}
	
	private void convertPdsItemInfo(PdsItem pdsItem) {
		String description = pdsItem.getDescription();
		pdsItem.setDescription("classic");
		pdsItem.setArticleId(Long.valueOf(description));
		pdsItem.setKind("");
	}
}