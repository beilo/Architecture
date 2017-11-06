package com.minister.architecture.ui.zhihu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportFragment;
import com.minister.architecture.event.TabEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

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
        EventBusActivityScope.getDefault(_mActivity).register(this);
        return inflate;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabReselected(TabEvent event){
        Toast.makeText(_mActivity, event.getPosition()+"", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onTabReselected: " + event);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }


    private void initView() {
        vpContainer.setAdapter(new ZhiHuTabAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(vpContainer);
    }


}
