package com.lmroom.zhihu.api;


import com.lmroom.zhihu.bean.DailyListBean;
import com.lmroom.zhihu.bean.DetailExtraBean;
import com.lmroom.zhihu.bean.HotListBean;
import com.lmroom.zhihu.bean.WelcomeBean;
import com.lmroom.zhihu.bean.ZhihuDetailBean;

import io.reactivex.Flowable;
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
    Flowable<HotListBean> getHotList();

    /**
     * 日报详情
     */
    @GET("news/{id}")
    Flowable<ZhihuDetailBean> getDetailInfo(@Path("id") int id);

    /**
     * 日报的额外信息
     */
    @GET("story-extra/{id}")
    Flowable<DetailExtraBean> getDetailExtraInfo(@Path("id") int id);

    /**
     * 最新日报
     */
    @GET("news/latest")
    Flowable<DailyListBean> getDailyList();

    /**
     * 启动界面图片
     */
    @GET("start-image/{res}")
    Flowable<WelcomeBean> getWelcomeInfo(@Path("res") String res);
}
