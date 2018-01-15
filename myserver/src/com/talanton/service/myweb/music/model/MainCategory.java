package com.talanton.service.myweb.music.model;

import java.util.ArrayList;
import java.util.List;

public class MainCategory extends MusicCategory {
	private List<SubCategory> subCategory = new ArrayList<SubCategory>();
	public List<SubCategory> getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(List<SubCategory> subCategory) {
		this.subCategory = subCategory;
	}
}