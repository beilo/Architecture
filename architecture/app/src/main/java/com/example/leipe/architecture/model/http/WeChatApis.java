package com.example.leipe.architecture.model.http;

import com.example.leipe.architecture.model.bean.WXHttpResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeChatApis {
    String HOST = "http://api.tianapi.com/";
    String KEY = "52b7ec3471ac3bec6846577e79f20e4c";
    @GET("wxnew")
    Call<WXHttpResponse> getWXHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);
//    LiveData<WXHttpResponse> getWXHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);
}