package com.example.leipe.rx.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.leipe.rx.model.bean.WXHttpResult;
import com.example.leipe.rx.model.bean.WXListBean;
import com.example.leipe.rx.model.http.RxHelper;
import com.example.leipe.rx.repository.WXRepository;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * ViewMddel 控制用于页面的数据,所有数据处理都在这边处理
 * Created by leipe on 2017/6/28.
 */

public class WXViewModel extends ViewModel {


    private MutableLiveData<WXHttpResult<List<WXListBean>>> wxHttpResponseLiveData = new MutableLiveData<>();

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

        wxRepository.getWXDataCall()
                .compose(RxHelper.<WXHttpResult<List<WXListBean>>>rxSchedulerHelper())
//                .compose(RxHelper.<List<WXListBean>>handleResult()) 注释的原因是统一处理失去了错误码,view层无法知道
                .subscribe(new Consumer<WXHttpResult<List<WXListBean>>>() {
                    @Override
                    public void accept(@NonNull WXHttpResult<List<WXListBean>> wxListBeanWXHttpResult) throws Exception {
                        wxHttpResponseLiveData.setValue(wxListBeanWXHttpResult);
                    }
                });
        return wxHttpResponseLiveData;
    }

    // 当集成的是ViewModel而不是AndroidViewModel时要用工厂模式
        public static class Factory extends ViewModelProvider.NewInstanceFactory {

            public Factory() {
            }

            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                //noinspection unchecked
                return (T) new WXViewModel();
            }
        }
}
