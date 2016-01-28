package com.ksls.funmall.base;

import org.json.JSONObject;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ksls.funmall.manager.ErrorManager;

public class AppRequestCallback<T> extends AjaxCallback<JSONObject> {

	private AppQuery aq;
	
	public AppRequestCallback(AppQuery aq) {
		this.aq = aq;
	}
	
	@Override
	public void callback(String url, JSONObject json, AjaxStatus status) {
		super.callback(url, json, status);
		
		beforeCallback();
		
		if(json == null) {
			ErrorManager.showError(aq.getContext(), "CODE: " + status.getCode());
		} else {
//			if(JSONUtil.getInt(json, "auth") == 0) {
//				if(Constants.debug) {
//					Toast.makeText(aq.getContext(), "API无权访问:" + url, Toast.LENGTH_SHORT).show();
//				} else {
//					Toast.makeText(aq.getContext(), "API无权访问", Toast.LENGTH_SHORT).show();
//				}
//			} else {
				handleCallback(url, json, status);
//			}
		}
		
		afterCallback();
	}
	
	public void handleCallback(String url, JSONObject json, AjaxStatus status) {}
	
	public void beforeCallback() {}
	
	public void afterCallback() {}
}
