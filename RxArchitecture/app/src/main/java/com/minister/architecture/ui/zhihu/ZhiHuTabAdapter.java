package com.minister.architecture.ui.zhihu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.minister.architecture.ui.zhihu.child.DailyListFragment;
import com.minister.architecture.ui.zhihu.child.HotListFragment;

/** 知乎Tab adapter
 * Created by leipe on 2017/9/18.
 */

public class ZhiHuTabAdapter extends FragmentPagerAdapter {

    public ZhiHuTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return DailyListFragment.newInstance();
            case 1:
                return HotListFragment.newInstance();
            default:
                return DailyListFragment.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "日报";
            case 1:
                return "热门";
            default:
                return "";
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
