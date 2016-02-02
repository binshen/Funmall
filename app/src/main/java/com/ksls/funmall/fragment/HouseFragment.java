package com.ksls.funmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.callback.AjaxStatus;
import com.ksls.funmall.R;
import com.ksls.funmall.adapter.HouseAdapter;
import com.ksls.funmall.base.AqArrayCallback;
import com.ksls.funmall.base.BaseFragment;
import com.ksls.funmall.base.Constants;
import com.ksls.funmall.util.JSONUtil;
import com.ksls.funmall.view.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HouseFragment extends BaseFragment implements PullToRefreshView.OnFooterRefreshListener {

    private PullToRefreshView mPullToRefreshView;
    private HouseAdapter mHouseAdapter;
    private ListView mHouseList;
    private List<JSONObject> house_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_house, container, false);
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
        house_list = new ArrayList<JSONObject>();
        aq.request(Constants.API_BASE_URL + "/list_house/" + user_id, JSONArray.class, new AqArrayCallback<JSONArray>(aq) {
            @Override
            public void handleCallback(String url, JSONArray json, AjaxStatus status) {
                showData(json);
            }
            @Override
            public void afterCallback() {
                progressDialog.dismiss();
            }
        });

        mPullToRefreshView = (PullToRefreshView) getView().findViewById(R.id.pull_refresh_view_house);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mHouseList = (ListView) getView().findViewById(R.id.list_houses);
    }

    private void showData(JSONArray json) {
        house_list.clear();
        if(json != null && json.length() > 0) {
            for (int i = 0; i < json.length(); i++) {
                house_list.add(JSONUtil.getObject(json, i));
            }
            mHouseAdapter = new HouseAdapter(aq, house_list);
            mHouseList.setAdapter(mHouseAdapter);
        }
    }
}
