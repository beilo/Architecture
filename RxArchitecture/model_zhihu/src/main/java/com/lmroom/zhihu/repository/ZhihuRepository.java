package com.lmroom.zhihu.repository;

import android.support.annotation.NonNull;

import com.lmroom.baselib.http.RetrofitUtil;
import com.lmroom.zhihu.api.ZhihuApis;
import com.lmroom.zhihu.bean.DailyListBean;
import com.lmroom.zhihu.bean.HotListBean;
import com.lmroom.zhihu.bean.WelcomeBean;
import com.lmroom.zhihu.bean.ZhihuDetailBean;

import io.reactivex.Flowable;
import io.reactivex.functions.Predicate;

/**
 * 知乎Api 数据提供工厂
 * Created by leipe on 2017/9/18.
 */

public class ZhihuRepository {
    private static class ZhihuHolder {
        private static final ZhihuRepository INSTALL = new ZhihuRepository();
    }

    private ZhihuRepository() {
        this.zhihuApis = RetrofitUtil.getInstall().getRetrofit(ZhihuApis.HOST)
                .create(ZhihuApis.class);
    }

    private ZhihuApis zhihuApis;

    /**
     * Singleton install
     * @return ZhihuRepository
     */
    public static final ZhihuRepository getInstall() {
        return ZhihuHolder.INSTALL;
    }

    /**
     * 热门日报
     *
     * @return Flowable<HotListBean>
     */
    public Flowable<HotListBean> getHotList() {
        Flowable<HotListBean> hotList = this.zhihuApis.getHotList();
        Flowable<HotListBean> hotListBeanFlowable = Flowable.concat(hotList, hotList)
                .filter(new Predicate<HotListBean>() {
                    @Override
                    public boolean test(@NonNull HotListBean data) throws Exception {
                        data.getRecent().get(0).getThumbnail();
                        return true;
                    }
                }).firstElement().toFlowable();
        return hotListBeanFlowable;
    }

    /**
     * 日报详情
     *
     * @param id id
     * @return Flowable<ZhihuDetailBean>
     */
    public Flowable<ZhihuDetailBean> getDetailInfo(int id) {
        return zhihuApis.getDetailInfo(id);
    }

    /**
     * 最新日报
     *
     * @return Flowable<DailyListBean>
     */
    public Flowable<DailyListBean> getDailyList() {
        return zhihuApis.getDailyList();
    }

    /**
     * 知乎启动界面图片
     *
     * @param res img res
     * @return Flowable<WelcomeBean>
     */
    public Flowable<WelcomeBean> getWelcomeInfo(String res) {
        return zhihuApis.getWelcomeInfo(res);
    }
}
