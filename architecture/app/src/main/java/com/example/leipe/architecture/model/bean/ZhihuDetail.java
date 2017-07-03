package com.example.leipe.architecture.model.bean;

/** 用于整合操作 Flowable.zip
 * Created by leipe on 2017/7/3.
 */

public class ZhihuDetail {
    ZhihuDetailBean zhihuDetailBean;
    DetailExtraBean detailExtraBean;

    public ZhihuDetail(ZhihuDetailBean zhihuDetailBean, DetailExtraBean detailExtraBean) {
        this.zhihuDetailBean = zhihuDetailBean;
        this.detailExtraBean = detailExtraBean;
    }

    public ZhihuDetailBean getZhihuDetailBean() {
        return zhihuDetailBean;
    }

    public DetailExtraBean getDetailExtraBean() {
        return detailExtraBean;
    }
}
