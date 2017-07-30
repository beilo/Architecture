package com.example.leipe.architecture.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.leipe.architecture.R;
import com.example.leipe.architecture.app.Router;
import com.example.leipe.architecture.base.BaseFragment;
import com.example.leipe.architecture.ui.wx.fragment.WXHotFragment;
import com.example.leipe.architecture.ui.zhihu.fragment.ZhihuDailyFragment;
import com.example.leipe.architecture.ui.zhihu.fragment.ZhihuHotFragment;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by 被咯苏州 on 2017/7/29.
 */
@Route(path = Router.MAIN)
public class MainFragment extends BaseFragment {
    public final int layout = R.layout.main_fragment;

    @BindView(R.id.draw_left_menu)
    ListView drawLeftMenu;
    @BindView(R.id.draw_layout)
    DrawerLayout drawLayout;

    ISupportFragment[] fragmentList = new ISupportFragment[3];

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpDrawer();
        WXHotFragment wxHotFragment = (WXHotFragment) ARouter.getInstance()
                .build(Router.WX_HOT)
                .navigation();
        ZhihuHotFragment zhihuHotFragment = (ZhihuHotFragment) ARouter.getInstance()
                .build(Router.ZHIHU_HOT)
                .navigation();
        ZhihuDailyFragment zhihuDailyFragment = (ZhihuDailyFragment) ARouter.getInstance()
                .build(Router.ZHIHU_DAILY)
                .navigation();
        fragmentList[0] = wxHotFragment;
        fragmentList[1] = zhihuHotFragment;
        fragmentList[2] = zhihuDailyFragment;
        if (savedInstanceState == null) {
            loadMultipleRootFragment(R.id.fragment_container, 1, fragmentList);
        }
        // else {
            // mFragments[FIRST] = firstFragment;
            // mFragments[SECOND] = findChildFragment(WechatSecondTabFragment.class);
            // mFragments[THIRD] = findChildFragment(WechatThirdTabFragment.class);
        // }
    }


    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(_mActivity);
        drawLeftMenu.addHeaderView(inflater.inflate(R.layout.header_just_username, drawLeftMenu, false));
        drawLeftMenu.setAdapter(new MenuItemAdapter(_mActivity));
        drawLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (l == 0) {
                    showHideFragment(fragmentList[0]);
                } else if (l == 1) {
                    showHideFragment(fragmentList[1]);
                } else if (l == 2) {
                    showHideFragment(fragmentList[2]);
                }
                drawLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return layout;
    }
}
