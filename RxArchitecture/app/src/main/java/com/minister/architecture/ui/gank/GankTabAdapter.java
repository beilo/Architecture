package com.minister.architecture.ui.gank;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.minister.architecture.ui.gank.child.GirlListFragment;
import com.minister.architecture.ui.gank.child.TechListFragment;

/** Gank Tab adapter
 * Created by leipe on 2017/9/15.
 */

public class GankTabAdapter extends FragmentPagerAdapter {



    public GankTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return TechListFragment.newInstance();
            case 1:
                return GirlListFragment.newInstance();
            default:
                return TechListFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Android";
            case 1:
                return "美女";
            default:
                return "不知道";
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }


}
