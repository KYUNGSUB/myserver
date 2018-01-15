package com.talanton.service.myweb.fcm.service;

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
import com.talanton.service.myweb.jdbc.JdbcUtil;
import com.talanton.service.myweb.listener.CommonParameter;
import com.talanton.service.myweb.token.dao.AccessTokenDao;
import com.talanton.service.myweb.xmpp.CcsClient;
import com.talanton.service.myweb.xmpp.CcsClient.GetXmppDownStreamAck;
import com.talanton.service.myweb.xmpp.messages.DownstreamMessage;

public class SendFcmService implements GetXmppDownStreamAck {
	private static SendFcmService instance = new SendFcmService();
	public static SendFcmService getInstance() {
		return instance;
	}
	private SendFcmService() { }
	
	private JsonAdapter<DownstreamMessage.Request> mDownstreamRequestAdapter;
    private JsonAdapter<DownstreamMessage.Response> mDownstreamResponseAdapter;

	public boolean sendFcmByHttp(FcmMessage msg) throws Exception {
		AccessTokenDao aDao = AccessTokenDao.getInstance();
		Connection conn = null;
		boolean result = false;
		try {
			conn = JdbcUtil.getConnection();
			String token = aDao.getAccessToken(conn, msg.getDeviceId());
			if(!token.isEmpty()) {	// 등록된 사용자
				String response = sendFcmRequestToFirebase(token, msg);
				FcmHistory fh = parseResponse(conn, response, msg);
				int mid = storeFcmSendMessage(conn, msg);
				if(mid != -1)
					fh.setMid(mid);
				if(fh.getFailure() != 0 || fh.getCanonical_ids() != 0)
					storeFcmSendHistory(conn, fh);
				else
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
	
	private int storeFcmSendMessage(Connection conn, FcmMessage msg) throws NamingException {
		FcmMessageDao fDao = FcmMessageDao.getInstance();
//		Connection conn = null;
		int result = -1;
		try {
//			conn = JdbcUtil.getConnection();
			result = fDao.insert(conn, msg);
		} catch(SQLException ex) {
//			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB 처리 오류 : " + ex.getMessage(), ex);
		} finally {
//			JdbcUtil.close(conn);
		}
		
		return result;
	}
	
	private int storeFcmSendHistory(Connection conn, FcmHistory fh) throws NamingException {
		FcmHistoryDao fDao = FcmHistoryDao.getInstance();
//		Connection conn = null;
		int result = -1;
		try {
//			conn = JdbcUtil.getConnection();
			if(fDao.insert(conn, fh) == 1) {	// success store FcmHistory
				FcmResultsDao rDao = FcmResultsDao.getInstance();
				List<String> rList = fh.getList();
				for(int i = 0;i < rList.size();i++) {
					rDao.insert(conn, fh.getMulticast_id(), rList.get(i));
				}
				result = 1;
			}
		} catch (SQLException e) {
//			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		} finally {
//			JdbcUtil.close(conn);
		}
		return result;
	}
	
	private FcmHistory parseResponse(Connection conn, String response, FcmMessage msg) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(response);
		JsonObject jsonObj = element.getAsJsonObject();
		FcmHistory fh = new FcmHistory();
		fh.setMulticast_id(jsonObj.get("multicast_id").getAsLong());
		fh.setSuccess(jsonObj.get("success").getAsInt());
		fh.setFailure(jsonObj.get("failure").getAsInt());
		fh.setCanonical_ids(jsonObj.get("canonical_ids").getAsInt());
		JsonArray jsonArray = jsonObj.getAsJsonArray("results");
		for(int i = 0;i < jsonArray.size();i++) {
			JsonObject midObj = jsonArray.get(i).getAsJsonObject();
			fh.putMessage_id(midObj.toString());
			if(midObj.get("message_id") == null) {
				String reason = midObj.get("error").getAsString();
				if(reason.equals("Unavailable")) {	// 재전송
						System.out.println("FCM Send error(Unavailable): " + msg.getDeviceId());
				}
				else if(reason.equals("InvalidRegistration")) {	// 복구 불가능 오류
					System.out.println("FCM Send error(InvalidRegistration): " + msg.getDeviceId());
				}
				else if(reason.equals("NotRegistered")) {	// 토큰 정보를 삭제해야 함
					System.out.println("FCM Send error(NotRegistered): " + msg.getDeviceId());
					int result = deleteAccessTokenFromDB(conn, msg.getDeviceId());
					System.out.println("Device " + msg.getDeviceId() + "removed : " + result);
				}
			}
			else {
				if(midObj.get("registration_id") != null) {	// 등록 토큰을 변경해야 함
					String resitration_id = midObj.get("registration_id").getAsString();
					System.out.println("registration_id = " + resitration_id);
				}
			}
		}
		return fh;
	}
	
	private int deleteAccessTokenFromDB(Connection conn, String deviceId) {
		AccessTokenDao aDao = AccessTokenDao.getInstance();
		int result = -1;
		try {
			result = aDao.delete(conn, deviceId);	// remove access token
		} catch (SQLException e) {
			throw new RuntimeException("DB 처리 오류 : " + e.getMessage(), e);
		}
		return result;
	}
	
	private String sendFcmRequestToFirebase(String token, FcmMessage msg) throws Exception {
		final String apiKey = (String)Constants.getParameter("fcm_server_key");
        URL url = new URL(Constants.FCM_SERVER_ADDRESS);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key=" + apiKey);

        conn.setDoOutput(true);

        // 이렇게 보내면 주제를 ALL로 지정해놓은 모든 사람들한테 알림을 날려준다.
        // String input = "{\"notification\" : {\"title\" : \"여기다 제목 넣기 \", \"body\" : \"여기다 내용 넣기\"}, \"to\":\"/topics/ALL\"}";
        
        // 이걸로 보내면 특정 토큰을 가지고있는 어플에만 알림을 날려준다  위에 둘중에 한개 골라서 날려주자
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
        // String input = "{\"notification\" : {\"title\" : \"여기다 제목넣기 \", \"body\" : \"여기다 내용 넣기\"}, \"to\":\" 여기가 받을 사람 토큰  \"}";

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
        AccessTokenDao aDao = AccessTokenDao.getInstance();
        FcmMessageDao fDao = FcmMessageDao.getInstance();
		Connection conn = null;
		boolean result = false;
		try {
			conn = JdbcUtil.getConnection();
			String to = aDao.getAccessToken(conn, msg.getDeviceId());
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