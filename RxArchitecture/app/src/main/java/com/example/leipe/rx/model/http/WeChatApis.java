package com.example.leipe.rx.model.http;

import com.example.leipe.rx.model.bean.WXHttpResult;
import com.example.leipe.rx.model.bean.WXListBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeChatApis {
    String HOST = "http://api.tianapi.com/";
    String KEY = "52b7ec3471ac3bec6846577e79f20e4c";

    @GET("wxnew")
    Flowable<WXHttpResult<List<WXListBean>>> getWXHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);

//    Call<WXHttpResponse> getWXHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);
    //    LiveData<WXHttpResponse> getWXHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);
}