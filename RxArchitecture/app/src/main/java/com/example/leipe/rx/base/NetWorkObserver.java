package com.example.leipe.rx.base;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.leipe.rx.model.bean.WXHttpResult;

/**
 * Observer适配器,为了符合业务需求
 * Created by leipe on 2017/6/29.
 */

public abstract class NetWorkObserver<T,R> implements Observer<T> {

    Context mContext;

    public NetWorkObserver(Context mContext) {
        this.mContext = mContext;
    }

    // 成功获取到数据
    public abstract void onData(R r);

    // 不管成功失败都会调用
    public void onComplete() {

    }

    public void onErrorCode(int code, String msg) {

    }


    @Override
    public void onChanged(@Nullable T t) {
        if (t == null) {
            onErrorCode(0, "Null");
        }
        switch (((WXHttpResult) t).getCode()) {
            case 200:
                onData((R) ((WXHttpResult) t).newslist);
                break;
            default:
                onErrorCode(((WXHttpResult) t).getCode(),((WXHttpResult) t).getMsg());
                break;
        }
        onComplete();
    }


}
