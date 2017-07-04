package com.example.leipe.architecture.viewmodel.wx;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.leipe.architecture.model.bean.WXHttpResult;
import com.example.leipe.architecture.model.bean.WXListBean;
import com.example.leipe.architecture.repository.WXRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewMddel 控制用于页面的数据,所有数据处理都在这边处理
 * Creted by leipe on 2017/6/28.
 */

public class WXViewModel extends ViewModel {


    private MutableLiveData<WXHttpResult<List<WXListBean>>> wxHttpResponseLiveData;

    private WXRepository wxRepository;


    public WXViewModel() {
        this.wxRepository = new WXRepository();
    }

    /**
     * 获取到网络数据
     *
     * @return
     */
    public LiveData<WXHttpResult<List<WXListBean>>> getWxDataCall() {
        if (wxHttpResponseLiveData == null) {
            wxHttpResponseLiveData = new MutableLiveData<>();
            wxRepository.getWXDataCall()
                    .enqueue(new Callback<WXHttpResult<List<WXListBean>>>() {
                        @Override
                        public void onResponse(Call<WXHttpResult<List<WXListBean>>> call, Response<WXHttpResult<List<WXListBean>>> response) {
                            wxHttpResponseLiveData.setValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<WXHttpResult<List<WXListBean>>> call, Throwable t) {
                        }
                    });
        }
        return wxHttpResponseLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        public Factory() {
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new WXViewModel();
        }
    }
}
