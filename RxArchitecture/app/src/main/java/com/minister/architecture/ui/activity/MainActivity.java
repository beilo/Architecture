package com.minister.architecture.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportActivity;
import com.minister.architecture.service.DaemonService;
import com.minister.architecture.ui.MainFragment;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseSupportActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container,
                    MainFragment.newInstance());
        }
        startService(new Intent(this, DaemonService.class));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 默认横向切换
        return new DefaultHorizontalAnimator();
    }

    // https://juejin.im/post/59cf0f9e6fb9a00a4b0c73d4?utm_source=gold_browser_extension
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
}
