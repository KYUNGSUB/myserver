package com.talanton.service.myweb.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.member.model.Member;

public class MemberDao {
	
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from member");
			rs.next();
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	public Member getMember(Connection conn, String uid, String password) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;
		try {
			String query = "select * from member where uid=? and password=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uid);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = makeMemberFromResultSet(rs);
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return member;
	}
	
	public Member selectById(Connection conn, String uid) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from member where uid = ?");
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			Member item = makeMemberFromResultSet(rs);
			return item;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public Member selectByEmail(Connection conn, String email) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from member where email = ?");
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			Member item = makeMemberFromResultSet(rs);
			return item;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public int userCheck(Connection conn, String uid, String passwd) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = -1;
		try {
			pstmt = conn.prepareStatement("select password from member where uid = ?");
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {	// 해당 id가 존재
				String dbPassword = rs.getString("password");
				if(passwd.equals(dbPassword))
					x = 1;	// 인증 성공
				else
					x = 0;	// 비밀번호 틀림
			}
			else {	// 해당 id가 없으면
				x = -1;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}

		return x;
	}
	
	public Member makeMemberFromResultSet(ResultSet rs) throws SQLException {
		Member member = new Member();
		member.setMid(rs.getInt("mid"));
		member.setUid(rs.getString("uid"));
		member.setPassword(rs.getString("password"));
		member.setName(rs.getString("name"));
		member.setLogin_type(rs.getInt("login_type"));
		member.setEmail(rs.getString("email"));
		member.setCreatedAt(rs.getTimestamp("createdAt"));
		member.setModifiedAt(rs.getTimestamp("modifiedAt"));
		return member;
	}
	
	public int insert(Connection conn, Member member) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			String query = "insert into member (uid, password, name, login_type, email, createdAt"
					+ ") values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, member.getUid());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setInt(4, member.getLogin_type());
			pstmt.setString(5, member.getEmail());
			pstmt.setTimestamp(6, new Timestamp(new Date().getTime()));
			pstmt.executeUpdate();
			ResultSet keys = pstmt.getGeneratedKeys();    
			keys.next();  
			result = keys.getInt(1);
			//System.out.println("result = " + result);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return result;
	}

	public int update(Connection conn, Member member) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			String query = "update member set uid = ?, password = ?, name=?, login_type=?, email=? where mid=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getUid());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setInt(4, member.getLogin_type());
			pstmt.setString(5, member.getEmail());
			pstmt.setInt(6, member.getMid());
			result = pstmt.executeUpdate();
			//System.out.println("result = " + result);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return result;
	}
	
	public void delete(Connection conn, int mid) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from member " + "where mid = ?");
			pstmt.setInt(1, mid);
			pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public List<Member> select(Connection conn, int firstRow, int endRow) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from member order by createdAt desc limit ?, ?");
			
			pstmt.setInt(1, firstRow - 1);
			pstmt.setInt(2, endRow - firstRow + 1);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return Collections.emptyList();
			}
			List<Member> itemList = new ArrayList<Member>();
			do {
				Member member = makeMemberFromResultSet(rs);
				itemList.add(member);
			} while (rs.next());
			return itemList;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
}