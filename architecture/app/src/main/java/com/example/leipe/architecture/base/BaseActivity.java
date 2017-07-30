package com.example.leipe.architecture.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.leipe.architecture.base.support.BaseSupportActivity;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by leipe on 2017/6/29.
 */

public abstract class BaseActivity extends BaseSupportActivity {
    protected Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = this;
        ImmersionBar immersionBar = ImmersionBar.with(this);
        immersionBar.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }


    protected abstract int getLayout();

}
