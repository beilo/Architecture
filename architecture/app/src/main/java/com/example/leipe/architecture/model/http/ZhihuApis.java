package com.example.leipe.architecture.model.http;

import com.example.leipe.architecture.model.bean.DailyListBean;
import com.example.leipe.architecture.model.bean.DetailExtraBean;
import com.example.leipe.architecture.model.bean.HotListBean;
import com.example.leipe.architecture.model.bean.ZhihuDetailBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 被咯苏州 on 2017/7/2.
 */

public interface ZhihuApis {
    String HOST = "http://news-at.zhihu.com/api/4/";

    /**
     * 热门日报
     */
    @GET("news/hot")
    Call<HotListBean> getHotList();

    /**
     * 日报详情
     */
    @GET("news/{id}")
    Call<ZhihuDetailBean> getDetailInfo(@Path("id") int id);

    /**
     * 日报的额外信息
     */
    @GET("story-extra/{id}")
    Call<DetailExtraBean> getDetailExtraInfo(@Path("id") int id);

    /**
     * 最新日报
     */
    @GET("news/latest")
    Call<DailyListBean> getDailyList();
}
