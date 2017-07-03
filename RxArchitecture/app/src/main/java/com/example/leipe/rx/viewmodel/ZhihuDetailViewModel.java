package com.example.leipe.rx.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.leipe.rx.base.RxHelper;
import com.example.leipe.rx.model.bean.DetailExtraBean;
import com.example.leipe.rx.model.bean.ZhihuDetail;
import com.example.leipe.rx.model.bean.ZhihuDetailBean;
import com.example.leipe.rx.repository.ZhihuRepository;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;

/**
 * Created by 被咯苏州 on 2017/7/2.
 */

public class ZhihuDetailViewModel extends ViewModel {
    private ZhihuRepository repository = new ZhihuRepository();

    ZhihuDetailViewModel() {
    }


    public Flowable<ZhihuDetail> getDetailInfo(int id) {
        // 日报详情
        Flowable<ZhihuDetailBean> detailInfo = repository.getDetailInfo(id);
        // 日报的额外信息
        Flowable<DetailExtraBean> detailExtraInfo = repository.getDetailExtraInfo(id);
        Flowable<ZhihuDetail> detailFlowable = Flowable
                .zip(detailInfo, detailExtraInfo, new BiFunction<ZhihuDetailBean, DetailExtraBean, ZhihuDetail>() {
                    @Override
                    public ZhihuDetail apply(@NonNull ZhihuDetailBean o, @NonNull DetailExtraBean o2) throws Exception {
                        return new ZhihuDetail(o, o2);
                    }
                })
                .compose(RxHelper.<ZhihuDetail>rxSchedulerHelper());
        return detailFlowable;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        public Factory() {
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ZhihuDetailViewModel();
        }
    }
}
