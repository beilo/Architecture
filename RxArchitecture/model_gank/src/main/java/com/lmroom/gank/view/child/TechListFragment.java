package com.lmroom.gank.view.child;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.gank.R;
import com.lmroom.gank.bean.GankItemBean;
import com.lmroom.baselib.eventbus.GankEvent;
import com.lmroom.gank.util.GankRxHelp;
import com.lmroom.gank.viewmodel.GankViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * android gank view
 * Created by 被咯苏州 on 2017/8/27.
 */
@Route(path = "/gank/tech/list")
public class TechListFragment extends BaseSupportFragment {


    GankViewModel mGankViewModel;

    private View _mView;
    RecyclerView mRecycleView;
    SwipeRefreshLayout mRefresh;

    private TechAdapter mBaseAdapter;

    public static TechListFragment newInstance() {
        TechListFragment fragment = new TechListFragment();
        return fragment;
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
        mRecycleView = _mView.findViewById(R.id.products_list);
        mRefresh = _mView.findViewById(R.id.refresh);
        mGankViewModel = ViewModelProviders.of(this).get(GankViewModel.class);
        initView();
    }

    private void initView() {
        mBaseAdapter = new TechAdapter(R.layout.gank_list_item_tech, new ArrayList<GankItemBean>());
        mRecycleView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecycleView.setAdapter(mBaseAdapter);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDisposable.add(
                        mGankViewModel.getTechList()
                                .compose(GankRxHelp.<List<GankItemBean>>rxScheduler())
                                .subscribe(new Consumer<List<GankItemBean>>() {
                                    @Override
                                    public void accept(@NonNull List<GankItemBean> gankItemBeen) throws Exception {
                                        mBaseAdapter.replaceData(gankItemBeen);
                                        mRefresh.setRefreshing(false);
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(@NonNull Throwable throwable) throws Exception {
                                        Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        mRefresh.setRefreshing(false);
                                    }
                                }));
            }
        });


        mBaseAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecycleView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("load", "onScrolled: ------------");
                        mDisposable.add(
                                mGankViewModel.pullUpLoadTech(
                                        new TechListCallback() {
                                            @Override
                                            public void call() {
                                                // 这里的是为了展示如何从viewModel调用ui
                                                Toast.makeText(_mActivity, "上拉加载成功", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .compose(GankRxHelp.<List<GankItemBean>>rxScheduler())
                                        .subscribe(new Consumer<List<GankItemBean>>() {
                                            @Override
                                            public void accept(@NonNull List<GankItemBean> gankItemBeen) throws Exception {
                                                mBaseAdapter.addData(gankItemBeen);
                                                mBaseAdapter.loadMoreComplete();
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(@NonNull Throwable throwable) throws Exception {
                                                Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }));
                    }
                }, 500);

            }
        }, mRecycleView);

        mBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GankItemBean item = (GankItemBean) adapter.getItem(position);
                EventBusActivityScope.getDefault(_mActivity).post(new GankEvent.StartTechDetailEvent(item.getUrl()));
            }
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.gank_head_view_image, (ViewGroup) mRecycleView.getParent(), false);
        final ImageView imgContainer = view.findViewById(R.id.img_container);
        mBaseAdapter.addHeaderView(view);

        final ArrayMap<String, List<GankItemBean>> map = new ArrayMap<>();
        Flowable<List<GankItemBean>> randomGirl = mGankViewModel.getRandomGirl();
        Flowable<List<GankItemBean>> techList = mGankViewModel.getTechList();

        mDisposable.add(
                Flowable.zip(randomGirl
                        , techList
                        , new BiFunction<List<GankItemBean>, List<GankItemBean>, ArrayMap<String, List<GankItemBean>>>() {
                            @Override
                            public ArrayMap<String, List<GankItemBean>> apply(@NonNull List<GankItemBean> gankItemBeen, @NonNull List<GankItemBean> gankItemBeen2) throws Exception {
                                map.put("head", gankItemBeen);
                                map.put("content", gankItemBeen2);
                                return map;
                            }
                        })
                        .compose(GankRxHelp.<ArrayMap<String, List<GankItemBean>>>rxScheduler())
                        .subscribe(new Consumer<ArrayMap<String, List<GankItemBean>>>() {
                            @Override
                            public void accept(@NonNull ArrayMap<String, List<GankItemBean>> stringListArrayMap) throws Exception {
                                List<GankItemBean> head = stringListArrayMap.get("head");
                                List<GankItemBean> content = stringListArrayMap.get("content");
                                Glide
                                        .with(_mActivity)
                                        .load(head.get(0).getUrl())
                                        .asBitmap()
                                        .into(imgContainer);
                                mBaseAdapter.replaceData(content);
                                mRefresh.setRefreshing(false);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                mRefresh.setRefreshing(false);
                            }
                        }));
    }

    public interface TechListCallback {
        void call();
    }
}
