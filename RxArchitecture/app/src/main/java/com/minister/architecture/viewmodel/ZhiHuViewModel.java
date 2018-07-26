package com.minister.architecture.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.minister.architecture.model.bean.DailyListBean;
import com.minister.architecture.model.bean.HotListBean;
import com.minister.architecture.model.bean.WelcomeBean;
import com.minister.architecture.model.bean.ZhihuDetailBean;
import com.minister.architecture.repository.ZhihuRepository;
import com.minister.architecture.util.RxHelp;

import io.reactivex.Flowable;

/** 知乎 viewModel, 知乎api没有提供异常控制,所以不需要 compose(RxHelp.<T>handleResult())
 * Created by leipe on 2017/9/18.
 */

public class ZhiHuViewModel extends ViewModel {

    private ZhihuRepository repository;

    public ZhiHuViewModel() {
        this.repository = ZhihuRepository.getInstall();
    }

    /**
     * 热门日报
     *
     * @return Flowable<HotListBean>
     */
    public Flowable<HotListBean> getHotList() {
        return repository
                .getHotList();
    }

    /**
     * 日报详情
     *
     * @param id id
     * @return Flowable<ZhihuDetailBean>
     */
    public Flowable<ZhihuDetailBean> getDetailInfo(int id) {
        return repository
                .getDetailInfo(id);
    }

    /**
     * 最新日报
     *
     * @return Flowable<DailyListBean>
     */
    public Flowable<DailyListBean> getDailyList() {
        return repository
                .getDailyList();
    }

    /**
     * 知乎启动界面图片
     *
     * @param res img size 1080*1776
     * @return Flowable<WelcomeBean>
     */
    public Flowable<WelcomeBean> getWelcomeInfo(String res) {
        return repository
                .getWelcomeInfo(res)
                .compose(RxHelp.<WelcomeBean>rxScheduler());
    }
}
