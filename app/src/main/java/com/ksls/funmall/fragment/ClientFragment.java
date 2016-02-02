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
import com.ksls.funmall.base.AqArrayCallback;
import com.ksls.funmall.base.AqObjectCallback;
import com.ksls.funmall.base.BaseFragment;
import com.ksls.funmall.base.Constants;
import com.ksls.funmall.util.JSONUtil;
import com.ksls.funmall.view.PullToRefreshView;

import org.json.JSONArray;
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
        mPullToRefreshView.onFooterRefreshComplete();
    }

    private void setUpViews() {

        Integer user_id = appManager.getLoginUser().optInt("id");
        client_list = new ArrayList<JSONObject>();
        aq.request(Constants.API_BASE_URL + "/list_client/" + user_id, JSONArray.class, new AqArrayCallback<JSONArray>(aq) {
            @Override
            public void handleCallback(String url, JSONArray json, AjaxStatus status) {
                showData(json);
            }
            @Override
            public void afterCallback() {
                progressDialog.dismiss();
            }
        });

        mPullToRefreshView = (PullToRefreshView) getView().findViewById(R.id.pull_refresh_view_client);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mClientList = (ListView) getView().findViewById(R.id.list_clients);
    }

    private void showData(JSONArray json) {
        client_list.clear();
        if(json != null && json.length() > 0) {
            for (int i = 0; i < json.length(); i++) {
                client_list.add(JSONUtil.getObject(json, i));
            }
            mClientAdapter = new ClientAdapter(aq, client_list);
            mClientList.setAdapter(mClientAdapter);
        }
    }
}
