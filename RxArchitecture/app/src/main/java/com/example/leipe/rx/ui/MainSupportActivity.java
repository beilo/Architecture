package com.example.leipe.rx.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.leipe.rx.R;
import com.example.leipe.rx.base.BaseActivity;
import com.example.leipe.rx.ui.adapter.MenuItemAdapter;
import com.example.leipe.rx.ui.wx.WXListSupportFragment;
import com.example.leipe.rx.ui.zhihu.ZhihuListFragment;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by leipe on 2017/6/27.
 */

public class MainSupportActivity extends BaseActivity {
    final int layout = R.layout.main_activity;

    DrawerLayout drawLayout;
    ListView drawLeftMenu;

    ISupportFragment[] fragmentList = new ISupportFragment[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setUpDrawer();
        if (savedInstanceState == null) {
            fragmentList[0] = WXListSupportFragment.newInstance();
            fragmentList[1] = ZhihuListFragment.newInstance();
            loadMultipleRootFragment(R.id.fragment_container, 1, fragmentList);
        }
    }

    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(this);
        drawLeftMenu.addHeaderView(inflater.inflate(R.layout.header_just_username, drawLeftMenu, false));
        drawLeftMenu.setAdapter(new MenuItemAdapter(this));
        drawLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (l == 0) {
                    showHideFragment(fragmentList[0]);
                    drawLayout.closeDrawer(GravityCompat.START);
                } else if (l == 1) {
                    showHideFragment(fragmentList[1]);
                    drawLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void initView() {
        drawLayout = findViewById(R.id.draw_layout);
        drawLeftMenu = findViewById(R.id.draw_left_menu);
    }

    @Override
    protected int getLayout() {
        return layout;
    }
}
