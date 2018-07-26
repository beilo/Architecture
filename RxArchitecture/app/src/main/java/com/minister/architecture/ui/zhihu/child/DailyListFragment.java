package com.minister.architecture.ui.zhihu.child;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportFragment;
import com.minister.architecture.model.bean.DailyListBean;
import com.minister.architecture.ui.MainFragment;
import com.minister.architecture.ui.zhihu.ZhiHuDetailFragment;
import com.minister.architecture.util.GlideImageLoader;
import com.minister.architecture.util.RxHelp;
import com.minister.architecture.viewmodel.ZhiHuViewModel;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 日报列表
 * Created by leipe on 2017/9/18.
 */

public class DailyListFragment extends BaseSupportFragment {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    ZhiHuViewModel mZhiHuViewModel;

    @BindView(R.id.products_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
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
        View inflate = inflater.inflate(R.layout.list_fragment, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        initView();
        mZhiHuViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ZhiHuViewModel.class);
        return inflate;
    }

    private void initView() {
        mAdapter = new DailyAdapter(R.layout.item_tech, new ArrayList<DailyListBean.StoriesBean>());
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
                Fragment parentFragment = getParentFragment().getParentFragment();
                if (parentFragment instanceof MainFragment){
                    MainFragment mainFragment = (MainFragment) parentFragment;
                    ZhiHuDetailFragment zhiHuDetailFragment = ZhiHuDetailFragment.newInstance(item.getId());
                    mainFragment.startDailyDetailFragment(zhiHuDetailFragment);
                }
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
        View inflate = getLayoutInflater().inflate(R.layout.view_head_viewpage, (ViewGroup) mRecyclerView.getParent(), false);
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
                Fragment parentFragment = getParentFragment().getParentFragment();
                if (parentFragment instanceof MainFragment){
                    MainFragment mainFragment = (MainFragment) parentFragment;
                    ZhiHuDetailFragment zhiHuDetailFragment = ZhiHuDetailFragment.newInstance(item.getId());
                    mainFragment.startDailyDetailFragment(zhiHuDetailFragment);
                }
            }
        });
        return inflate;
    }

    private void loadData() {
        mDisposable
                .add(mZhiHuViewModel
                        .getDailyList()
                        .compose(RxHelp.<DailyListBean>rxScheduler())
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
