package com.talanton.service.myweb.member.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.member.dao.MemberDao;
import com.talanton.service.myweb.member.model.Member;

public class ReadMemberService {

	private static ReadMemberService instance = new ReadMemberService();

	public static ReadMemberService getInstance() {
		return instance;
	}

	private ReadMemberService() {
	}

	public Member getMember(String id) throws MemberNotFoundException, NamingException {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			MemberDao memberDao = new MemberDao();
			Member member = memberDao.selectById(conn, id);
			if (member == null) {
				throw new MemberNotFoundException("존재하지 않는 사용자:" + id);
			}
			return member;
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 에러: " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public boolean idCheck(String uid) throws NamingException {
		Connection conn = null;
		Member member = null;
		try {
			conn = JdbcUtil.getConnection();
			MemberCheckHelper mch = new MemberCheckHelper();
			member = mch.checkExists(conn, uid);
			
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 에러: " + e.getMessage(), e);
		} catch (MemberNotFoundException e) {
			
		} finally {
			JdbcUtil.close(conn);
		}
		return member != null;
	}
	
	public boolean emailCheck(String email) throws NamingException {
		Connection conn = null;
		Member member = null;
		try {
			conn = JdbcUtil.getConnection();
			MemberCheckHelper mch = new MemberCheckHelper();
			member = mch.checkEmailExists(conn, email);
			
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 에러: " + e.getMessage(), e);
		} catch (MemberNotFoundException e) {
			
		} finally {
			JdbcUtil.close(conn);
		}
		return member != null;
	}
}