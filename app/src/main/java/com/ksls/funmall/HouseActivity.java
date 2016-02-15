package com.ksls.funmall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.callback.AjaxStatus;
import com.ksls.funmall.adapter.ViewPagerAdapter;
import com.ksls.funmall.base.AqArrayCallback;
import com.ksls.funmall.base.AqObjectCallback;
import com.ksls.funmall.base.BaseActivity;
import com.ksls.funmall.base.Constants;
import com.ksls.funmall.listener.ViewPagerChangeListener;
import com.ksls.funmall.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HouseActivity extends BaseActivity {

    private Integer house_id;

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
    }

    private void showData(JSONObject json) {

        JSONObject oj_data = JSONUtil.getObject(json, "detail");

        String picUrl = "http://www.funmall.com.cn/uploadfiles/pics/" + JSONUtil.getString(oj_data, "folder") + "/1/";

        //幻灯
        initDetailVPager(json, picUrl);
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
            //intent.putExtra("gid", gid);
            //intent.putExtra("pid", JSONUtil.getInt(vObject.get(currentPosition), "pid"));
            //intent.putExtra("style", JSONUtil.getInt(vObject.get(currentPosition), "style"));
            startActivity(intent);
        }
    }
}
