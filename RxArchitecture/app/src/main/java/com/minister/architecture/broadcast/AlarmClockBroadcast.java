package com.minister.architecture.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.minister.architecture.model.bean.AlarmClock;
import com.minister.architecture.ui.activity.AlarmClockActivity;
import com.minister.architecture.ui.weather.WeacConstants;
import com.minister.architecture.util.GsonUtil;
import com.minister.architecture.util.MyUtil;

/**
 * @author leipe on 2018/2/8.
 */

public class AlarmClockBroadcast extends BroadcastReceiver {

    public static final String TAG = "AlarmClockBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String stringExtra = intent.getStringExtra(WeacConstants.ALARM_CLOCK);
            AlarmClock alarmClock = GsonUtil.GsonToBean(stringExtra, AlarmClock.class);
            if (alarmClock != null) {
                if (alarmClock.getWeeks() == null) {
                    // 此处指调用一次
                } else {
                    // 接收到立马再调用 -- 重复
                    MyUtil.startAlarmClock(context, alarmClock);
                }
            }
            AlarmClockActivity.startAlarmClockActivity(context);
        }
    }
}
