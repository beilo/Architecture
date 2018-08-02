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
import com.lmroom.zhihu.R;
import com.lmroom.zhihu.bean.HotListBean;
import com.lmroom.baselib.eventbus.ZhiHuEvent;
import com.lmroom.zhihu.util.ZhiHuRxHelp;
import com.lmroom.zhihu.viewmodel.ZhiHuViewModel;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * 热门列表
 * Created by leipe on 2017/9/19.
 */
@Route(path = "/zhihu/hot/list")
public class HotListFragment extends BaseSupportFragment {

    ZhiHuViewModel mZhihuViewModel;

    private View mView;
    RecyclerView mRecyclerView;
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
        mView = inflater.inflate(R.layout.zhihu_list_fragment, container, false);
        mRecyclerView = mView.findViewById(R.id.products_list);
        mRefresh = mView.findViewById(R.id.refresh);
        mZhihuViewModel = ViewModelProviders.of(this).get(ZhiHuViewModel.class);
        initView();
        return mView;
    }

    private void initView() {
        mAdapter = new HotAdapter(R.layout.zhihu_list_item_tech, new ArrayList<HotListBean.RecentBean>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HotListBean.RecentBean item = (HotListBean.RecentBean) adapter.getItem(position);
                EventBusActivityScope.getDefault(_mActivity).post(new ZhiHuEvent.StartDetailEvent(item.getNews_id()));
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
                        .compose(ZhiHuRxHelp.<HotListBean>rxScheduler())
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
