package com.example.leipe.architecture.viewmodel.zhihu;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.leipe.architecture.model.bean.HotListBean;
import com.example.leipe.architecture.repository.ZhihuRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 被咯苏州 on 2017/7/2.
 */

public class ZhihuViewModel extends ViewModel {

    private ZhihuRepository repository = new ZhihuRepository();

    private MutableLiveData<HotListBean> hotListBeanMutableLiveData;

    /**获取热门
     * @return Flowable<HotListBean>
     */
    public LiveData<HotListBean> fetchHotListInfo() {
        if (hotListBeanMutableLiveData == null){
            hotListBeanMutableLiveData = new MutableLiveData<>();
            repository.fetchHotListInfo()
                    .enqueue(new Callback<HotListBean>() {
                        @Override
                        public void onResponse(Call<HotListBean> call, Response<HotListBean> response) {
                            hotListBeanMutableLiveData.setValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<HotListBean> call, Throwable t) {

                        }
                    });
        }
        return hotListBeanMutableLiveData;
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
