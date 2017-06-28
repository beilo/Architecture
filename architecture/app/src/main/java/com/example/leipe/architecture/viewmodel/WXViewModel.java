package com.example.leipe.architecture.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.leipe.architecture.model.bean.WXHttpResponse;

/**
 * Created by leipe on 2017/6/28.
 */

public class WXViewModel extends AndroidViewModel {
    private WXRepository wxRepository;


    public WXViewModel(Application application) {
        super(application);
        this.wxRepository = new WXRepository();
    }

    public LiveData<WXHttpResponse> getWxData(){
        return wxRepository.getWXData();
    }
}
