package com.minister.architecture.ui.zhihu.child;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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
import com.minister.architecture.model.bean.HotListBean;
import com.minister.architecture.ui.MainFragment;
import com.minister.architecture.ui.zhihu.ZhiHuDetailFragment;
import com.minister.architecture.ui.zhihu.ZhiHuViewModel;
import com.minister.architecture.util.RxHelp;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 热门列表
 * Created by leipe on 2017/9/19.
 */

public class HotListFragment extends BaseSupportFragment {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    ZhiHuViewModel mZhihuViewModel;

    @BindView(R.id.products_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    HotAdapter mAdapter;

    public static HotListFragment newInstance() {
        Bundle bundle = new Bundle();
        HotListFragment fragment = new HotListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.list_fragment, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        mZhihuViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ZhiHuViewModel.class);
        initView();
        return inflate;
    }

    private void initView() {
        mAdapter = new HotAdapter(R.layout.item_tech, new ArrayList<HotListBean.RecentBean>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HotListBean.RecentBean item = (HotListBean.RecentBean) adapter.getItem(position);
                Fragment parentFragment = getParentFragment().getParentFragment();
                if (parentFragment instanceof MainFragment) {
                    MainFragment mainFragment = (MainFragment) parentFragment;
                    ZhiHuDetailFragment zhiHuDetailFragment = ZhiHuDetailFragment.newInstance(item.getNews_id());
                    mainFragment.startDailyDetailFragment(zhiHuDetailFragment);
                }
            }
        });
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadData();
    }

    private void loadData() {
        mDisposable
                .add(mZhihuViewModel
                        .getHotList()
                        .compose(RxHelp.<HotListBean>rxScheduler())
                        .subscribe(new Consumer<HotListBean>() {
                            @Override
                            public void accept(@NonNull HotListBean hotListBean) throws Exception {
                                mAdapter.replaceData(hotListBean.getRecent());
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
}
