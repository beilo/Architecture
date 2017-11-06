package com.example.leipe.architecture.base.http;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.example.leipe.architecture.model.http.result.GankHttpResponse;

/**
 * Created by 被咯苏州 on 2017/7/31.
 */

public abstract class GankObserver<T extends GankHttpResponse,R> implements Observer<T> {
    // 成功获取到数据
    public abstract void onData(R r);

    // 不管成功失败都会调用
    public void onComplete() {

    }

    public void onErrorCode(int code, String msg) {

    }

    @Override
    public void onChanged(@Nullable T t) {
        if (t == null){
            onErrorCode(100, "t is null");
            onComplete();
            return;
        }
        if (!t.getError()){
            onData((R) t.getResults());
        }else {
            onErrorCode(100, "获取数据失败");
        }
        onComplete();
    }
}
