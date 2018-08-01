package com.lmroom.gank.view.child;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lmroom.baselib.base.BaseApplication;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.gank.R;
import com.lmroom.gank.bean.GankItemBean;
import com.lmroom.gank.event.GankEvent;
import com.lmroom.gank.util.GankRxHelp;
import com.lmroom.gank.viewmodel.GankViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


/**
 * 美女图片列表
 * Created by leipe on 2017/9/14.
 */
@Route(path = "/gank/girl/list")
public class GirlListFragment extends BaseSupportFragment {

    private View _mView;
    RecyclerView mRecycler;
    SwipeRefreshLayout mRefresh;

    GankViewModel mGankViewModel;

    GirlAdapter mAdapter;

    public static GirlListFragment newInstance() {
        GirlListFragment fragment = new GirlListFragment();
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getGirlData();
    }

    private void getGirlData() {
        mDisposable.add(
                mGankViewModel
                        .getGirlList()
                        .compose(GankRxHelp.<List<GankItemBean>>rxScheduler())
                        .subscribe(new Consumer<List<GankItemBean>>() {
                            @Override
                            public void accept(@NonNull List<GankItemBean> gankItemBeen) throws Exception {
                                setImageHeight(gankItemBeen, true);
                                mRefresh.setRefreshing(false);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                mRefresh.setRefreshing(false);
                            }
                        })
        );
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _mView = inflater.inflate(R.layout.gank_fragment_list, container, false);
        return _mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler  = _mView.findViewById(R.id.products_list);
        mRefresh  = _mView.findViewById(R.id.refresh);
        initView();
        mGankViewModel = ViewModelProviders.of(this).get(GankViewModel.class);
    }

    private void initView() {
        mAdapter = new GirlAdapter(R.layout.gank_list_item_girl, new ArrayList<GankItemBean>());
        mAdapter.setFooterViewAsFlow(true);
        mAdapter.setHeaderViewAsFlow(true);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        //fix issue #52 https://github.com/codeestX/GeekNews/issues/52
        mStaggeredGridLayoutManager.setItemPrefetchEnabled(false);
        mRecycler.setLayoutManager(mStaggeredGridLayoutManager);
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGirlData();
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDisposable.add(
                                mGankViewModel
                                        .pullUpLoadGirl()
                                        .compose(GankRxHelp.<List<GankItemBean>>rxScheduler())
                                        .subscribe(new Consumer<List<GankItemBean>>() {
                                            @Override
                                            public void accept(@NonNull List<GankItemBean> gankItemBeen) throws Exception {
                                                setImageHeight(gankItemBeen, false);
                                                mAdapter.loadMoreComplete();
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(@NonNull Throwable throwable) throws Exception {
                                                Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                        );
                    }
                }, 500);
            }
        }, mRecycler);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GankItemBean item = (GankItemBean) adapter.getItem(position);
                EventBusActivityScope.getDefault(_mActivity).post(new GankEvent.StartGirlDetailEvent(item.get_id(),item.getUrl()));
                // activity共享元素
                //                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(_mActivity, adapter.getViewByPosition(position, R.id.imageView), "shareView");
                //                Intent intent = new Intent(_mActivity, GirlDetailActivity.class);
                //                intent.putExtra(IT_GANK_GRIL_URL, item.getUrl());
                //                _mActivity.startActivity(intent, compat.toBundle());
            }
        });
    }

    public void setImageHeight(final List<GankItemBean> gankItemBeanList, final boolean isRefresh) {
        final List<GankItemBean> newList = new ArrayList<>();
        Flowable
                .fromIterable(gankItemBeanList)
                .map(new Function<GankItemBean, GankItemBean>() {
                    @Override
                    public GankItemBean apply(@NonNull GankItemBean gankItemBeen) throws Exception {
                        if (gankItemBeen.getHeight() == 0) {
                            Bitmap bitmap = Glide
                                    .with(_mActivity)
                                    .load(gankItemBeen.getUrl())
                                    .asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                            int bHeight = bitmap.getHeight();
                            int bWeight = bitmap.getWidth();
                            int realHeight = (int) ((float) (BaseApplication.SCREEN_WIDTH / 2) / bWeight * bHeight);
                            gankItemBeen.setHeight(realHeight);
                            bitmap.recycle();
                        }
                        return gankItemBeen;
                    }
                })
                .compose(GankRxHelp.<GankItemBean>rxScheduler())
                .subscribe(new Consumer<GankItemBean>() {
                    @Override
                    public void accept(@NonNull GankItemBean gankItemBeen) throws Exception {
                        newList.add(gankItemBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (isRefresh) {
                            mAdapter.replaceData(newList);
                        } else {
                            mAdapter.addData(newList);
                        }
                    }
                });
    }
}
