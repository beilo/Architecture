package com.example.leipe.architecture.repository;


import com.example.leipe.architecture.base.http.RetrofitHelper;
import com.example.leipe.architecture.model.bean.DailyListBean;
import com.example.leipe.architecture.model.bean.DetailExtraBean;
import com.example.leipe.architecture.model.bean.HotListBean;
import com.example.leipe.architecture.model.bean.ZhihuDetailBean;
import com.example.leipe.architecture.model.http.ZhihuApis;

import retrofit2.Call;

/**
 * Created by 被咯苏州 on 2017/7/2.
 */

public class ZhihuRepository {
    private ZhihuApis zhihuApis = RetrofitHelper.getDefault()
            .getZhihuRetrofit()
            .create(ZhihuApis.class);

    // 热门日报
    public Call<HotListBean> fetchHotListInfo() {
        return zhihuApis.getHotList();
    }

    // 日报详情
    public Call<ZhihuDetailBean> getDetailInfo(int id) {
        return zhihuApis.getDetailInfo(id);
    }

    // 日报的额外信息
    public Call<DetailExtraBean> getDetailExtraInfo(int id) {
        return zhihuApis.getDetailExtraInfo(id);
    }

    // 最新日报
    public Call<DailyListBean> getDailyList(){
        return zhihuApis.getDailyList();
    }


}
