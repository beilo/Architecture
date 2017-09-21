package com.minister.architecture.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.minister.architecture.R;
import com.minister.architecture.di.injector.Injectable;
import com.minister.architecture.util.ResourcesUtil;

import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by 被咯苏州 on 2017/8/27.
 */

public class BaseSupportFragment extends SupportFragment
        implements LifecycleOwner,
        Injectable {
    protected final String TAG = this.getClass().getSimpleName();

    protected CompositeDisposable mDisposable = new CompositeDisposable();
    protected Unbinder unbinder;
    protected ImmersionBar mImmersionBar;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            View titleBar = view.findViewById(setTitleBar());
            if (titleBar != null)
                ImmersionBar.setTitleBar(_mActivity, titleBar);
            View statusBarView = view.findViewById(setStatusBarView());
            if (statusBarView != null)
                ImmersionBar.setStatusBarView(_mActivity, statusBarView);
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        //如果要在Fragment单独使用沉浸式，请在onSupportVisible实现沉浸式
        if (isImmersionBarEnabled()) {
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.navigationBarWithKitkatEnable(false).init();
        }
    }

    protected void setToolbar(Toolbar toolbar,String title){
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(ResourcesUtil.getColor(_mActivity,R.color.white));
    }

    private boolean isImmersionBarEnabled() {
        return false;
    }

    protected int setTitleBar() {
        return R.id.toolbar;
    }

    protected int setStatusBarView() {
        return 0;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mDisposable != null) {
            mDisposable.clear();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }


    // lifecycle
    LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    public LifecycleRegistry getLifecycle() {
        return this.mLifecycleRegistry;
    }
}
