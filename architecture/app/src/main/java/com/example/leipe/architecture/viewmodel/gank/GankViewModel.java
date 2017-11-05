package com.example.leipe.architecture.viewmodel.gank;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.leipe.architecture.model.bean.GankItemBean;
import com.example.leipe.architecture.model.http.result.GankHttpResponse;
import com.example.leipe.architecture.repository.GankRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 被咯苏州 on 2017/7/31.
 */

public class GankViewModel extends ViewModel {
    private MutableLiveData<GankHttpResponse<List<GankItemBean>>> itemLiveData;
    private GankRepository gankRepository;

    public GankViewModel() {
        this.gankRepository = new GankRepository();
    }

    /**
     * 每日十条
     */
    public LiveData<GankHttpResponse<List<GankItemBean>>> getTechList(int num, int page) {
        if (itemLiveData == null) {
            itemLiveData = new MutableLiveData<>();
            gankRepository.getTechList("Android", num, page)
                    .enqueue(new Callback<GankHttpResponse<List<GankItemBean>>>() {
                        @Override
                        public void onResponse(Call<GankHttpResponse<List<GankItemBean>>> call,
                                               Response<GankHttpResponse<List<GankItemBean>>> response) {
                            GankHttpResponse<List<GankItemBean>> body = response.body();
                            itemLiveData.setValue(body);
                        }

                        @Override
                        public void onFailure(Call<GankHttpResponse<List<GankItemBean>>> call, Throwable t) {

                        }
                    });
        }
        return itemLiveData;
    }

    /**
     * 妹子福利
     */
    public LiveData<GankHttpResponse<List<GankItemBean>>> getGirlList(int num, int page) {
        if (itemLiveData == null) {
            itemLiveData = new MutableLiveData<>();
            gankRepository.getGirlList(num, page)
                    .enqueue(new Callback<GankHttpResponse<List<GankItemBean>>>() {
                        @Override
                        public void onResponse(Call<GankHttpResponse<List<GankItemBean>>> call,
                                               Response<GankHttpResponse<List<GankItemBean>>> response) {
                            GankHttpResponse<List<GankItemBean>> body = response.body();
                            itemLiveData.setValue(body);
                        }

                        @Override
                        public void onFailure(Call<GankHttpResponse<List<GankItemBean>>> call, Throwable t) {

                        }
                    });
        }
        return itemLiveData;
    }
}
