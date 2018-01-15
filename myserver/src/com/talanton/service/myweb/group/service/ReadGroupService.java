package com.talanton.service.myweb.group.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.talanton.service.myweb.group.dao.FcmGroupDao;
import com.talanton.service.myweb.group.dao.FcmGroupDeviceDao;
import com.talanton.service.myweb.group.model.GroupInfo;
import com.talanton.service.myweb.group.model.GroupName;
import com.talanton.service.myweb.jdbc.JdbcUtil;

public class ReadGroupService {
	private static ReadGroupService instance = new ReadGroupService();
	public static ReadGroupService getInstance() {
		return instance;
	}
	private ReadGroupService() { }
	
	public GroupInfo getGroupInfo(int gid) throws NamingException, GroupNotFoundException {
		FcmGroupDao fDao = FcmGroupDao.getInstance();
		FcmGroupDeviceDao dDao = FcmGroupDeviceDao.getInstance();
		Connection conn = null;
		GroupInfo info = null;
		try {
			conn = JdbcUtil.getConnection();
			GroupName group = fDao.getGroupName(conn, gid);
			if(group == null) {
				throw new GroupNotFoundException("그룹이 존재하지 않음 :" + gid);
			}
			else {
				info = new GroupInfo();
				info.setGroupName(group);
			}
			info.setdList(dDao.getDeviceList(conn, gid));
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
		return info;
	}
	
	public GroupName getGroupName(int gid) throws NamingException, GroupNotFoundException {
		FcmGroupDao fDao = FcmGroupDao.getInstance();
		Connection conn = null;
		GroupName group = null;
		try {
			conn = JdbcUtil.getConnection();
			group = fDao.getGroupName(conn, gid);
			if(group == null) {
				throw new GroupNotFoundException("그룹이 존재하지 않음 :" + gid);
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
		return group;
	}
}