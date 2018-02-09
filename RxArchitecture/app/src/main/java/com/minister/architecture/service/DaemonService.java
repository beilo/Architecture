package com.minister.architecture.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.minister.architecture.model.bean.AlarmClock;
import com.minister.architecture.util.GsonUtil;
import com.minister.architecture.util.MyUtil;
import com.minister.architecture.util.SPUtil;

import java.util.List;

/**
 * 守护进程service
 *
 * @author leipe on 2018/2/9.
 */

public class DaemonService extends Service {

    /**
     * Log tag ：DaemonService
     */
    private static final String LOG_TAG = "DaemonService";
    /**
     * 定时唤醒的时间间隔，5分钟
     */
    private final static int ALARM_INTERVAL = 5 * 60 * 1000;
    private final static int WAKE_REQUEST_CODE = 6666;

    private final static int GRAY_SERVICE_ID = -1001;

    private Thread mThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getThread().start();
        Toast.makeText(this, "重置service成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(this,DaemonService.class));
    }

    /**
     * 获取线程操作
     * @return Thread
     */
    private Thread getThread() {
        if (mThread == null) {
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String alarmStrings = SPUtil.init().getString(SPUtil.ALARM_CLOCKS);
                    List<AlarmClock> alarmClocks = GsonUtil.GsonToList(alarmStrings, AlarmClock.class);
                    if (alarmClocks != null) {
                        for (AlarmClock item : alarmClocks) {
                            MyUtil.startAlarmClock(DaemonService.this, item);
                        }
                    }
                }
            });
        }
        return mThread;
    }
}
