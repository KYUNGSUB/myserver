package com.talanton.service.myweb.group.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.talanton.service.myweb.common.Constants;
import com.talanton.service.myweb.group.dao.FcmGroupDao;
import com.talanton.service.myweb.group.dao.FcmGroupDeviceDao;
import com.talanton.service.myweb.group.model.AddGroup;
import com.talanton.service.myweb.jdbc.JdbcUtil;

public class RegistGroupService {
	private static RegistGroupService instance = new RegistGroupService();
	public static RegistGroupService getInstance() {
		return instance;
	}
	private RegistGroupService() { }
	
	public void addGroup(AddGroup addGroup, List<String> tList) throws IOException, NamingException {
		String response = registGroupToFcmServer(addGroup.getGroupName(), tList);
		String notification_key = parseResponse(response);
		FcmGroupDao fDao = FcmGroupDao.getInstance();
		FcmGroupDeviceDao dDao = FcmGroupDeviceDao.getInstance();
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			conn.setAutoCommit(false);
			List<String> dList = addGroup.getList();
			int gid = fDao.addGroupName(conn, addGroup.getGroupName(), notification_key);
			if(gid > 0) {	// success add group
				for(String deviceId : dList) {
					dDao.addGroupDevice(conn, gid, deviceId);
				}
				conn.commit();
			}
		} catch(SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		}finally {
			JdbcUtil.close(conn);
		}
	}
	
	private String parseResponse(String response) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(response);
		JsonObject jsonObj = element.getAsJsonObject();
		return jsonObj.get("notification_key").getAsString();
	}
	
	private String registGroupToFcmServer(String groupName, List<String> tList) throws IOException {
		String apiKey = (String)Constants.getParameter("fcm_server_key");
		String sender_id = (String)Constants.getParameter("fcm_sender_id");
        URL url = new URL(Constants.FCM_NOTIFICATION_SERVER_ADDRESS);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key=" + apiKey);
        conn.setRequestProperty("project_id", sender_id);

        conn.setDoOutput(true);

        StringBuilder input = new StringBuilder("{ ");
       	input.append("\"operation\" : \"create\",");
        input.append("\"notification_key_name\" : \"").append(groupName).append("\", ");

       	input.append("\"registration_ids\" : [");
        for(int i = 0;i < tList.size();i++) {
        	input.append("\"");
        	input.append(tList.get(i)).append("\",");
        }
        input.deleteCharAt(input.length() - 1);
        input.append("]}");

        OutputStream os = conn.getOutputStream();
        
        // 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
        os.write(input.toString().getBytes("UTF-8"));
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
//        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Post parameters : " + input.toString());
//        System.out.println("Response Code : " + responseCode);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // print result
        System.out.println(response.toString());
        return response.toString();
	}
}
