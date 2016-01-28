package com.ksls.funmall;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.callback.AjaxStatus;
import com.ksls.funmall.R;
import com.ksls.funmall.base.AppRequestCallback;
import com.ksls.funmall.base.BaseActivity;
import com.ksls.funmall.base.Constants;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText mUsernameEditText;
	private EditText mPasswordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		setUpViews();
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
	}

	private void processLogin() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", mUsernameEditText.getText().toString());
		params.put("password", mPasswordEditText.getText().toString());
		aq.request(Constants.API_BASE_URL + "/login", params, JSONObject.class, new AppRequestCallback<JSONObject>(aq) {
			@Override
			public void handleCallback(String url, JSONObject json, AjaxStatus status) {
				Boolean result = json.optBoolean("result");
				if(result) {
					mUsernameEditText.setText("111");
					mPasswordEditText.setText("222");
				} else {
					mUsernameEditText.setText("");
					mPasswordEditText.setText("");
					Toast.makeText(aq.getContext(), "用户名密码错误，请重试！", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}