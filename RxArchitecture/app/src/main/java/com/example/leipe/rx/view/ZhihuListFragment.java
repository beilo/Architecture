package com.example.leipe.rx.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.leipe.rx.R;
import com.example.leipe.rx.base.BaseFragment;
import com.example.leipe.rx.view.adapter.WXAdapter;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by leipe on 2017/6/30.
 */

public class ZhihuListFragment extends BaseFragment {
    private final int layout =R.layout.list_fragment;
    TextView tv_loading;
    RecyclerView rl_list;
    Toolbar toolbar;

    private boolean isLoading = true;
    WXAdapter mAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_loading = view.findViewById(R.id.loading_tv);
        rl_list = view.findViewById(R.id.products_list);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("知乎热门");
        ImmersionBar.with(this).titleBar(toolbar).init();
        isLoading();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mAdapter = new WXAdapter(_mActivity, null);
        rl_list.setLayoutManager(new LinearLayoutManager(_mActivity));
        rl_list.setAdapter(mAdapter);
    }


    private void isLoading() {
        if (isLoading) {
            tv_loading.setVisibility(View.VISIBLE);
            rl_list.setVisibility(View.GONE);
        } else {
            rl_list.setVisibility(View.VISIBLE);
            tv_loading.setVisibility(View.GONE);
        }
    }
    @Override
    protected int getLayoutId() {
        return layout;
    }
}
