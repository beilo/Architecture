package com.example.leipe.architecture.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.leipe.architecture.R;
import com.example.leipe.architecture.app.Router;
import com.example.leipe.architecture.base.BaseActivity;

/**
 * Created by leipe on 2017/6/27.
 */

public class MainActivity extends BaseActivity {
    final int layout = R.layout.main_activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (findChildFragment(MainFragment.class) == null) {
            MainFragment mainFragment = (MainFragment) ARouter.getInstance()
                    .build(Router.MAIN)
                    .navigation();
            loadRootFragment(R.id.fl_content, mainFragment);
        }
    }

    @Override
    protected int getLayout() {
        return layout;
    }
}
