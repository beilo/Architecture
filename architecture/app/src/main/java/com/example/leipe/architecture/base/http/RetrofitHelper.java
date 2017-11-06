package com.example.leipe.architecture.base.http;

import com.example.leipe.architecture.model.http.GankApi;
import com.example.leipe.architecture.model.http.WeChatApis;
import com.example.leipe.architecture.model.http.ZhihuApis;

import retrofit2.Retrofit;
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

    private Retrofit.Builder retrofit;

    private RetrofitHelper() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create());
    }

    public Retrofit getWxRetrofit() {
        return retrofit.baseUrl(WeChatApis.HOST).build();
    }

    public Retrofit getZhihuRetrofit() {
        return retrofit.baseUrl(ZhihuApis.HOST).build();
    }

    public Retrofit getGankRetrofit(){
        return retrofit.baseUrl(GankApi.HOST).build();
    }

}
