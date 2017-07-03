package com.example.leipe.rx.base;

import android.content.Context;
import android.widget.Toast;

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
        }else {
            Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void _onError(int code,String msg){

    }
}
