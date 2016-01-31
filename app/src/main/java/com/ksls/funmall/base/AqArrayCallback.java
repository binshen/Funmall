package com.ksls.funmall.base;

import org.json.JSONArray;
import org.json.JSONObject;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ksls.funmall.manager.ErrorManager;

public class AqArrayCallback<T> extends AjaxCallback<JSONArray> {

	private AppQuery aq;
	
	public AqArrayCallback(AppQuery aq) {
		this.aq = aq;
	}
	
	@Override
	public void callback(String url, JSONArray json, AjaxStatus status) {
		super.callback(url, json, status);
		
		beforeCallback();

		handleCallback(url, json, status);

		afterCallback();
	}
	
	public void handleCallback(String url, JSONArray json, AjaxStatus status) {}

	public void beforeCallback() {}
	
	public void afterCallback() {}
}
