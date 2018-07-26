package com.minister.architecture.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;

import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by 被咯苏州 on 2017/8/27.
 */

public class BaseSupportActivity extends SupportActivity
        implements  LifecycleOwner{
    protected final String TAG = this.getClass().getSimpleName();
    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";

    protected Context mContext;
    protected CompositeDisposable mDisposable = new CompositeDisposable();
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
        //解决华为emui3.0与3.1手机手动隐藏底部导航栏时，导航栏背景色未被隐藏的问题
        if (OSUtils.isEMUI3_1()) {
            //第一种
            getContentResolver().registerContentObserver(Settings.System.getUriFor
                    (NAVIGATIONBAR_IS_MIN), true, mNavigationStatusObserver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.clear();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }

    }


    // lifecycle
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }


    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                    NAVIGATIONBAR_IS_MIN, 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
                mImmersionBar.transparentNavigationBar().init();
            } else {
                //导航键显示了
                mImmersionBar.navigationBarColor(android.R.color.black) //隐藏前导航栏的颜色
                        .fullScreen(false)
                        .init();
            }
        }
    };
}
