package com.minister.architecture.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.gank.event.GankEvent;
import com.lmroom.gank.view.GankTabFragment;
import com.lmroom.gank.view.GirlDetailFragment;
import com.lmroom.gank.view.TechDetailFragment;
import com.lmroom.journalism.view.JournalismTabFragment;
import com.lmroom.zhihu.event.ZhiHuEvent;
import com.lmroom.zhihu.view.ZhiHuDetailFragment;
import com.lmroom.zhihu.view.ZhiHuTabFragment;
import com.minister.architecture.R;
import com.minister.architecture.widget.bottomBar.BottomBar;
import com.minister.architecture.widget.bottomBar.BottomBarTab;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * 主 fragment
 * Created by 被咯苏州 on 2017/9/4.
 */
public class MainFragment extends BaseSupportFragment {
    private View _mView;
    BottomBar mBottomBar;
    private ISupportFragment[] mFragments = new ISupportFragment[3];

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _mView = inflater.inflate(R.layout.fragment_main, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        return _mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GankTabFragment firstFragment = findChildFragment(GankTabFragment.class);
        if (firstFragment == null) {
            mFragments[0] = GankTabFragment.newInstance();
            mFragments[1] = ZhiHuTabFragment.newInstance();
            mFragments[2] = JournalismTabFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container, 0, mFragments);
        } else {
            mFragments[0] = firstFragment;
            mFragments[1] = findChildFragment(ZhiHuTabFragment.class);
            mFragments[2] = findChildFragment(JournalismTabFragment.class);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBottomBar = _mView.findViewById(R.id.bottom);
        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.android, "安卓"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.zhihu, "知乎"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.tianqi, "天气"))
        ;
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startDailyDetailFragment(ZhiHuEvent.StartDetailEvent event) { // 第二种:子fg和父fg通信的方式
        start(ZhiHuDetailFragment.newInstance(event.getId()));
    }

    @Subscribe
    public void startDailyDetailFragment(GankEvent.StartTechDetailEvent event) { // 第二种:子fg和父fg通信的方式
        start(TechDetailFragment.newInstance(event.getUrl()));
    }

    @Subscribe
    public void startGirlDetailFragment(GankEvent.StartGirlDetailEvent event) {
        start(GirlDetailFragment.newInstance(event.getId(), event.getUrl()));
    }
}
