package com.ksls.funmall.base;

import android.app.Application;

import org.json.JSONObject;

public class AppManager extends Application {

	private JSONObject loginUser;

	public JSONObject getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(JSONObject loginUser) {
		this.loginUser = loginUser;
	}

	@Override
	public void onCreate() {
		
	}
}
