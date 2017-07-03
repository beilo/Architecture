package com.example.leipe.architecture.viewmodel.zhihu;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.leipe.architecture.model.bean.DetailExtraBean;
import com.example.leipe.architecture.model.bean.ZhihuDetail;
import com.example.leipe.architecture.model.bean.ZhihuDetailBean;
import com.example.leipe.architecture.repository.ZhihuRepository;

import java.util.function.BiFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 被咯苏州 on 2017/7/2.
 */

public class ZhihuDetailViewModel extends ViewModel {
    private ZhihuRepository repository = new ZhihuRepository();

    ZhihuDetailViewModel() {
    }
    private MutableLiveData<ZhihuDetailBean> detailLiveData;

    public LiveData<ZhihuDetailBean> getDetail(int id){
        repository.getDetailInfo(id)
            .enqueue(new Callback<ZhihuDetailBean>() {
                @Override
                public void onResponse(Call<ZhihuDetailBean> call, Response<ZhihuDetailBean> response) {
                    if(detailLiveData == null){
                        detailLiveData = new MutableLiveData<>();
                    }
                    detailLiveData.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ZhihuDetailBean> call, Throwable t) {

                }
            });
        return detailLiveData;
    }

    private MutableLiveData<DetailExtraBean> detailExtraLiveData;
    public LiveData<DetailExtraBean> getDetailExtra(int id){
        repository.getDetailExtraInfo(id)
                .enqueue(new Callback<DetailExtraBean>() {
                    @Override
                    public void onResponse(Call<DetailExtraBean> call, Response<DetailExtraBean> response) {
                        if(detailExtraLiveData == null){
                            detailExtraLiveData = new MutableLiveData<>();
                        }
                        detailExtraLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<DetailExtraBean> call, Throwable t) {

                    }
                });
        return detailExtraLiveData;
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
