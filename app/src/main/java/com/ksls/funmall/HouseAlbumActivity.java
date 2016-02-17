package com.ksls.funmall;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

public class HouseAlbumActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private ViewPager vPager;
    private List<View> viewList = new ArrayList<View>();

    private PagerAdapter vPagerAdapter;

    private int house_id, style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_album);

        progressDialog.show();

        initHeader();
        setUpViews();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int style_id;
        switch (checkedId) {
            case R.id.house_album_tab1:
                style_id = 2;
                break;
            case R.id.house_album_tab2:
                style_id = 1;
                break;
            case R.id.house_album_tab3:
                style_id = 3;
                break;
            case R.id.house_album_tab4:
                style_id = 4;
                break;
            case R.id.house_album_tab5:
                style_id = 5;
                break;
            case R.id.house_album_tab6:
                style_id = 6;
                break;
            default:
                style_id = -1;
                break;
        }

        aq.request(Constants.API_BASE_URL + "/get_house/" + house_id, JSONObject.class, new AqObjectCallback<JSONObject>(aq) {
            @Override
            public void handleCallback(String url, JSONObject json, AjaxStatus status) {
                showData(json);

                progressDialog.cancel();
            }
        });
    }

    private void setUpViews() {

        house_id = getIntent().getExtras().getInt("h_id");
        style = getIntent().getExtras().getInt("style");
        switch (style) {
            case 1:
                ((RadioButton)findViewById(R.id.house_album_tab2)).setChecked(true);
                onCheckedChanged(null, R.id.house_album_tab2);
                break;
            case 2:
                ((RadioButton)findViewById(R.id.house_album_tab1)).setChecked(true);
                onCheckedChanged(null, R.id.house_album_tab1);
                break;
            case 3:
                ((RadioButton)findViewById(R.id.house_album_tab3)).setChecked(true);
                onCheckedChanged(null, R.id.house_album_tab3);
                break;
            case 4:
                ((RadioButton)findViewById(R.id.house_album_tab4)).setChecked(true);
                onCheckedChanged(null, R.id.house_album_tab4);
                break;
            case 5:
                ((RadioButton)findViewById(R.id.house_album_tab5)).setChecked(true);
                onCheckedChanged(null, R.id.house_album_tab5);
                break;
            case 6:
                ((RadioButton)findViewById(R.id.house_album_tab6)).setChecked(true);
                onCheckedChanged(null, R.id.house_album_tab6);
                break;
            default:
                progressDialog.dismiss();
                break;
        }

        RadioGroup house_album_radio_group = (RadioGroup)findViewById(R.id.house_album_radio_group);
        house_album_radio_group.setOnCheckedChangeListener(this);
    }

    private void showData(JSONObject json) {

        JSONObject oj_data = JSONUtil.getObject(json, "detail");
        JSONArray oj_array = JSONUtil.getArray(json, "slide");

        String picUrl = "http://www.funmall.com.cn/uploadfiles/pics/" + JSONUtil.getString(oj_data, "folder") + "/1/";

        viewList.clear();
        for (int i = 0; i < oj_array.length(); i++) {
            ImageView pic = new ImageView(this);
            pic.setScaleType(ImageView.ScaleType.FIT_CENTER);
            viewList.add(pic);
            aq.id(pic).image(picUrl + JSONUtil.getString(oj_array, i, "pic_short"));
        }
        Drawable drawable = getResources().getDrawable(R.drawable.icon_detail_vpage);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        header.getTitle().setCompoundDrawables(drawable, null, null, null);
        header.getTitle().setCompoundDrawablePadding(5);

        final int pager_total = viewList.size();
        if(pager_total == 0) {
            header.getTitle().setText("0/0");
            Toast.makeText(this, "图片不存在", Toast.LENGTH_SHORT).show();
        } else {
            header.getTitle().setText("1/" + pager_total);
        }
        vPagerAdapter = new ViewPagerAdapter(viewList) {
            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
            }
            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView((View)object);
            }
            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        vPager = (ViewPager) findViewById(R.id.house_album_vpager);
        vPager.setAdapter(vPagerAdapter);
        vPager.setOnPageChangeListener(new ViewPagerChangeListener() {
            @Override
            public void onPageSelected(int position) {
                header.getTitle().setText((position + 1) + "/" + pager_total);
            }
        });
    }
}
