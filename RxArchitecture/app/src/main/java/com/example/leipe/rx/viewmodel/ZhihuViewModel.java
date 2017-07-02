package com.example.leipe.rx.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.leipe.rx.base.RxHelper;
import com.example.leipe.rx.model.bean.HotListBean;
import com.example.leipe.rx.repository.ZhihuRepository;

import io.reactivex.Flowable;

/**
 * Created by 被咯苏州 on 2017/7/2.
 */

public class ZhihuViewModel extends ViewModel {

    private ZhihuRepository repository = new ZhihuRepository();

    /**获取热门
     * @return Flowable<HotListBean>
     */
    public Flowable<HotListBean> fetchHotListInfo() {
        return repository.fetchHotListInfo()
                .compose(RxHelper.<HotListBean>rxSchedulerHelper());
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        public Factory() {
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ZhihuViewModel();
        }
    }
}
