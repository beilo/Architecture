package com.minister.architecture.model.http;


import com.minister.architecture.model.bean.WXListBean;
import com.minister.architecture.model.http.result.WXHttpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeChatApis {
    String HOST = "http://api.tianapi.com/";
    String KEY = "52b7ec3471ac3bec6846577e79f20e4c";
    @GET("wxnew")
    Call<WXHttpResponse<List<WXListBean>>> getWXHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);
//    LiveData<WXHttpResponse> getWXHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);
}