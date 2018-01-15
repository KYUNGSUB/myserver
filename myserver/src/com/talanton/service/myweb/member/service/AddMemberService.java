package com.talanton.service.myweb.member.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.member.dao.MemberDao;
import com.talanton.service.myweb.member.model.Member;

public class AddMemberService {
	private static AddMemberService instance = new AddMemberService();
	public static AddMemberService getInstance() {
		return instance;
	}
	private AddMemberService() {
		
	}
	
	public int add(Member member) throws NamingException {
		MemberDao mDao = new MemberDao();
		Connection conn = null;
		int result = -1;
		try {
			conn = JdbcUtil.getConnection();
			result = mDao.insert(conn, member);
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB 처리 오류:"+e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
		return result;
	}
}