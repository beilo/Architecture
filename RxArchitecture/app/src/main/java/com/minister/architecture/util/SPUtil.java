package com.minister.architecture.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.minister.architecture.app.MyApp;

/**
 * SharedPreferences 帮助类
 * Created by leipe on 2018/1/19.
 */

public class SPUtil {

    // --------------------- 用到的key -----------------------
    public static final String CITYS = "citys";
    public static final String ALARM_CLOCKS = "alarmClocks";

    // --------------------- 用到的key end-----------------------

    private static final String SHARED_PREFERENCES = "sharedPreferences";
    private static final String SP_UTIL = "SPUtil";


    private static SPUtil mSharedPreferenceUtils;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    @SuppressLint("CommitPrefEdits")
    private SPUtil() {
        mSharedPreferences = MyApp.getInstance().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public static synchronized SPUtil init() {
        if (mSharedPreferenceUtils == null) {
            mSharedPreferenceUtils = new SPUtil();
        }
        return mSharedPreferenceUtils;
    }

    /**
     * @param key 存放的key
     * @param obj 存放的value
     * @return 是否成功
     */
    public boolean put(@NonNull String key, Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Boolean) {
            mSharedPreferencesEditor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            mSharedPreferencesEditor.putFloat(key, (Float) obj);
        } else if (obj instanceof Integer) {
            mSharedPreferencesEditor.putInt(key, (Integer) obj);
        } else if (obj instanceof Long) {
            mSharedPreferencesEditor.putLong(key, (Long) obj);
        } else {
            mSharedPreferencesEditor.putString(key, (String) obj);
        }

        boolean commit = mSharedPreferencesEditor.commit();
        Log.d(SP_UTIL, "key: " + key + " , value: " + obj + " , commit: " + commit);
        return commit;
    }

    public String getString(@NonNull String key) {
        return mSharedPreferences.getString(key, "");

    }

    public Long getLong(@NonNull String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public int getInt(@NonNull String key, @NonNull Integer defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public float getFloat(@NonNull String key, @NonNull Float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    public boolean getBoolean(@NonNull String key, @NonNull Boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public boolean removeKey(String key) {
        mSharedPreferencesEditor.remove(key);
        return mSharedPreferencesEditor.commit();
    }

    public boolean clear() {
        mSharedPreferencesEditor.clear();
        return mSharedPreferencesEditor.commit();
    }
}
