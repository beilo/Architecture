package com.minister.architecture.repository;

import com.minister.architecture.app.MyApp;
import com.minister.architecture.model.bean.GankItemBean;
import com.minister.architecture.model.http.GankApi;
import com.minister.architecture.model.http.result.GankHttpResponse;
import com.minister.architecture.util.ApiException;
import com.minister.architecture.util.NetWorkUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by 被咯苏州 on 2017/9/4.
 */
@Singleton
public class GankRepository {

    @Inject
    MyApp app;

    private final GankApi gankApi;

    @Inject
    public GankRepository(GankApi gankApi) {
        this.gankApi = gankApi;
    }

    /**
     * 获取干货信息
     * @param tech 搜索条件
     * @param num 每页数量
     * @param page 页码
     */
    public Flowable<GankHttpResponse<List<GankItemBean>>> getTechList(String tech, int num, int page) {
        return gankApi.getTechList(tech, num, page);
    }

    /**
     * 获取美女图片
     * @param num 每页数量
     * @param page 页码
     */
    public Flowable<GankHttpResponse<List<GankItemBean>>> getGirlList(int num, int page) {
        if (NetWorkUtils.isNetworkConnected(app)) {
            return gankApi.getGirlList(num, page);
        }else {
            return Flowable.error(new ApiException("没有网络哟"));
        }

    }

    /**
     * 随机妹子图片
     * @param num 数量
     */
    public Flowable<GankHttpResponse<List<GankItemBean>>> getRandomGirl(int num){
        return gankApi.getRandomGirl(num);
    }
}
