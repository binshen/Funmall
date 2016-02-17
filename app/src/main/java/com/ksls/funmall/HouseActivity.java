package com.ksls.funmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Html;

import com.androidquery.callback.AjaxStatus;
import com.ksls.funmall.adapter.ViewPagerAdapter;
import com.ksls.funmall.base.AqObjectCallback;
import com.ksls.funmall.base.BaseActivity;
import com.ksls.funmall.base.Constants;
import com.ksls.funmall.listener.ViewPagerChangeListener;
import com.ksls.funmall.util.JSONUtil;
import com.ksls.funmall.util.TextViewUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HouseActivity extends BaseActivity implements View.OnClickListener {

    private Integer house_id;
    private String xq_name;
    private double latitude, longitude;

    private ViewPager vPager;
    private List<View> viewList;

    private List<JSONObject> vObject;
    private int currentPosition;

    private TextView detail_vpager_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        progressDialog.show();

        initHeader();
        setUpViews();
    }

    private void setUpViews() {
        vObject = new ArrayList<JSONObject>();

        house_id = getIntent().getExtras().getInt("house_id");
        aq.request(Constants.API_BASE_URL + "/get_house/" + house_id, JSONObject.class, new AqObjectCallback<JSONObject>(aq) {
            @Override
            public void handleCallback(String url, JSONObject json, AjaxStatus status) {
                showData(json);

                progressDialog.cancel();
            }
        });
        detail_vpager_counter = (TextView) findViewById(R.id.detail_vpager_counter);

        findViewById(R.id.button_house_loan_calculator).setOnClickListener(this);
        findViewById(R.id.detail_house_info_3_layout).setOnClickListener(this);
    }

    private void showData(JSONObject json) {

        JSONObject oj_data = JSONUtil.getObject(json, "detail");
        xq_name = JSONUtil.getString(oj_data, "xiaoqu_name");
        latitude = JSONUtil.getDouble(oj_data, "latitude");
        longitude = JSONUtil.getDouble(oj_data, "longitude");

        String picUrl = "http://www.funmall.com.cn/uploadfiles/pics/" + JSONUtil.getString(oj_data, "folder") + "/1/";

        //幻灯
        initDetailVPager(json, picUrl);

        TextViewUtil.setText(this, R.id.detail_house_top_title, JSONUtil.getString(oj_data, "xiaoqu_name"));
        TextViewUtil.setText(this, R.id.detail_house_selling_point, JSONUtil.getString(oj_data, "name"));

        Double unit_price = oj_data.optDouble("total_price") / oj_data.optInt("acreage") * 10000;
        TextViewUtil.setText(this, R.id.detail_house_average_price, new BigDecimal(unit_price).setScale(2, BigDecimal.ROUND_HALF_UP) + "元/㎡");

        TextViewUtil.setText(this, R.id.detail_house_info_1, JSONUtil.getString(oj_data, "room") + "室" + JSONUtil.getString(oj_data, "lounge") + "厅" + JSONUtil.getString(oj_data, "toilet") + "卫");
        TextViewUtil.setText(this, R.id.detail_house_info_2, JSONUtil.getString(oj_data, "floor") + "/" + JSONUtil.getString(oj_data, "total_floor"));
        TextViewUtil.setText(this, R.id.detail_house_info_3, JSONUtil.getString(oj_data, "address"));
        TextViewUtil.setText(this, R.id.detail_house_info_4, JSONUtil.getString(oj_data, "acreage") + "平米");
        TextViewUtil.setText(this, R.id.detail_house_info_5, JSONUtil.getString(oj_data, "build_year"));
        TextViewUtil.setText(this, R.id.detail_house_info_6, JSONUtil.getString(oj_data, "orientation_name"));

        TextViewUtil.setSpanedText(this, R.id.detail_house_desc, Html.fromHtml(JSONUtil.getString(oj_data, "description")));
    }

    private void initDetailVPager(JSONObject json, String picUrl) {
        vObject.clear();
        JSONArray oj_data = JSONUtil.getArray(json, "slide");
        viewList = new ArrayList<View>();
        for (int i = 0; i < oj_data.length(); i++) {
            vObject.add(JSONUtil.getObject(oj_data, i));

            ImageView pic = new ImageView(this);
            pic.setScaleType(ImageView.ScaleType.FIT_XY);
            pic.setOnClickListener(new PagerOnClickListener());
            viewList.add(pic);
            aq.id(pic).image(picUrl + JSONUtil.getString(oj_data, i, "pic_short"));
        }

        final int pager_total = viewList.size();
        if(pager_total == 0) {
            detail_vpager_counter.setText("0/0");
        } else {
            detail_vpager_counter.setText("1/" + pager_total);
        }
        vPager = (ViewPager) findViewById(R.id.house_detail_vpager);
        vPager.setAdapter(new ViewPagerAdapter(viewList));
        vPager.setOnPageChangeListener(new ViewPagerChangeListener() {
            public void onPageSelected(int position) {
                currentPosition = position;
                detail_vpager_counter.setText((position+1) + "/" + pager_total);
            }
        });
    }

    class PagerOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), HouseAlbumActivity.class);
            intent.putExtra("h_id", house_id);
            intent.putExtra("style", 1);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_house_loan_calculator:
                startActivity(new Intent(this, CalculatorActivity.class));
                break;
            case R.id.detail_house_info_3_layout:
                Intent baiduIntent = new Intent(this, BaiduMapActivity.class);
                baiduIntent.putExtra("name", xq_name);
                baiduIntent.putExtra("latitude", latitude);
                baiduIntent.putExtra("longitude", longitude);
                startActivity(baiduIntent);
        }
    }
}
