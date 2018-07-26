package com.minister.architecture.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import com.minister.architecture.ui.gank.child.GirlListFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Gank Tab切换卡
 * Created by leipe on 2017/9/15.
 */
public class GankTabFragment extends BaseSupportFragment {

    TabLayout tab;
    ViewPager vpContainer;
    Toolbar toolbar;
    View _mView;


    public static GankTabFragment newInstance() {
        GankTabFragment fragment = new GankTabFragment();
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


    private OnGankTabFragmentListener mOnGankTabFragmentListener;

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (childFragment instanceof GirlListFragment) {
            ((GirlListFragment) childFragment).setOnStartGirlDetaiListener(new GirlListFragment.OnStartGirlDetaiListener() {
                @Override
                public void onStartGirlDetail(View view, ISupportFragment supportFragment) {
                    mOnGankTabFragmentListener.onStartGirlDetail(view, supportFragment);
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _mView = inflater.inflate(R.layout.fragment_tab, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        return _mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tab = _mView.findViewById(R.id.tab);
        vpContainer = _mView.findViewById(R.id.vp_container);
        toolbar = _mView.findViewById(R.id.toolbar);

        setToolbar(toolbar, "干货集中营", 0);
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabReselected(TabEvent event) {
        Toast.makeText(_mActivity, event.getPosition()+"", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onTabReselected: " + event);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }

    private void initView() {
        vpContainer.setAdapter(new GankTabAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(vpContainer);
    }


    public void setOnGankTabFragmentListener(OnGankTabFragmentListener mOnGankTabFragmentListener) {
        this.mOnGankTabFragmentListener = mOnGankTabFragmentListener;
    }


    public interface OnGankTabFragmentListener {
        void onStartGirlDetail(View view, ISupportFragment supportFragment);
    }
}
