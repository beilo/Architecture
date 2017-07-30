package com.example.leipe.architecture.model.http;

import com.example.leipe.architecture.model.bean.GankItemBean;
import com.example.leipe.architecture.model.http.result.GankHttpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 被咯苏州 on 2017/7/29.
 */

public interface GankApi {
    String HOST = "http://gank.io/api/";

    /**
     * 技术文章列表
     */
    @GET("data/{tech}/{num}/{page}")
    Call<GankHttpResponse<List<GankItemBean>>> getTechList(@Path("tech") String tech,@Path("num") int num,@Path("page") int page);

    /**
     * 妹纸列表
     */
    @GET("data/福利/{num}/{page}")
    Call<GankHttpResponse<List<GankItemBean>>> getGirlList(@Path("num") int num, @Path("page") int page);

    /**
     * 随机妹纸图
     */
    @GET("random/data/福利/{num}")
    Call<GankHttpResponse<List<GankItemBean>>> getRandomGirl(@Path("num") int num);
}
