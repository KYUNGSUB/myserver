package com.talanton.service.myweb.music.model;

public class MusicCategory {
	private int codeId;
	private String kname;
	private String ename;
	private int mcount;
	
	public int getCodeId() {
		return codeId;
	}
	public void setCodeId(int codeId) {
		this.codeId = codeId;
	}
	public String getKname() {
		return kname;
	}
	public void setKname(String name) {
		this.kname = name;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String name) {
		this.ename = name;
	}
	public int getMcount() {
		return mcount;
	}
	public void setMcount(int mcount) {
		this.mcount = mcount;
	}
}