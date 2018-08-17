package com.minister.architecture.repository;

import com.minister.architecture.model.bean.DailyListBean;
import com.minister.architecture.model.bean.HotListBean;
import com.minister.architecture.model.bean.WelcomeBean;
import com.minister.architecture.model.bean.ZhihuDetailBean;
import com.minister.architecture.model.http.ZhihuApis;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

/**
 * 知乎Api 数据提供工厂
 * Created by leipe on 2017/9/18.
 */
@Singleton
public class ZhihuRepository {
    ZhihuApis zhihuApis;

    @Inject
    public ZhihuRepository(ZhihuApis zhihuApis) {
        this.zhihuApis = zhihuApis;
    }

    /**
     * 热门日报
     *
     * @return Flowable<HotListBean>
     */
    public Flowable<HotListBean> getHotList() {
        Flowable<HotListBean> hotList = zhihuApis.getHotList();
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
