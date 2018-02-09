package com.minister.architecture;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.minister.architecture.base.BaseSupportActivity;
import com.minister.architecture.event.WeatherEvent;
import com.minister.architecture.service.DaemonService;
import com.minister.architecture.ui.MainFragment;
import com.minister.architecture.ui.weather.WeacConstants;
import com.minister.architecture.util.RxHelp;
import com.minister.architecture.viewmodel.WeatherViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseSupportActivity {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    WeatherViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container,
                    MainFragment.newInstance());
        }


        EventBus.getDefault().register(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WeatherViewModel.class);
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
        EventBus.getDefault().unregister(this);
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
        if (level == TRIM_MEMORY_UI_HIDDEN){
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void invokeWeatherVoice(WeatherEvent event) {
        mDisposable.add(mViewModel.getBroadcastWeather(WeacConstants.CITY)
                .compose(RxHelp.<String>rxScheduler())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        // TODO: 2018/2/8 调用语音接口
                        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(mContext, "语音播放发生异常" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}
