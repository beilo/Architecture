package com.minister.architecture.ui.journalism;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportFragment;
import com.minister.architecture.ui.journalism.child.JokeListFragment;
import com.minister.architecture.ui.journalism.child.JournalismListFragment;

public class JournalismTabFragment extends BaseSupportFragment {
    public static JournalismTabFragment newInstance() {
        Bundle args = new Bundle();
        JournalismTabFragment fragment = new JournalismTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View mView;

    TabLayout tab;
    ViewPager vpContainer;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab,container,false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tab = mView.findViewById(R.id.tab);
        vpContainer = mView.findViewById(R.id.vp_container);
        toolbar = mView.findViewById(R.id.toolbar);
        setToolbar(toolbar,"知乎日报",0);
        vpContainer.setAdapter(new JournalismTabAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(vpContainer);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected int setStatusBarView() {
        return R.id.view;
    }

    @Override
    protected int setTitleBar() {
        return 0;
    }

    static class JournalismTabAdapter extends FragmentPagerAdapter{

        public JournalismTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1){
                return "热点新闻";
            }else {
                return "笑话";
            }
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 1){
                return JournalismListFragment.newInstance();
            }else {
                return JokeListFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }
}
