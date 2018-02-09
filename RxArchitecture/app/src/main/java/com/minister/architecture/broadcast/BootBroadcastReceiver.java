package com.minister.architecture.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.minister.architecture.service.DaemonService;

/**
 * 开机启动广播
 * 用于启动DaemonService
 * @author leipe on 2018/2/9.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (ACTION_BOOT.equals(intent.getAction())) {
                Toast.makeText(context, "开机自启DaemonService", Toast.LENGTH_SHORT).show();
                 context.startService(new Intent(context, DaemonService.class));
//                context.startActivity(new Intent(context, MainActivity.class));
            }
        }
    }
}
