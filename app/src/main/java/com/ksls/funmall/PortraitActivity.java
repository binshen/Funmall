package com.ksls.funmall;

import android.app.Activity;
import android.os.Bundle;

import com.ksls.funmall.base.BaseActivity;

public class PortraitActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait);

        initHeader();
        setUpViews();
    }

    private void setUpViews() {

        String ticket = appManager.getLoginUser().optString("ticket");
        aq.id(R.id.login_user_barcode).image("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket, true, true, 0, R.drawable.icon_portrait);
    }
}
