package com.example.leipe.rx.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.leipe.rx.app.App;
import com.example.leipe.rx.base.support.BaseSupportActivity;
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
        ImmersionBar.with(this).init();
        App.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
        ImmersionBar.with(this).destroy();
    }


    protected abstract int getLayout();

}
