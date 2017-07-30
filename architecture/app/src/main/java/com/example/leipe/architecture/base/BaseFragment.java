package com.example.leipe.architecture.base;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leipe.architecture.base.support.BaseSupportFragment;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by leipe on 2017/6/29.
 */

public abstract class BaseFragment extends BaseSupportFragment {
    protected final String TAG = this.getClass().getSimpleName();
    protected View mView;
    ImmersionBar immersionBar;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if (OSUtils.isEMUI3_1())  //解决华为emui3.0与3.1手机手动隐藏底部导航栏时，导航栏背景色未被隐藏的问题
        //     _mActivity.getContentResolver().registerContentObserver(Settings.System.getUriFor
        //             ("navigationbar_is_min"), true, mNavigationStatusObserver);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected void setToolbar(Toolbar toolbar, String title) {
        if (title != null) toolbar.setTitle(title);
        immersionBar = ImmersionBar.with(this);
        immersionBar.titleBar(toolbar)
                .navigationBarColor(android.R.color.black)
                .navigationBarWithKitkatEnable(false)
                .init();
    }

    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(_mActivity.getContentResolver(),
                    "navigationbar_is_min", 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
                immersionBar
                        .transparentNavigationBar()
                        .init();
            } else {
                //导航键显示了
                immersionBar
                        .navigationBarColor(android.R.color.black)
                        .fullScreen(false)
                        .init();
            }
        }
    };

    protected abstract int getLayoutId();
}
