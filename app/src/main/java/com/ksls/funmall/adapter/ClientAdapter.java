package com.ksls.funmall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksls.funmall.R;
import com.ksls.funmall.base.AppBaseAdapter;
import com.ksls.funmall.base.AppQuery;

import org.json.JSONObject;

import java.util.List;

public class ClientAdapter extends AppBaseAdapter {

    public ClientAdapter(AppQuery aq, List<JSONObject> list) {
        super(aq, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final JSONObject data = (JSONObject) list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_client, null);
        }
        aq.id(convertView.findViewById(R.id.client_head_pic)).image(data.optString("headimgurl"), true, true, 0, R.drawable.header_logo);
        return convertView;
    }
}