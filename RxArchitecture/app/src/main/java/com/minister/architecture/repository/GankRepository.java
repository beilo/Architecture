package com.minister.architecture.repository;

import com.minister.architecture.model.bean.GankItemBean;
import com.minister.architecture.model.http.GankApi;
import com.minister.architecture.model.http.result.GankHttpResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by 被咯苏州 on 2017/9/4.
 */
@Singleton
public class GankRepository {
    private final GankApi gankApi;

    @Inject
    public GankRepository(GankApi gankApi) {
        this.gankApi = gankApi;
    }


    public Flowable<GankHttpResponse<List<GankItemBean>>> getTechList(String tech, int num, int page) {
        return gankApi.getTechList(tech, num, page);
    }


    public Flowable<GankHttpResponse<List<GankItemBean>>> getGirlList(int num, int page) {
        return gankApi.getGirlList(num, page);
    }
}
