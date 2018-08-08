package com.minister.architecture.app;

import com.facebook.stetho.Stetho;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.lmroom.baselib.base.BaseApplication;
import com.minister.architecture.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by leipe on 2017/8/24.
 */

public class MyApp extends BaseApplication {

    private static MyApp instance;


    public static synchronized MyApp getInstance() {
        return instance;
    }

//    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

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
//        initGreenDao();

        Stetho.initializeWithDefaults(this);

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5a7d30e7");
    }

//    private void initGreenDao() {
//        //创建数据库girl.db"
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "girl.db", null);
//        //获取可写数据库
//        SQLiteDatabase db = helper.getWritableDatabase();
//        //获取数据库对象
//        DaoMaster dao = new DaoMaster(db);
//        //获取Dao对象管理者
//        daoSession = dao.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return daoSession;
//    }



}
