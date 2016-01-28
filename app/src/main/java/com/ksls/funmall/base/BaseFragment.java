package com.ksls.funmall.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    protected AppQuery aq;
    protected AppManager appManager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.aq = new AppQuery(getActivity());
        this.appManager = (AppManager) getActivity().getApplication();
    }
}
