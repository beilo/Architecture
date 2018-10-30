package com.lmroom.journalism.util;

import android.support.annotation.NonNull;

import com.lmroom.baselib.util.ApiException;
import com.lmroom.journalism.bean.JokeResponse;
import com.lmroom.journalism.bean.JournalismResponse;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx 帮助类
 * Created by leipe on 2017/9/13.
 */

public class JournalismRxHelp {

    public static <T> FlowableTransformer<JournalismResponse<T>, T> handleResultForJournalism() {
        return new FlowableTransformer<JournalismResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<JournalismResponse<T>> upstream) {
                return upstream
                        .flatMap(new Function<JournalismResponse<T>, Publisher<T>>() {
                            @Override
                            public Publisher<T> apply(final JournalismResponse<T> tJournalismResponse) throws Exception {
                                if ("000000".equals(tJournalismResponse.statusCode)) {
                                    return Flowable.create(new FlowableOnSubscribe<T>() {
                                        @Override
                                        public void subscribe(FlowableEmitter<T> flowableEmitter) throws Exception {
                                            try {
                                                flowableEmitter.onNext(tJournalismResponse.result);
                                                flowableEmitter.onComplete();
                                            } catch (Exception e) {
                                                flowableEmitter.onError(e);
                                            }
                                        }
                                    },BackpressureStrategy.ERROR);
                                } else {
                                    return Flowable.error(new ApiException(tJournalismResponse.desc));
                                }
                            }
                        });
            }
        };
    }

    public static <T> FlowableTransformer<JokeResponse<T>, T> handleResultForJoke() {
        return new FlowableTransformer<JokeResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<JokeResponse<T>> upstream) {
                return upstream
                        .flatMap(new Function<JokeResponse<T>, Flowable<T>>() {
                            @Override
                            public Flowable<T> apply(final JokeResponse<T> tJokeResponse) throws Exception {
                                if (0 == tJokeResponse.error_code) {
                                    return Flowable.create(new FlowableOnSubscribe<T>() {
                                        @Override
                                        public void subscribe(FlowableEmitter<T> flowableEmitter) throws Exception {
                                            try {
                                                flowableEmitter.onNext(tJokeResponse.result);
                                                flowableEmitter.onComplete();
                                            } catch (Exception e) {
                                                flowableEmitter.onError(e);
                                            }
                                        }
                                    }, BackpressureStrategy.ERROR);
                                } else {
                                    return Flowable.error(new ApiException(tJokeResponse.reason));
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
