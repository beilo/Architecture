package com.example.leipe.architecture.repository;

import com.example.leipe.architecture.base.RetrofitHelper;
import com.example.leipe.architecture.model.bean.WXHttpResponse;
import com.example.leipe.architecture.model.http.WeChatApis;

import retrofit2.Call;

/** 数据工厂,推荐一个模块创建一个工厂
 * Created by leipe on 2017/6/28.
 */

public class WXRepository {
    // TODO: 2017/6/29 是否可用dagger2封装
    private WeChatApis weChatApis = RetrofitHelper.getDefault()
            .getRetrofit()
            .create(WeChatApis.class);

    public Call<WXHttpResponse> getWXDataCall() {
        return weChatApis.getWXHot(WeChatApis.KEY, 50, 1);
    }
}
