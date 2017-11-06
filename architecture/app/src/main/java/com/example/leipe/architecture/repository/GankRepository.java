package com.example.leipe.architecture.repository;

import com.example.leipe.architecture.base.http.RetrofitHelper;
import com.example.leipe.architecture.model.bean.GankItemBean;
import com.example.leipe.architecture.model.http.GankApi;
import com.example.leipe.architecture.model.http.result.GankHttpResponse;

import java.util.List;

import retrofit2.Call;

/**
 * Created by 被咯苏州 on 2017/7/29.
 */

public class GankRepository {
    private GankApi api = RetrofitHelper.getDefault()
            .getGankRetrofit()
            .create(GankApi.class);

    public Call<GankHttpResponse<List<GankItemBean>>> getTechList(String tech, int num, int page) {
        return api.getTechList(tech, num, page);
    }

    public Call<GankHttpResponse<List<GankItemBean>>> getGirlList(int num, int page){
        return api.getGirlList(num,page);
    }

    public Call<GankHttpResponse<List<GankItemBean>>> getRandomGirl(int num){
        return api.getRandomGirl(num);
    }
}
