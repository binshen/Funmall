package com.ksls.funmall;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.callback.AjaxStatus;
import com.ksls.funmall.base.AqObjectCallback;
import com.ksls.funmall.base.BaseActivity;
import com.ksls.funmall.base.Constants;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText mUsernameEditText;
	private EditText mPasswordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		setUpViews();

		mUsernameEditText.setText("13913913999");
		mPasswordEditText.setText("888888");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btnLogin:
			processLogin();
			break;
		default:
			break;
		}
	}

	private void setUpViews() {
		mUsernameEditText = (EditText) findViewById(R.id.login_edtId);
		mPasswordEditText = (EditText) findViewById(R.id.login_edtPwd);
		findViewById(R.id.login_btnLogin).setOnClickListener(this);

		JPushInterface.init(getApplicationContext());
	}

	private void processLogin() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", mUsernameEditText.getText().toString());
		params.put("password", mPasswordEditText.getText().toString());
		aq.request(Constants.API_BASE_URL + "/login", params, JSONObject.class, new AqObjectCallback<JSONObject>(aq) {
			@Override
			public void handleCallback(String url, JSONObject json, AjaxStatus status) {
				if (json != null) {
					appManager.setLoginUser(json);
					Intent intent = new Intent(aq.getContext(), MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					mUsernameEditText.setText("");
					mPasswordEditText.setText("");
					Toast.makeText(aq.getContext(), "用户名或密码错误，请重试！", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(getApplicationContext());
	}

	@Override
	protected  void onResume() {
		super.onResume();
		JPushInterface.onResume(getApplicationContext());
	}
}
