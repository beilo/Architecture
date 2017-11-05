package com.minister.architecture;

import android.os.Bundle;

import com.minister.architecture.base.BaseSupportActivity;
import com.minister.architecture.ui.MainFragment;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseSupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container,
                    MainFragment.newInstance());
        }
    }


    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 默认横向切换
        return new DefaultHorizontalAnimator();
    }


}
