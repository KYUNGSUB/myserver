package com.talanton.service.myweb.pds.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.pds.dao.PdsItemDao;
import com.talanton.service.myweb.pds.model.PdsItem;

public class GetPdsItemService {

	private static GetPdsItemService instance = new GetPdsItemService();

	public static GetPdsItemService getInstance() {
		return instance;
	}

	private GetPdsItemService() {
	}

	public PdsItem getPdsItem(int id) throws PdsItemNotFoundException {
		PdsItemDao pDao = new PdsItemDao();
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			PdsItem pdsItem = pDao.selectById(conn, id);
			if (pdsItem == null) {
				throw new PdsItemNotFoundException("해당 파일이 존재하지 않음 :" + id);
			}
			return pdsItem;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		} catch (NamingException e) {
			throw new RuntimeException("Naming 예외 처리 : " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public PdsItem getPdsItemByPid(String pid) {
		PdsItemDao pDao = new PdsItemDao();
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			PdsItem pdsItem = pDao.selectByPid(conn, pid);
			return pdsItem;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		} catch (NamingException e) {
			throw new RuntimeException("Naming 예외 처리 : " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
