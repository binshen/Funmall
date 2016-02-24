package com.ksls.funmall;

import android.os.Bundle;

import com.ksls.funmall.base.BaseActivity;
import com.ksls.funmall.util.TextViewUtil;

import org.json.JSONObject;

public class PortraitActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait);

        initHeader();
        setUpViews();
    }

    private void setUpViews() {

        JSONObject loginUser = appManager.getLoginUser();
        String ticket = loginUser.optString("ticket");
        aq.id(R.id.login_user_barcode).image("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket, true, true, 0, R.drawable.icon_portrait);

        TextViewUtil.setText(this, R.id.text_agency_company_name, loginUser.optString("company_name"));
        TextViewUtil.setText(this, R.id.text_agency_company_address, loginUser.optString("address"));
        TextViewUtil.setText(this, R.id.text_agency_company_contact, loginUser.optString("company_tel"));
    }
}
