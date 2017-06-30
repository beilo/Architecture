package com.example.leipe.rx.model.http;


import com.example.leipe.rx.base.ApiException;
import com.example.leipe.rx.model.bean.WXHttpResult;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jam on 16-6-12
 * Description: Rx 一些巧妙的处理
 */
public class RxHelper {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<WXHttpResult<T>, T> handleResult() {
        return new FlowableTransformer<WXHttpResult<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<WXHttpResult<T>> upstream) {
                return upstream.flatMap(new Function<WXHttpResult<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(@NonNull WXHttpResult<T> twxHttpResult) throws Exception {
                        if (twxHttpResult.success()){
                            return createData(twxHttpResult.newslist);
                        }else {
                            return Flowable.error(new ApiException(twxHttpResult.getMsg()));
                        }
                    }
                });
            }
        };
    }


    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Flowable<T> createData(final T data) {
        return Flowable.create(
                new FlowableOnSubscribe<T>() {
                    @Override
                    public void subscribe(FlowableEmitter<T> e) {
                        try {
                            e.onNext(data);
                            e.onComplete();
                        } catch (Exception el) {
                            e.onError(el);
                        }
                    }
                },
                BackpressureStrategy.ERROR);

    }


}
