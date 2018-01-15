package com.talanton.service.myweb.listener;

import java.util.HashMap;
import java.util.Map;

public class CommonParameter {
	private static CommonParameter instance;
	public static CommonParameter getInstance() {
		if(instance == null) {
			instance = new CommonParameter();
		}
		return instance;
	}
	private CommonParameter() {
		params = new HashMap<String, Object>();
	}
	
	Map<String, Object> params;
	
	public void put(String key, Object value) {
		params.put(key, value);
	}
	
	public Object get(String key) {
		return params.get(key);
	}
}