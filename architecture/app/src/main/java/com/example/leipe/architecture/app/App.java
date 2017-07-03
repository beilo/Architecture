package com.example.leipe.architecture.app;

import android.app.Activity;
import android.app.Application;

import com.example.leipe.architecture.BuildConfig;

import java.util.HashSet;
import java.util.Set;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Created by leipe on 2017/6/29.
 */

public class App extends Application {
    static volatile App INSTANCE;
    public static synchronized App getInstance() {
        return INSTANCE;
    }

    private Set<Activity> allActivityList;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initFragmentation();
    }

    private void initFragmentation() {
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(BuildConfig.DEBUG)
                // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获
                // 建议在回调处上传至我们的Crash检测服务器
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
    }


    public void addActivity(Activity act) {
        if (allActivityList == null) {
            allActivityList = new HashSet<>();
        }
        allActivityList.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivityList != null) {
            allActivityList.remove(act);
        }
    }

    public void exitApp() {
        if (allActivityList != null) {
            synchronized (allActivityList) {
                for (Activity act : allActivityList) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

//    private static final Object mDataLock = new Object();
//    static App getInstance(){
//        if (INSTANCE == null){
//            synchronized (mDataLock){
//                if (INSTANCE == null){
//                    INSTANCE = new App();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}
