package com.ksls.funmall;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ksls.funmall.base.BaseFragmentActivity;
import com.ksls.funmall.fragment.ClientFragment;
import com.ksls.funmall.fragment.HomeFragment;
import com.ksls.funmall.fragment.HouseFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private FragmentTabHost tabHost;

    private boolean isExit = false;
    private Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        setUpViews();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fm = getSupportFragmentManager();
        HomeFragment tab1 = (HomeFragment) fm.findFragmentByTag("tab1");
        HouseFragment tab2 = (HouseFragment) fm.findFragmentByTag("tab2");
        ClientFragment tab3 = (ClientFragment) fm.findFragmentByTag("tab3");

        FragmentTransaction ft = fm.beginTransaction();
        if (tab1 != null) ft.detach(tab1);
        if (tab2 != null) ft.detach(tab2);
        if (tab3 != null) ft.detach(tab3);

        switch (checkedId) {
            case R.id.main_tab1:
                if (tab1 == null) {
                    ft.add(android.R.id.tabcontent, new HomeFragment(), "tab1");
                } else {
                    ft.attach(tab1);
                }
                tabHost.setCurrentTabByTag("tab1");
                break;
            case R.id.main_tab2:
                if (tab2 == null) {
                    ft.add(android.R.id.tabcontent, new HouseFragment(), "tab2");
                } else {
                    ft.attach(tab2);
                }
                tabHost.setCurrentTabByTag("tab2");
                break;
            case R.id.main_tab3:
                if (tab3 == null) {
                    ft.add(android.R.id.tabcontent, new ClientFragment(), "tab3");
                } else {
                    ft.attach(tab3);
                }
                tabHost.setCurrentTabByTag("tab3");
                break;
        }
        ft.commit();
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

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        if(tabHost.getCurrentTab() == 0) {
            if (isExit) {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                //System.exit(0);
            } else {
                isExit = true;
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                TimerTask timeTask = new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                };
                timer.schedule(timeTask, 2000);
            }
        } else {
            tabHost.setCurrentTabByTag("tab1");
            RadioButton button = (RadioButton) findViewById(R.id.main_tab1);
            button.setChecked(true);
        }
    }
}
