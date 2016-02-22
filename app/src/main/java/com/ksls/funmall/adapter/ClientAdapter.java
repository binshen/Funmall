package com.ksls.funmall.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksls.funmall.ChatActivity;
import com.ksls.funmall.R;
import com.ksls.funmall.base.AppBaseAdapter;
import com.ksls.funmall.base.AppQuery;
import com.ksls.funmall.util.TextViewUtil;

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

        convertView.findViewById(R.id.client_head_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("open_id", data.optString("open_id"));
                intent.putExtra("headimgurl", data.optString("headimgurl"));
                intent.setClass(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });

        aq.id(convertView.findViewById(R.id.client_head_pic)).image(data.optString("headimgurl"), true, true, 0, R.drawable.header_logo);
        TextViewUtil.setText(convertView, R.id.client_nickname, data.optString("nickname"));
        TextViewUtil.setText(convertView, R.id.client_sex, data.optString("sex").equals("1") ? "男" : "女");

        String username = data.optString("realname");
        if(username != null && !("").equals(username) && !("null").equals(username)) {
            TextViewUtil.setText(convertView, R.id.client_realname, "姓名：" + username);
        } else {
            TextViewUtil.setText(convertView, R.id.client_realname, "姓名：暂无");
        }

        String user_tel = data.optString("user_tel");
        if(user_tel != null && !("").equals(user_tel) && !("null").equals(user_tel)) {
            TextViewUtil.setText(convertView, R.id.client_user_tel, "电话：" + user_tel);
        } else {
            TextViewUtil.setText(convertView, R.id.client_user_tel, "电话：暂无");
        }

        return convertView;
    }
}
