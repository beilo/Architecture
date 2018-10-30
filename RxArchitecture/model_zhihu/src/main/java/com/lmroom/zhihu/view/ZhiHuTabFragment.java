package com.lmroom.zhihu.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.zhihu.R;

/**
 * 知乎Tab 主页
 * Created by leipe on 2017/9/18.
 */
@Route(path = "/zhihu/tab")
public class ZhiHuTabFragment extends BaseSupportFragment {

    TabLayout tab;
    ViewPager vpContainer;
    Toolbar toolbar;
    View mView;

    public static ZhiHuTabFragment newInstance() {
        Bundle bundle = new Bundle();
        ZhiHuTabFragment fragment = new ZhiHuTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setStatusBarView() {
        return R.id.view;
    }

    @Override
    protected int setTitleBar() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.zhihu_fragment_tab, container, false);

        tab = mView.findViewById(R.id.tab);
        vpContainer = mView.findViewById(R.id.vp_container);
        toolbar = mView.findViewById(R.id.toolbar);

        setToolbar(toolbar,"知乎日报",0);
        initView();
        return mView;
    }


    private void initView() {
        vpContainer.setAdapter(new ZhiHuTabAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(vpContainer);
    }


}
