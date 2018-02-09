package com.minister.architecture.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportActivity;
import com.minister.architecture.ui.weather.WeatherFragment;

/**
 * 闹钟响起页面
 * Created by leipe on 2018/2/9.
 */

public class AlarmClockActivity extends BaseSupportActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, AlarmClockActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findFragment(WeatherFragment.class) == null) {
            loadRootFragment(R.id.fl_container,
                    WeatherFragment.newInstance(true));
        }
    }
}
