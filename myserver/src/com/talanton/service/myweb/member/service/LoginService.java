package com.talanton.service.myweb.member.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.member.dao.MemberDao;
import com.talanton.service.myweb.member.model.Member;

public class LoginService {
	private static LoginService instance = new LoginService();
	public static LoginService getInstance() {
		return instance;
	}
	private LoginService() {
		
	}
	
	public Member login(String id, String password) throws NamingException {
		Member member = null;
		MemberDao mDao = new MemberDao();
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			member = mDao.getMember(conn, id, password);
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 :"+e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
		return member;
	}
}