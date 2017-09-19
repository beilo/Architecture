package com.minister.architecture.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.support.v4.app.Fragment;

import com.minister.architecture.di.injector.Injectable;

import javax.inject.Inject;

import butterknife.Unbinder;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by 被咯苏州 on 2017/8/27.
 */

public class BaseSupportActivity extends SupportActivity
        implements HasSupportFragmentInjector,
        LifecycleOwner,
        Injectable {
    protected final String TAG = this.getClass().getSimpleName();

    protected CompositeDisposable mDisposable = new CompositeDisposable();
    protected Unbinder unbinder;


    @Override
    protected void onStop() {
        super.onStop();
        if (mDisposable != null) {
            mDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    // dagger-android
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    // lifecycle
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
