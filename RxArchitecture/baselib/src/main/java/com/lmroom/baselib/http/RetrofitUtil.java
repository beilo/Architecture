package com.lmroom.baselib.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static class RetrofitHolder {
        private static final RetrofitUtil INSTALL = new RetrofitUtil();
    }

    private RetrofitUtil() {
        client = OkHttpUtil.getInstall().getOkHttpClient();
        builder = new Retrofit.Builder();
    }

    private OkHttpClient client;
    private Retrofit.Builder builder;

    public static final RetrofitUtil getInstall() {
        return RetrofitHolder.INSTALL;
    }

    public Retrofit getRetrofit(String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(String url, OkHttpClient client) {
        return builder
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
