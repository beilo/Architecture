package com.example.leipe.rx.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.leipe.rx.base.RxHelper;
import com.example.leipe.rx.model.bean.DetailExtraBean;
import com.example.leipe.rx.model.bean.ZhihuDetailBean;
import com.example.leipe.rx.repository.ZhihuRepository;

import io.reactivex.Flowable;

/**
 * Created by 被咯苏州 on 2017/7/2.
 */

public class ZhihuDetailViewModel extends ViewModel {
    private ZhihuRepository repository = new ZhihuRepository();

    ZhihuDetailViewModel() {
    }

    // 日报详情
    public Flowable<ZhihuDetailBean> getDetailInfo(int id) {
        return repository.getDetailInfo(id)
                .compose(RxHelper.<ZhihuDetailBean>rxSchedulerHelper());
    }

    // 日报的额外信息
    public Flowable<DetailExtraBean> getDetailExtraInfo(int id) {
        return repository.getDetailExtraInfo(id)
                .compose(RxHelper.<DetailExtraBean>rxSchedulerHelper());
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
