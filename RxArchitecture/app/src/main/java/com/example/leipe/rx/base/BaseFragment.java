package com.example.leipe.rx.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leipe.rx.base.support.BaseSupportFragment;

/**
 * Created by leipe on 2017/6/29.
 */

public abstract class BaseFragment extends BaseSupportFragment {
    protected View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    protected abstract int getLayoutId();
}
