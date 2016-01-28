package com.ksls.funmall;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ksls.funmall.base.BaseFragmentActivity;
import com.ksls.funmall.fragment.ClientFragment;
import com.ksls.funmall.fragment.HomeFragment;
import com.ksls.funmall.fragment.HouseFragment;

public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    private void setUpViews() {

        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.getTabWidget().setVisibility(View.GONE);

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1"), HomeFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2"), HouseFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("tab3"), ClientFragment.class, null);

        tabHost.setCurrentTabByTag("tab1");
        RadioButton button = (RadioButton) findViewById(R.id.main_tab1);
        button.setChecked(true);
    }
}
