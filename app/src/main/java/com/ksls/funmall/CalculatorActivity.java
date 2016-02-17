package com.ksls.funmall;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import com.ksls.funmall.base.BaseActivity;

@SuppressLint("SetJavaScriptEnabled") 
public class CalculatorActivity extends BaseActivity {
	
	private WebView contentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		
		initHeader();
		setUpViews();
	}
	
	private void setUpViews() {	
		header.getTitle().setText("房贷计算器");
		
		contentView = (WebView) findViewById(R.id.calculator_content);
		contentView.getSettings().setDefaultTextEncodingName("utf-8");
		contentView.setVerticalScrollBarEnabled(true);
		contentView.setBackgroundColor(0);
		contentView.getSettings().setJavaScriptEnabled(true); 
//		contentView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                return super.onJsAlert(view, url, message, result);
//            }
//      });
		contentView.loadUrl("http://f.ks.js.cn/tool/");
	}
}

//import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTabHost;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//
//import com.kunshan.hbo.base.BaseFragmentActivity;
//import com.kunshan.hbo.fragment.AccumulationLoanFragment;
//import com.kunshan.hbo.fragment.CombinationLoanFragment;
//import com.kunshan.hbo.fragment.CommercialLoanFragment;
//
//public class CalculatorActivity extends BaseFragmentActivity implements OnCheckedChangeListener {
//
//	private FragmentTabHost tabHost;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_calculator);
//		
//		initHeader();
//		setUpViews();
//	}
//	
//	private void setUpViews() {	
//		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//		tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
//		tabHost.getTabWidget().setVisibility(View.GONE);
//		
//		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1"), AccumulationLoanFragment.class, null);
//    	tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2"), CommercialLoanFragment.class, null);
//		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("tab3"), CombinationLoanFragment.class, null);
//		
//		tabHost.setCurrentTabByTag("tab1");
//		RadioButton button = (RadioButton) findViewById(R.id.calculator_tab1);
//		button.setChecked(true);
//		
//		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.calculator_group);
//		radioGroup.setOnCheckedChangeListener(this);
//	}
//	
//	@Override
//	public void onCheckedChanged(RadioGroup group, int checkedId) {
//		FragmentManager fm = getSupportFragmentManager();
//		AccumulationLoanFragment tab1 = (AccumulationLoanFragment) fm.findFragmentByTag("tab1");
//		CommercialLoanFragment tab2 = (CommercialLoanFragment) fm.findFragmentByTag("tab2");
//		CombinationLoanFragment tab3 = (CombinationLoanFragment) fm.findFragmentByTag("tab3");
//		
//		FragmentTransaction ft = fm.beginTransaction();
//		if (tab1 != null) ft.detach(tab1);
//		if (tab2 != null) ft.detach(tab2);
//		if (tab3 != null) ft.detach(tab3);
//		
//		switch (checkedId) {
//		case R.id.calculator_tab1:
//			if (tab1 == null) {
//				ft.add(android.R.id.tabcontent, new AccumulationLoanFragment(), "tab1");
//			} else {
//				ft.attach(tab1);
//			}
//			tabHost.setCurrentTabByTag("tab1");
//			break;
//		case R.id.calculator_tab2:
//			if (tab2 == null) {
//				ft.add(android.R.id.tabcontent, new CommercialLoanFragment(), "tab2");
//			} else {
//				ft.attach(tab2);
//			}
//			tabHost.setCurrentTabByTag("tab2");
//			break;
//		case R.id.calculator_tab3:
//			if (tab3 == null) {
//				ft.add(android.R.id.tabcontent, new CombinationLoanFragment(), "tab3");
//			} else {
//				ft.attach(tab3);
//			}
//			tabHost.setCurrentTabByTag("tab3");
//			break;
//		}
//		ft.commit();
//	}
//}
