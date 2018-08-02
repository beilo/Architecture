package com.minister.architecture.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.baselib.eventbus.GankEvent;
import com.lmroom.baselib.eventbus.ZhiHuEvent;
import com.minister.architecture.R;
import com.minister.architecture.widget.bottomBar.BottomBar;
import com.minister.architecture.widget.bottomBar.BottomBarTab;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

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
        SupportFragment gankTabFragment = (SupportFragment) ARouter.getInstance().build("/gank/tab").navigation();
        SupportFragment zhihuTabFragment = (SupportFragment) ARouter.getInstance().build("/zhihu/tab").navigation();
        SupportFragment journalismTabFragment = (SupportFragment) ARouter.getInstance().build("/journalism/tab").navigation();

        Class<SupportFragment> gankTabClass = (Class<SupportFragment>) gankTabFragment.getClass();
        Class<SupportFragment> zhihuTabClass = (Class<SupportFragment>) zhihuTabFragment.getClass();
        Class<SupportFragment> journalismTabClass = (Class<SupportFragment>) journalismTabFragment.getClass();

        if (findChildFragment(gankTabClass) == null) {
            mFragments[0] = gankTabFragment;
            mFragments[1] = zhihuTabFragment;
            mFragments[2] = journalismTabFragment;
            loadMultipleRootFragment(R.id.fl_container, 0, mFragments);
        } else {
            mFragments[0] = findChildFragment(gankTabClass);
            mFragments[1] = findChildFragment(zhihuTabClass);
            mFragments[2] = findChildFragment(journalismTabClass);
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
        Object dailyDetail = ARouter.getInstance()
                .build("/zhihu/detail")
                .withInt("id", event.getId())
                .navigation();
        start((ISupportFragment) dailyDetail);
    }

    @Subscribe
    public void startDailyDetailFragment(GankEvent.StartTechDetailEvent event) { // 第二种:子fg和父fg通信的方式
        Object dailyDetail = ARouter.getInstance()
                .build("/gank/tech/detail")
                .withString("url", event.getUrl())
                .navigation();
        start((ISupportFragment) dailyDetail);
    }

    @Subscribe
    public void startGirlDetailFragment(GankEvent.StartGirlDetailEvent event) {
        Object girlDetail = ARouter.getInstance()
                .build("/gank/girl/detail")
                .withString("gank_girl_id", event.getId())
                .withString("gank_girl_url", event.getUrl())
                .navigation();
        start((ISupportFragment) girlDetail);
    }
}
