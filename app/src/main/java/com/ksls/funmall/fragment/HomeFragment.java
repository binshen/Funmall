package com.ksls.funmall.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksls.funmall.R;
import com.ksls.funmall.base.BaseFragment;
import com.ksls.funmall.util.JSONUtil;
import com.ksls.funmall.util.TextViewUtil;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initHeader();
    }

    @Override
    public void onStart() {
        super.onStart();

        setUpViews();
    }

    private void setUpViews() {

        String username = appManager.getLoginUser().optString("rel_name");
        String user_tel = appManager.getLoginUser().optString("tel");
        String user_pic = appManager.getLoginUser().optString("pic");


        TextViewUtil.setText(getActivity(), R.id.text_agency_homepage_uname, username);
        TextViewUtil.setText(getActivity(), R.id.text_agency_homepage_tel, user_tel);

        if(user_pic != null) {
            aq.id(R.id.login_user_headpic).image("http://www.funmall.com.cn/" + user_pic);
        }

        getView().findViewById(R.id.button_agency_homepage_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_agency_homepage_logout:
                getActivity().finish();
                System.exit(0);
                break;
        }
    }
}
