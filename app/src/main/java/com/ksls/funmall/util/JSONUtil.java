package com.ksls.funmall.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.androidquery.callback.AjaxCallback;
import com.ksls.funmall.base.AppQuery;
import com.ksls.funmall.base.Constants;

public class JSONUtil {
	
	public static String getString(JSONObject json, String key) {
		
		try {
			String value = json.getString(key);
			return value == null || ("null").equals(value) ? "" : value;
		} catch (JSONException e) {
			if(Constants.debug) e.printStackTrace();
		}
		return null;
	}
	
	public static String getString(JSONArray json, int index, String key) {

		try {
			return getString(json.getJSONObject(index), key);
		} catch (JSONException e) {
			if(Constants.debug) e.printStackTrace();
		}
		return null;
	}
	
	public static Integer getInt(JSONObject json, String key) {
		
		try {
			return json.getInt(key);
		} catch (JSONException e) {
			if(Constants.debug) e.printStackTrace();
		}
		return -1;
	}
	
	public static Integer getInt(JSONArray json, int index, String key) {

		try {
			return getInt(json.getJSONObject(index), key);
		} catch (JSONException e) {
			if(Constants.debug) e.printStackTrace();
		}
		return -1;
	}
	
	public static Double getDouble(JSONObject json, String key) {
		
		try {
			return json.getDouble(key);
		} catch (JSONException e) {
			if(Constants.debug) e.printStackTrace();
		}
		return 0d;
	}
	
	public static JSONObject getObject(JSONObject json, String key) {
		
		try {
			return json.getJSONObject(key);
		} catch (JSONException e) {
			if(Constants.debug) e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject getObject(JSONArray json, int index) {
		
		try {
			return json.getJSONObject(index);
		} catch (JSONException e) {
			if(Constants.debug) e.printStackTrace();
		}
		return null;
	}
	
	public static JSONArray getArray(JSONObject json, String key) {
		
		try {
			return json.getJSONArray(key);
		} catch (JSONException e) {
			if(Constants.debug) e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject getData(Activity activity, String url) {
		
		AppQuery aq = new AppQuery(activity);
		AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
		cb.url(url).type(JSONObject.class);
		aq.sync(cb);
		
		return cb.getResult();
	}
	
	public static void setString(JSONObject object, String key, String value) {
		
		try {
			object.put(key, value);
		} catch (JSONException e) {
			if(Constants.debug) e.printStackTrace();
		}
	}
	
	public static JSONObject getObject(String value) {
		try {
			return new JSONObject(value);
		} catch (JSONException e) {
			if(Constants.debug) e.printStackTrace();
		}
		return null;
	}
	
	public static Long getLong(JSONObject json, String key) {
		
		try {
			return json.getLong(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return -1l;
	}
}
