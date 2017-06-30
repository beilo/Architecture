package com.example.leipe.rx.base;

import com.example.leipe.rx.model.http.WeChatApis;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leipe on 2017/6/28.
 */

public class RetrofitHelper {
    private static volatile RetrofitHelper INSTANCE;

    public static RetrofitHelper getDefault() {
        if (INSTANCE == null) {
            synchronized (RetrofitHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitHelper();
                }
            }
        }
        return INSTANCE;
    }

    private Retrofit retrofit;

    private RetrofitHelper() {
        retrofit = new Retrofit.Builder()
                .baseUrl(WeChatApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
