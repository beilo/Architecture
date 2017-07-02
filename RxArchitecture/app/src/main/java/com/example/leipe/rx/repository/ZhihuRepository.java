package com.example.leipe.rx.repository;

import com.example.leipe.rx.base.RetrofitHelper;
import com.example.leipe.rx.model.bean.DetailExtraBean;
import com.example.leipe.rx.model.bean.HotListBean;
import com.example.leipe.rx.model.bean.ZhihuDetailBean;
import com.example.leipe.rx.model.http.ZhihuApis;

import io.reactivex.Flowable;

/**
 * Created by 被咯苏州 on 2017/7/2.
 */

public class ZhihuRepository {
    private ZhihuApis zhihuApis = RetrofitHelper.getDefault()
            .getZhihuRetrofit()
            .create(ZhihuApis.class);

    // 热门日报
    public Flowable<HotListBean> fetchHotListInfo() {
        return zhihuApis.getHotList();
    }

    // 日报详情
    public Flowable<ZhihuDetailBean> getDetailInfo(int id) {
        return zhihuApis.getDetailInfo(id);
    }

    // 日报的额外信息
    public Flowable<DetailExtraBean> getDetailExtraInfo(int id) {
        return zhihuApis.getDetailExtraInfo(id);
    }
}
