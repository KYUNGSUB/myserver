package com.talanton.service.myweb.group.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.talanton.service.myweb.common.Constants;
import com.talanton.service.myweb.fcm.dao.FcmHistoryDao;
import com.talanton.service.myweb.fcm.dao.FcmMessageDao;
import com.talanton.service.myweb.fcm.dao.FcmResultsDao;
import com.talanton.service.myweb.fcm.model.FcmHistory;
import com.talanton.service.myweb.fcm.model.FcmMessage;
import com.talanton.service.myweb.fcm.model.Notification;
import com.talanton.service.myweb.group.dao.FcmGroupDao;
import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.listener.CommonParameter;
import com.talanton.service.myweb.xmpp.CcsClient;
import com.talanton.service.myweb.xmpp.CcsClient.GetXmppDownStreamAck;
import com.talanton.service.myweb.xmpp.messages.DownstreamMessage;

public class SendGroupFcmService implements GetXmppDownStreamAck {
	private static SendGroupFcmService instance = new SendGroupFcmService();
	public static SendGroupFcmService getInstance() {
		return instance;
	}
	private SendGroupFcmService() { }
	
	private JsonAdapter<DownstreamMessage.Request> mDownstreamRequestAdapter;
    private JsonAdapter<DownstreamMessage.Response> mDownstreamResponseAdapter;

	public boolean sendFcmByHttp(FcmMessage msg) throws Exception {
		FcmGroupDao fDao = FcmGroupDao.getInstance();
		Connection conn = null;
		boolean result = false;
		try {
			conn = JdbcUtil.getConnection();
			String token = fDao.getNotificationKey(conn, Integer.parseInt(msg.getDeviceId()));
			String response = sendGroupFcmRequestToFirebase(token, msg);
			FcmHistory fh = parseResponse(conn, response);
			int mid = storeFcmSendMessage(conn, msg);
			if(mid != -1)
				fh.setMid(mid);
			if(fh.getFailure() != 0)
				storeFcmSendHistory(conn, fh);
			else
				result = true;
		} catch(SQLException e) {
			conn.rollback();
			throw new RuntimeException("DB 처리 오류:" + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
		
		return result;
	}
	
	private int storeFcmSendMessage(Connection conn, FcmMessage msg) throws NamingException {
		FcmMessageDao fDao = FcmMessageDao.getInstance();
		int result = -1;
		try {
			result = fDao.insert(conn, msg);
		} catch(SQLException ex) {
			throw new RuntimeException("DB 처리 오류 : " + ex.getMessage(), ex);
		}
		return result;
	}
	
	private int storeFcmSendHistory(Connection conn, FcmHistory fh) throws NamingException {
		FcmHistoryDao fDao = FcmHistoryDao.getInstance();
		int result = -1;
		try {
			if(fDao.insert(conn, fh) == 1) {	// success store FcmHistory
				FcmResultsDao rDao = FcmResultsDao.getInstance();
				List<String> rList = fh.getList();
				for(int i = 0;i < rList.size();i++) {
					rDao.insert(conn, fh.getMulticast_id(), rList.get(i));
				}
				result = 1;
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		}
		return result;
	}
	
	private FcmHistory parseResponse(Connection conn, String response) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(response);
		JsonObject jsonObj = element.getAsJsonObject();
		FcmHistory fh = new FcmHistory();
		fh.setSuccess(jsonObj.get("success").getAsInt());
		fh.setFailure(jsonObj.get("failure").getAsInt());
		if(fh.getFailure() > 0) {
			JsonArray jsonArray = jsonObj.getAsJsonArray("failed_registration_ids");
			for(int i = 0;i < jsonArray.size();i++) {
				JsonObject midObj = jsonArray.get(i).getAsJsonObject();
				fh.putMessage_id(midObj.toString());
			}
		}
		return fh;
	}

	private String sendGroupFcmRequestToFirebase(String token, FcmMessage msg) throws Exception {
		String apiKey = (String)Constants.getParameter("fcm_server_key");
        URL url = new URL(Constants.FCM_SERVER_ADDRESS);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key=" + apiKey);

        conn.setDoOutput(true);

        StringBuilder input = new StringBuilder("{ ");
        Notification noti = msg.getNotification();
        if(noti != null) {
        	input.append("\"notification\" : {\"title\" : \"").append(noti.getTitle()).append("\", ");
            input.append("\"body\" : \"").append(noti.getBody()).append("\"}, ");
        }
        
        Map<String, String> data = msg.getData();
        if(data != null && data.size() > 0) {
        	input.append("\"data\" : {");
        	for(Map.Entry<String, String> entry : data.entrySet()) {
        		input.append("\"");
        		input.append(entry.getKey()).append("\" : \"");
        		input.append(entry.getValue()).append("\",");
        	}
        	input.deleteCharAt(input.length() - 1);
        	input.append("},");
        }
        
        input.append("\"to\":\"").append(token).append("\"}");

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
	
	public boolean sendFcmByXmpp(FcmMessage msg) throws SQLException, NamingException {
		CommonParameter cp = CommonParameter.getInstance();
		CcsClient ccsClient = CcsClient.prepareClient((String)cp.get("fcm_sender_id"), (String)cp.get("fcm_server_key"), false);
		Moshi moshi = new Moshi.Builder().build();
		mDownstreamRequestAdapter = moshi.adapter(DownstreamMessage.Request.class);
        mDownstreamResponseAdapter = moshi.adapter(DownstreamMessage.Response.class);
        FcmGroupDao gDao = FcmGroupDao.getInstance();
        FcmMessageDao fDao = FcmMessageDao.getInstance();
		Connection conn = null;
		boolean result = false;
		try {
			conn = JdbcUtil.getConnection();
			String to = gDao.getNotificationKey(conn, Integer.parseInt(msg.getDeviceId()));
			if(!to.isEmpty()) {	// 등록된 사용자
				storeFcmSendMessage(conn, msg);
		        String message_id = Integer.toString(fDao.getLastId(conn));
				String jsonRequest = mDownstreamRequestAdapter.toJson(new DownstreamMessage.Request(to, message_id, msg.getData()));
				ccsClient.registerCallback(this);
				ccsClient.send(jsonRequest);
				result = true;
			}
		} catch(SQLException e) {
			conn.rollback();
			throw new RuntimeException("DB 처리 오류:" + e.getMessage(), e);
		} finally {
			JdbcUtil.close(conn);
		}
		
		return result;
	}

	@Override
	public void callbackXmppAck(int messageId) throws NamingException {
		FcmMessageDao fDao = FcmMessageDao.getInstance();
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			fDao.updateXmppResult(conn, messageId);
		} catch(SQLException ex) {
			throw new RuntimeException("DB 처리 오류 : " + ex.getMessage(), ex);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}