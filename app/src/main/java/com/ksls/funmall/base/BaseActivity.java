package com.ksls.funmall.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.ksls.funmall.R;
import com.ksls.funmall.view.Header;

import cn.jpush.android.api.JPushInterface;

public class BaseActivity extends Activity {

	protected AppQuery aq;
	protected Header header;
	protected AppManager appManager;
	protected ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTheme(android.R.style.Theme_Light);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setTitle("请稍等");
		progressDialog.setMessage("正在读取数据...");

		this.aq = new AppQuery(this);
		this.appManager = (AppManager) getApplication();
	}

	protected void initHeader() {
		header = (Header) findViewById(R.id.system_header_layout);
		header.setLeftHeaderOnClickListener(new Header.LeftHeaderOnClickListener() {
			@Override
			public void onLeftHeaderClick(View v) {
				setupLeftHeaderCallback();
			}
		});
		header.setRightHeaderOnClickListener(new Header.RightHeaderOnClickListener() {
			@Override
			public void onRightHeaderClick(View v) {
				setupRightHeaderCallback();
			}
		});
	};

	protected void setupLeftHeaderCallback() {
		finish();
	}

	protected void setupRightHeaderCallback() {

	}

	@Override
	protected void onPause() {
		super.onPause();

		aq.clear();
		progressDialog.dismiss();

//		JPushInterface.onPause(getApplicationContext());
	}

//	@Override
//	protected  void onResume() {
//		super.onResume();
//		JPushInterface.onResume(getApplicationContext());
//	}
}
