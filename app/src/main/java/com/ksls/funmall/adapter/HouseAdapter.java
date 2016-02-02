package com.ksls.funmall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksls.funmall.R;
import com.ksls.funmall.base.AppBaseAdapter;
import com.ksls.funmall.base.AppQuery;
import com.ksls.funmall.util.TextViewUtil;

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
        aq.id(convertView.findViewById(R.id.house_pic)).image("http://www.funmall.com.cn/uploadfiles/pics/" + data.optString("bg_pic"), true, true, 0, R.drawable.header_logo);
        TextViewUtil.setText(convertView, R.id.house_xiaoqu, data.optString("xiaoqu_name"));
        TextViewUtil.setText(convertView, R.id.house_region, data.optString("region_name"));

        String detail = data.optString("room") + "室" + data.optString("lounge") + "厅 | " + data.optString("floor") + "/" + data.optString("total_floor") + "层 | " + data.optString("acreage") + "㎡";
        TextViewUtil.setText(convertView, R.id.house_detail, detail);
        TextViewUtil.setText(convertView, R.id.house_price, data.optString("total_price") + "万");

        String desc = "装修：" + data.optString("decoration_name") + " | 朝向：" + data.optString("orientation_name");
        TextViewUtil.setText(convertView, R.id.house_description, desc);

        TextViewUtil.setText(convertView, R.id.house_extra_info, data.optString("unit_price") + "元/㎡");
        return convertView;
    }
}
