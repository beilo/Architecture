package com.minister.architecture.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportFragment;
import com.minister.architecture.event.TabEvent;
import com.minister.architecture.model.bean.AlarmClock;
import com.minister.architecture.ui.gank.GankTabFragment;
import com.minister.architecture.ui.gank.TechDetailFragment;
import com.minister.architecture.ui.weather.WeatherFragment;
import com.minister.architecture.ui.zhihu.ZhiHuDetailFragment;
import com.minister.architecture.ui.zhihu.ZhiHuTabFragment;
import com.minister.architecture.util.MyUtil;
import com.minister.architecture.widget.bottomBar.BottomBar;
import com.minister.architecture.widget.bottomBar.BottomBarTab;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * 主 fragment
 * Created by 被咯苏州 on 2017/9/4.
 */
public class MainFragment extends BaseSupportFragment {
    protected Unbinder unbinder;
    @BindView(R.id.bottom)
    BottomBar mBottomBar;
    private ISupportFragment[] mFragments = new ISupportFragment[3];

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }


    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (childFragment instanceof GankTabFragment) { // 第一种:子fg和父fg通信的方式
            ((GankTabFragment) childFragment).setOnGankTabFragmentListener(new GankTabFragment.OnGankTabFragmentListener() {
                @Override
                public void onStartGirlDetail(View view, ISupportFragment supportFragment) {
                    extraTransaction()
                            .addSharedElement(view, ViewCompat.getTransitionName(view))
                            .start(supportFragment);
                }
            });
        }
    }

    public void startDailyDetailFragment(ZhiHuDetailFragment zhiHuDetailFragment) { // 第二种:子fg和父fg通信的方式
        start(zhiHuDetailFragment);
    }

    public void startTechDetailFragment(TechDetailFragment techDetailFragment) {
        start(techDetailFragment);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        initView();
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GankTabFragment firstFragment = findChildFragment(GankTabFragment.class);
        if (firstFragment == null) {
            mFragments[0] = GankTabFragment.newInstance();
            mFragments[1] = ZhiHuTabFragment.newInstance();
            mFragments[2] = WeatherFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container, 0, mFragments);
        } else {
            mFragments[0] = firstFragment;
            mFragments[1] = findChildFragment(ZhiHuTabFragment.class);
            mFragments[2] = findChildFragment(WeatherFragment.class);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AlarmClock clock = new AlarmClock();
        clock.setId(1);
        clock.setHour(12);
        clock.setMinute(30);
        clock.setWeeks("2,3,4,5,6,7,1");
        MyUtil.startAlarmClock(_mActivity, clock);
    }

    private void initView() {
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
                EventBusActivityScope.getDefault(_mActivity).post(new TabEvent(position));
            }
        });
    }
}
