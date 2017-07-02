package com.example.leipe.rx.base;

import android.content.Context;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by codeest on 2017/2/23.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T>{
    private Context mContext;

    public CommonSubscriber(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mContext == null) {
            return;
        }
        if (e instanceof ApiException){
            _onError(((ApiException) e).code,e.getMessage());
        }

    }

    public void _onError(int code,String msg){

    }
}
