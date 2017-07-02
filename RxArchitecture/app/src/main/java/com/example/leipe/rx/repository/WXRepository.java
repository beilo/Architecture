package com.example.leipe.rx.repository;

import com.example.leipe.rx.base.RetrofitHelper;
import com.example.leipe.rx.model.bean.WXListBean;
import com.example.leipe.rx.model.bean.WXHttpResult;
import com.example.leipe.rx.model.http.WeChatApis;

import java.util.List;

import io.reactivex.Flowable;

/** 数据工厂,推荐一个模块创建一个工厂
 * Created by leipe on 2017/6/28.
 */
public class WXRepository {
    // TODO: 2017/6/29 是否可用dagger2封装
    private WeChatApis weChatApis = RetrofitHelper.getDefault()
            .getWxRetrofit()
            .create(WeChatApis.class);


    public Flowable<WXHttpResult<List<WXListBean>>> getWXDataCall() {
        return weChatApis.getWXHot(WeChatApis.KEY, 50, 1);
    }

}
