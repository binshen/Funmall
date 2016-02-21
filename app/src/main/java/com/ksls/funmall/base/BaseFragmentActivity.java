package com.ksls.funmall.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.ksls.funmall.R;
import com.ksls.funmall.view.Header;

import cn.jpush.android.api.JPushInterface;

public class BaseFragmentActivity extends FragmentActivity {

    protected AppQuery aq;
    protected Header header;
    protected AppManager appManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(android.R.style.Theme_Light);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
    }

    protected void setupLeftHeaderCallback() {
        finish();
    }

    protected void setupRightHeaderCallback() {

    }

    @Override
    public void onPause() {
        super.onPause();
        JPushInterface.onPause(getApplicationContext());
    }

    @Override
    public  void onResume() {
        super.onResume();
        JPushInterface.onResume(getApplicationContext());
    }
}
