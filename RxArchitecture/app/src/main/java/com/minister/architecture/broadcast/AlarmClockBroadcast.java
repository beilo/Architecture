package com.minister.architecture.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.minister.architecture.event.WeatherEvent;
import com.minister.architecture.model.bean.AlarmClock;
import com.minister.architecture.ui.weather.WeacConstants;
import com.minister.architecture.util.MyUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * @author leipe on 2018/2/8.
 */

public class AlarmClockBroadcast extends BroadcastReceiver {

    public static final String TAG = "AlarmClockBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            AlarmClock alarmClock = intent.getParcelableExtra(WeacConstants.ALARM_CLOCK);
            if (alarmClock != null) {
                if (alarmClock.getWeeks() == null) {
                    // 此处指调用一次
                } else {
                    // 接收到立马再调用 -- 重复
                    MyUtil.startAlarmClock(context, alarmClock);
                }
            }
            EventBus.getDefault().post(new WeatherEvent());
        }
    }
}
