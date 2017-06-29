package com.example.leipe.architecture.base;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.leipe.architecture.model.bean.WXHttpResponse;

/** Observer适配器,为了符合业务需求
 * Created by leipe on 2017/6/29.
 */

public abstract class NetWorkObserver<T extends WXHttpResponse> implements Observer<T> {

    Context mContext;

    public NetWorkObserver(Context mContext) {
        this.mContext = mContext;
    }
    // 成功获取到数据
    public abstract void onData(T t);
    // 不管成功失败都会调用
    public void onComplete() {

    }

    public void onErrorCodeNull() {

    }

    public void onErrorCode100() {

    }

    public void onErrorCode300() {

    }

    public void onErrorCode400() {

    }

    @Override
    public void onChanged(@Nullable T t) {
        if (t == null) {
            onErrorCodeNull();
        } else {
            switch (t.getCode()) {
                case 200:
                    onData(t);
                    break;
                case 100:
                    onErrorCode100();
                    break;
                case 300:
                    onErrorCode300();
                    break;
                case 400:
                    onErrorCode400();
                    break;
            }
        }
        onComplete();
    }


}
