package com.talanton.service.myweb.fcm.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FcmHistory {
	private int id;
	private int mid;	// Fcm_Message id
	private long multicast_id;
	private int success;
	private int failure;
	private int canonical_ids;
	private Timestamp createdAt;
	private List<String> list = new ArrayList<String>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public long getMulticast_id() {
		return multicast_id;
	}
	public void setMulticast_id(long multicast_id) {
		this.multicast_id = multicast_id;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getFailure() {
		return failure;
	}
	public void setFailure(int failure) {
		this.failure = failure;
	}
	public int getCanonical_ids() {
		return canonical_ids;
	}
	public void setCanonical_ids(int canonical_ids) {
		this.canonical_ids = canonical_ids;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public boolean hasList() {
		return list.isEmpty();
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public void putMessage_id(String message_id) {
		list.add(message_id);
	}
	public String getMessage_id(int index) {
		return list.get(index);
	}
}