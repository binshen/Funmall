package com.ksls.funmall.base;

import org.json.JSONArray;
import org.json.JSONObject;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ksls.funmall.manager.ErrorManager;

public class AqObjectCallback<T> extends AjaxCallback<JSONObject> {

	private AppQuery aq;
	
	public AqObjectCallback(AppQuery aq) {
		this.aq = aq;
	}
	
	@Override
	public void callback(String url, JSONObject json, AjaxStatus status) {
		super.callback(url, json, status);
		
		beforeCallback();

		handleCallback(url, json, status);

		afterCallback();
	}
	
	public void handleCallback(String url, JSONObject json, AjaxStatus status) {}

	public void beforeCallback() {}
	
	public void afterCallback() {}
}
