package com.talanton.service.myweb.pds.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.pds.dao.PdsItemDao;
import com.talanton.service.myweb.pds.model.AddRequest;
import com.talanton.service.myweb.pds.model.PdsItem;

public class AddPdsItemService {

	private static AddPdsItemService instance = new AddPdsItemService();
	public static AddPdsItemService getInstance() {
		return instance;
	}
	
	private AddPdsItemService() {
	}
	
	public PdsItem add(AddRequest request) {
		PdsItemDao pDao = new PdsItemDao();
		Connection conn = null;
		PdsItem pdsItem = null;
		try {
			conn = JdbcUtil.getConnection();
			pdsItem = request.toPdsItem();
			int id = pDao.insert(conn, pdsItem);
			if (id == -1) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException("DB 처리 오류");
			}
			pdsItem.setId(id);
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (NamingException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
		return pdsItem;
	}
}