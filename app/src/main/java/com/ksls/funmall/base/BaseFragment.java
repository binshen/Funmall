package com.ksls.funmall.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ksls.funmall.R;
import com.ksls.funmall.view.Header;

public class BaseFragment extends Fragment {

    protected AppQuery aq;
    protected Header header;
    protected AppManager appManager;
    protected ProgressDialog progressDialog;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("正在读取数据...");

        this.aq = new AppQuery(getActivity());
        this.appManager = (AppManager) getActivity().getApplication();
    }

    protected void initHeader() {
        header = (Header) getView().findViewById(R.id.system_header_layout);
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
        getActivity().finish();
    }

    protected void setupRightHeaderCallback() {

    }
}
