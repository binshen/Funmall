package com.ksls.funmall.base;

import java.util.Map;

import android.app.Activity;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;

public class AppQuery extends AQuery {

	public AppQuery(Activity act) {
		super(act);
	}

	public <K> AQuery request(String url, Class<K> type, AjaxCallback<K> callback) {
		return super.ajax(url, type, callback);
	}

	public <K> AQuery request(String url, Map<String, Object> params, Class<K> type, AjaxCallback<K> callback) {
		return super.ajax(url, params, type, callback);
	}
}
