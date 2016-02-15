package com.ksls.funmall;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ksls.funmall.base.BaseActivity;

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


    }
}
