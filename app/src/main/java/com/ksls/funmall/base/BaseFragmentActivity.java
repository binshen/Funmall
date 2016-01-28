package com.ksls.funmall.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseFragmentActivity extends FragmentActivity {

    protected AppQuery aq;
    protected AppManager appManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.aq = new AppQuery(this);
        this.appManager = (AppManager) getApplication();
    }
}
