package com.example.leipe.architecture.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.leipe.architecture.base.RetrofitHelper;
import com.example.leipe.architecture.model.bean.WXHttpResponse;
import com.example.leipe.architecture.model.http.WeChatApis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leipe on 2017/6/28.
 */

public class WXRepository {
    private WeChatApis weChatApis = RetrofitHelper.getDefault()
            .getRetrofit()
            .create(WeChatApis.class);

    public LiveData<WXHttpResponse> getWXData(){
        final MutableLiveData<WXHttpResponse> data = new MutableLiveData<>();
        weChatApis.getWXHot(WeChatApis.KEY,50,1).enqueue(new Callback<WXHttpResponse>() {
            @Override
            public void onResponse(Call<WXHttpResponse> call, Response<WXHttpResponse> response) {
                // error case is left out for brevity
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WXHttpResponse> call, Throwable t) {

            }
        });
        return data;
    }
}
