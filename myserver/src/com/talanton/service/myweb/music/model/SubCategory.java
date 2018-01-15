package com.talanton.service.myweb.music.model;

import java.util.ArrayList;
import java.util.List;

public class SubCategory extends MusicCategory {
	private int mCode;	// main code
	private List<Information> iList = new ArrayList<Information>();
	
	public int getmCode() {
		return mCode;
	}
	public void setmCode(int mCode) {
		this.mCode = mCode;
	}
	public List<Information> getiList() {
		return iList;
	}
	public void setiList(List<Information> iList) {
		this.iList = iList;
	}
}