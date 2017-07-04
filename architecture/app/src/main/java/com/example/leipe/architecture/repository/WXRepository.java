package com.example.leipe.architecture.repository;

import com.example.leipe.architecture.base.RetrofitHelper;
import com.example.leipe.architecture.model.bean.WXHttpResult;
import com.example.leipe.architecture.model.bean.WXListBean;
import com.example.leipe.architecture.model.http.WeChatApis;

import java.util.List;

import retrofit2.Call;

/** 数据工厂,推荐一个模块创建一个工厂
 * Created by leipe on 2017/6/28.
 */

public class WXRepository {
    // TODO: 2017/6/29 是否可用dagger2封装
    private WeChatApis weChatApis = RetrofitHelper.getDefault()
            .getWxRetrofit()
            .create(WeChatApis.class);

    public Call<WXHttpResult<List<WXListBean>>> getWXDataCall() {
        return weChatApis.getWXHot(WeChatApis.KEY, 50, 1);
    }
}
