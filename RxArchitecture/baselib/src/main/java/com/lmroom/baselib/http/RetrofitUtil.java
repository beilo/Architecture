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
    }

    private OkHttpClient client = OkHttpUtil.getInstall().getOkHttpClient();
    private Retrofit.Builder builder;

    public static final RetrofitUtil getInstall() {
        return RetrofitHolder.INSTALL;
    }

    public Retrofit getRetrofit(String url) {
        if (builder == null) {
            builder = new Retrofit.Builder();
        }
        return builder
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(String url,OkHttpClient client) {
        if (builder == null) {
            builder = new Retrofit.Builder();
        }
        return builder
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
