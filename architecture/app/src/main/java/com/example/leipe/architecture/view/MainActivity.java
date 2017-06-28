package com.example.leipe.architecture.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.leipe.architecture.R;
import com.example.leipe.architecture.base.BaseActivity;

/**
 * Created by leipe on 2017/6/27.
 */

public class MainActivity extends BaseActivity {
    final int layout = R.layout.main_activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fragment_container, WXListFragment.newInstance());
        }
    }

    public void show(){

    }
}
