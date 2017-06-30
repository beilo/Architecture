package com.example.leipe.rx.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.leipe.rx.R;
import com.example.leipe.rx.base.BaseActivity;

/**
 * Created by leipe on 2017/6/27.
 */

public class MainSupportActivity extends BaseActivity {
    final int layout = R.layout.main_activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fragment_container, WXListSupportFragment.newInstance());
        }
    }

    @Override
    protected int getLayout() {
        return layout;
    }

    public void show(){

    }
}
