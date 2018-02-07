package com.minister.architecture.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.facebook.stetho.Stetho;
import com.minister.architecture.BuildConfig;
import com.minister.architecture.di.injector.AppInjector;
import com.minister.architecture.model.bean.DaoMaster;
import com.minister.architecture.model.bean.DaoSession;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by leipe on 2017/8/24.
 */

public class MyApp extends Application implements HasActivityInjector {

    private static MyApp instance;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static synchronized MyApp getInstance() {
        return instance;
    }

    private DaoSession daoSession;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化Injector
        AppInjector.init(this);

        //初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter());

        //初始化Fragmentation
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG) // 实际场景建议.debug(BuildConfig.DEBUG)
                .install();

        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), "aface5f237", BuildConfig.DEBUG);

        //初始化屏幕宽高
        getScreenSize();

        // 初始化SqLite
        initGreenDao();

        Stetho.initializeWithDefaults(this);
    }

    private void initGreenDao() {
        //创建数据库girl.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "girl.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster dao = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = dao.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
