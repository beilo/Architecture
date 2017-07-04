package com.example.leipe.architecture.viewmodel.wx;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.leipe.architecture.model.bean.WXHttpResponse;
import com.example.leipe.architecture.repository.WXRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** ViewMddel 控制用于页面的数据,所有数据处理都在这边处理
 * Creted by leipe on 2017/6/28.
 */

public class WXViewModel extends ViewModel {


    private MutableLiveData<WXHttpResponse> wxHttpResponseLiveData = new MutableLiveData<>();

    private WXRepository wxRepository;


    public WXViewModel() {
        this.wxRepository = new WXRepository();
    }

    /**
     * 获取到网络数据
     * @return
     */
    public LiveData<WXHttpResponse> getWxDataCall() {
        wxRepository.getWXDataCall()
                .enqueue(new Callback<WXHttpResponse>() {
                    @Override
                    public void onResponse(Call<WXHttpResponse> call, Response<WXHttpResponse> response) {
                        wxHttpResponseLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<WXHttpResponse> call, Throwable t) {
                    }
                });
        return wxHttpResponseLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        public Factory() {
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new WXViewModel();
        }
    }
}
