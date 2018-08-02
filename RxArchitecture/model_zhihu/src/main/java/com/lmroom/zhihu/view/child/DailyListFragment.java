package com.lmroom.zhihu.view.child;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.baselib.util.GlideImageLoader;
import com.lmroom.zhihu.R;
import com.lmroom.zhihu.bean.DailyListBean;
import com.lmroom.baselib.eventbus.ZhiHuEvent;
import com.lmroom.zhihu.util.ZhiHuRxHelp;
import com.lmroom.zhihu.viewmodel.ZhiHuViewModel;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * 日报列表
 * Created by leipe on 2017/9/18.
 */
@Route(path = "/zhihu/daily/list")
public class DailyListFragment extends BaseSupportFragment {

    ZhiHuViewModel mZhiHuViewModel;

    private View mView;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mRefresh;
    Banner banner;
    View bannerPreView;

    DailyAdapter mAdapter;


    public static DailyListFragment newInstance() {
        Bundle bundle = new Bundle();
        DailyListFragment fragment = new DailyListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.zhihu_list_fragment, container, false);
        mZhiHuViewModel = ViewModelProviders.of(this).get(ZhiHuViewModel.class);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = mView.findViewById(R.id.products_list);
        mRefresh = mView.findViewById(R.id.refresh);
        initView();
    }

    private void initView() {
        mAdapter = new DailyAdapter(R.layout.zhihu_list_item_tech, new ArrayList<DailyListBean.StoriesBean>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DailyListBean.StoriesBean item = (DailyListBean.StoriesBean) adapter.getItem(position);
                EventBusActivityScope.getDefault(_mActivity).post(new ZhiHuEvent.StartDetailEvent(item.getId()));
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadData();
    }

    List<DailyListBean.TopStoriesBean> mTopStories = new ArrayList<>();

    private View initHeadBanner() {
        View inflate = getLayoutInflater().inflate(R.layout.zhihu_view_head_viewpage, (ViewGroup) mRecyclerView.getParent(), false);
        banner = inflate.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置点击跳转详情页
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                DailyListBean.TopStoriesBean item = mTopStories.get(position);
                EventBusActivityScope.getDefault(_mActivity).post(new ZhiHuEvent.StartDetailEvent(item.getId()));
            }
        });
        return inflate;
    }

    private void loadData() {
        mDisposable
                .add(mZhiHuViewModel
                        .getDailyList()
                        .compose(ZhiHuRxHelp.<DailyListBean>rxScheduler())
                        .subscribe(new Consumer<DailyListBean>() {
                            @Override
                            public void accept(@NonNull DailyListBean dailyListBean) throws Exception {
                                mTopStories = dailyListBean.getTop_stories();
                                List<String> images = new ArrayList<>();
                                List<String> titles = new ArrayList<>();
                                for (DailyListBean.TopStoriesBean item : mTopStories) {
                                    images.add(item.getImage());
                                    titles.add(item.getTitle());
                                }

                                if (mAdapter.getHeaderLayoutCount() <= 0) {
                                    bannerPreView = initHeadBanner();
                                    mAdapter.addHeaderView(bannerPreView);
                                    //设置图片集合
                                    banner.setImages(images);
                                    //设置标题集合（当banner样式有显示title时）
                                    banner.setBannerTitles(titles);
                                    banner.start();

                                } else {
                                    banner.update(images, titles);
                                }

                                mAdapter.replaceData(dailyListBean.getStories());
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


    @Override
    public void onStart() {
        super.onStart();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }
}
