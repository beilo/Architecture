package com.minister.architecture.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportFragment;
import com.minister.architecture.ui.gank.child.GirlListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.ISupportFragment;

/** Gank Tab切换卡
 * Created by leipe on 2017/9/15.
 */
public class GankTabFragment extends BaseSupportFragment {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;

    public static GankTabFragment newInstance(){
        GankTabFragment fragment = new GankTabFragment();
        return fragment;
    }

    private OnGankTabFragmentListener mOnGankTabFragmentListener;

    public void setOnGankTabFragmentListener(OnGankTabFragmentListener mOnGankTabFragmentListener) {
        this.mOnGankTabFragmentListener = mOnGankTabFragmentListener;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (childFragment instanceof GirlListFragment){
            ((GirlListFragment) childFragment).setOnStartGirlDetaiListener(new GirlListFragment.OnStartGirlDetaiListener() {
                @Override
                public void onStartGirlDetail(View view, ISupportFragment supportFragment) {
                    mOnGankTabFragmentListener.onStartGirlDetail(view,supportFragment);
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_tab, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        initView();
        return inflate;
    }

    private void initView() {
        vpContainer.setAdapter(new GankTabAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(vpContainer);
    }


    public interface OnGankTabFragmentListener{
        void onStartGirlDetail(View view, ISupportFragment supportFragment);
    }
}
