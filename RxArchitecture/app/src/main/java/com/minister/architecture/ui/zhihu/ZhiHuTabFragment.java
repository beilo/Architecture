package com.minister.architecture.ui.zhihu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 知乎Tab 主页
 * Created by leipe on 2017/9/18.
 */

public class ZhiHuTabFragment extends BaseSupportFragment {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view)
    View view;

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
        View inflate = inflater.inflate(R.layout.fragment_tab, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        setToolbar(toolbar,"知乎日报",0);
        initView();
        return inflate;
    }


    private void initView() {
        vpContainer.setAdapter(new ZhiHuTabAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(vpContainer);
    }


}
