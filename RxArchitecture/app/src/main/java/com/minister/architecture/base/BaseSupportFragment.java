package com.minister.architecture.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;

import com.minister.architecture.di.injector.Injectable;

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


    @Override
    public void onStop() {
        super.onStop();
        if (mDisposable != null){
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


    // lifecycle
    LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    public LifecycleRegistry getLifecycle() {
        return this.mLifecycleRegistry;
    }
}
