package com.minister.architecture;

import android.os.Bundle;

import com.minister.architecture.base.BaseSupportActivity;
import com.minister.architecture.ui.MainFragment;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
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
    public void onStart() {
        super.onStart();
        EventBusActivityScope.getDefault(this).register(this);
        EventBusActivityScope.getDefault(this).post("main");
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusActivityScope.getDefault(this).unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void testMain(String message){
        Logger.t("eventMain").d(message);
    }


    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 默认横向切换
        return new DefaultHorizontalAnimator();
    }


}
