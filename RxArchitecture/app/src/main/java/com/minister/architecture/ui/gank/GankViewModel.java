package com.minister.architecture.ui.gank;

import android.arch.lifecycle.ViewModel;

import com.minister.architecture.model.bean.GankItemBean;
import com.minister.architecture.model.http.result.GankHttpResponse;
import com.minister.architecture.repository.GankRepository;
import com.minister.architecture.ui.gank.child.TechListFragment;
import com.minister.architecture.util.RxHelp;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.DefaultSubscriber;

/**
 * android gank viewModel
 * Created by 被咯苏州 on 2017/9/4.
 */

public class GankViewModel extends ViewModel {

    private GankRepository mGankRepository;

    private int num = 10;
    private int techPage = 1;
    private String tech = "Android";

    @Inject
    public GankViewModel(GankRepository mGankRepository) {
        this.mGankRepository = mGankRepository;
    }

    /**
     * android 技术列表(page = 1)
     *
     * @return List<GankItemBean>
     */
    public Flowable<List<GankItemBean>> getTechList() {
        techPage = 1;
        return Flowable.create(new FlowableOnSubscribe<List<GankItemBean>>() {
            @Override
            public void subscribe(@NonNull final FlowableEmitter<List<GankItemBean>> emitter) throws Exception {
                mGankRepository
                        .getTechList(tech, num, techPage)
                        .compose(RxHelp.<GankHttpResponse<List<GankItemBean>>>rxScheduler())
                        .compose(RxHelp.<List<GankItemBean>>handleResult())
                        .subscribe(new Consumer<List<GankItemBean>>() {
                            @Override
                            public void accept(@NonNull List<GankItemBean> gankItemBeen) throws Exception {
                                emitter.onNext(gankItemBeen);
                                emitter.onComplete();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                emitter.onError(throwable);
                            }
                        });
            }
        }, BackpressureStrategy.ERROR);
    }

    /**
     * android 技术列表(page > 1)
     *
     * @param callback 调用view的回调
     * @return List<GankItemBean>
     */
    public Flowable<List<GankItemBean>> pullUpLoadTech(final TechListFragment.TechListCallback callback) {
        return Flowable.create(new FlowableOnSubscribe<List<GankItemBean>>() {
            @Override
            public void subscribe(@NonNull final FlowableEmitter<List<GankItemBean>> emitter) throws Exception {
                mGankRepository
                        .getTechList(tech, num, techPage + 1)
                        .compose(RxHelp.<GankHttpResponse<List<GankItemBean>>>rxScheduler())
                        .compose(RxHelp.<List<GankItemBean>>handleResult())
                        .subscribe(new Consumer<List<GankItemBean>>() {
                            @Override
                            public void accept(@NonNull List<GankItemBean> gankItemBeen) throws Exception {
                                techPage++;
                                emitter.onNext(gankItemBeen);
                                emitter.onComplete();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                callback.call();
                                emitter.onError(throwable);
                            }
                        });
            }
        }, BackpressureStrategy.ERROR);
    }


    private int girlPage = 1;

    public Flowable<List<GankItemBean>> getGirlList() {
        girlPage = 1;
        return mGankRepository
                .getGirlList(num, girlPage)
                .compose(RxHelp.<GankHttpResponse<List<GankItemBean>>>rxScheduler())
                .compose(RxHelp.<List<GankItemBean>>handleResult());
    }


    public Flowable<List<GankItemBean>> pullUpLoadGirl() {
        return Flowable.create(new FlowableOnSubscribe<List<GankItemBean>>() {
            @Override
            public void subscribe(@NonNull final FlowableEmitter<List<GankItemBean>> emitter) throws Exception {
                mGankRepository
                        .getGirlList(num, girlPage + 1)
                        .compose(RxHelp.<GankHttpResponse<List<GankItemBean>>>rxScheduler())
                        .compose(RxHelp.<List<GankItemBean>>handleResult())
                        .subscribe(new DefaultSubscriber<List<GankItemBean>>() {
                            @Override
                            public void onNext(List<GankItemBean> gankItemBeen) {
                                girlPage++;
                                emitter.onNext(gankItemBeen);
                            }

                            @Override
                            public void onError(Throwable t) {
                                emitter.onError(t);
                            }

                            @Override
                            public void onComplete() {
                                emitter.onComplete();
                            }
                        });
            }
        }, BackpressureStrategy.ERROR);
    }


    public Flowable<List<GankItemBean>> getRandomGirl(){
        return mGankRepository
                .getRandomGirl(1)
                .compose(RxHelp.<GankHttpResponse<List<GankItemBean>>>rxScheduler())
                .compose(RxHelp.<List<GankItemBean>>handleResult());
    }

}
