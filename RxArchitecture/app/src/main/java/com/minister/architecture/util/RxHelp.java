package com.minister.architecture.util;

import android.support.annotation.NonNull;

import com.minister.architecture.model.http.result.GankHttpResponse;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/** Rx 帮助类
 * Created by leipe on 2017/9/13.
 */

public class RxHelp {

    public static <T> FlowableTransformer<GankHttpResponse<T>, T> handleResult() {
        return new FlowableTransformer<GankHttpResponse<T>, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<GankHttpResponse<T>> upstream) {
                return upstream
                        .flatMap(new Function<GankHttpResponse<T>, Publisher<T>>() {
                            @Override
                            public Publisher<T> apply(@NonNull final GankHttpResponse<T> tGankHttpResponse) throws Exception {
                                if (tGankHttpResponse.getError()) {
                                    return Flowable.error(new ApiException("接口逻辑错误"));
                                } else {
                                    return Flowable.create(new FlowableOnSubscribe<T>() {
                                        @Override
                                        public void subscribe(@NonNull FlowableEmitter<T> flowable) throws Exception {
                                            try {
                                                flowable.onNext(tGankHttpResponse.getResults());
                                                flowable.onComplete();
                                            } catch (Exception e) {
                                                flowable.onError(e);
                                            }
                                        }
                                    }, BackpressureStrategy.ERROR);
                                }
                            }
                        });
            }
        };
    }

    public static <T> FlowableTransformer<T, T> rxScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
