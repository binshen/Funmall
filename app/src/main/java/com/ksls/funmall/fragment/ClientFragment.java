package com.ksls.funmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.callback.AjaxStatus;
import com.ksls.funmall.R;
import com.ksls.funmall.adapter.ClientAdapter;
import com.ksls.funmall.base.AppRequestCallback;
import com.ksls.funmall.base.BaseFragment;
import com.ksls.funmall.view.PullToRefreshView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClientFragment extends BaseFragment implements PullToRefreshView.OnFooterRefreshListener {

    private PullToRefreshView mPullToRefreshView;
    private ClientAdapter mClientAdapter;
    private ListView mClientList;
    private List<JSONObject> client_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client, container, false);
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

    @Override
    public void onFooterRefresh(PullToRefreshView view) {

    }

    private void setUpViews() {

        client_list = new ArrayList<JSONObject>();
        aq.request("", JSONObject.class, new AppRequestCallback<JSONObject>(aq) {
            @Override
            public void handleCallback(String url, JSONObject json, AjaxStatus status) {
                showData(json);
            }
            @Override
            public void afterCallback() {
                progressDialog.dismiss();
            }
        });

        mPullToRefreshView = (PullToRefreshView) getView().findViewById(R.id.pull_refresh_view_home);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mClientList = (ListView) getView().findViewById(R.id.list_clients);
    }

    private void showData(JSONObject json) {

    }
}
