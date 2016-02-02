package com.ksls.funmall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksls.funmall.R;
import com.ksls.funmall.base.AppBaseAdapter;
import com.ksls.funmall.base.AppQuery;

import org.json.JSONObject;

import java.util.List;

public class HouseAdapter extends AppBaseAdapter {

    public HouseAdapter(AppQuery aq, List<JSONObject> list) {
        super(aq, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final JSONObject data = (JSONObject) list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_house, null);
        }
        aq.id(convertView.findViewById(R.id.house_pic)).image("http://www.funmall.com.cn/" + data.optString("bg_pic"), true, true, 0, R.drawable.header_logo);
        return convertView;
    }
}
