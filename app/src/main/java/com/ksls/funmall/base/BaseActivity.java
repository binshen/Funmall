package com.ksls.funmall.base;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	protected AppQuery aq;
	protected AppManager appManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.aq = new AppQuery(this);
		this.appManager = (AppManager) getApplication();
	}
}
