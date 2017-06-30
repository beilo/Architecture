package com.example.leipe.rx.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.leipe.rx.R;
import com.example.leipe.rx.base.BaseActivity;
import com.example.leipe.rx.view.adapter.MenuItemAdapter;

/**
 * Created by leipe on 2017/6/27.
 */

public class MainSupportActivity extends BaseActivity {
    final int layout = R.layout.main_activity;

    DrawerLayout drawLayout;
    ListView drawLeftMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setUpDrawer();
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fragment_container, WXListSupportFragment.newInstance());
        }
    }

    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(this);
        drawLeftMenu.addHeaderView(inflater.inflate(R.layout.header_just_username, drawLeftMenu, false));
        drawLeftMenu.setAdapter(new MenuItemAdapter(this));
        drawLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
