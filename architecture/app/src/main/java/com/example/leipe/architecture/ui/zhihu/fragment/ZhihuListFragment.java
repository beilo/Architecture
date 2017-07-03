package com.example.leipe.architecture.ui.zhihu.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.leipe.architecture.R;
import com.example.leipe.architecture.base.BaseFragment;
import com.example.leipe.architecture.model.bean.HotListBean;
import com.example.leipe.architecture.ui.zhihu.adapter.ZhihuAdapter;
import com.example.leipe.architecture.viewmodel.zhihu.ZhihuViewModel;
import com.gyf.barlibrary.ImmersionBar;

/**
 * 这个fg只使用了rx和viewModel 没有使用LiveData
 * Created by leipe on 2017/6/30.
 */

public class ZhihuListFragment extends BaseFragment {
    private final int layout = R.layout.list_fragment;
    TextView tv_loading;
    RecyclerView rl_list;
    Toolbar toolbar;

    ZhihuViewModel viewModel;

    private boolean isLoading = true;
    ZhihuAdapter mAdapter;

    public static ZhihuListFragment newInstance() {
        ZhihuListFragment fragment = new ZhihuListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_loading = view.findViewById(R.id.loading_tv);
        rl_list = view.findViewById(R.id.products_list);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("知乎热门");
        ImmersionBar.with(this).titleBar(toolbar).init();
        isLoading();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mAdapter = new ZhihuAdapter(_mActivity, null);
        mAdapter.setItemClick(new ZhihuAdapter.OnItemClick() {

            @Override
            public void onClick(View view, HotListBean.RecentBean item, int position) {
                start(ZhihuDetailFragment.newInstance(item.getNews_id(),true));
            }
        });
        rl_list.setLayoutManager(new LinearLayoutManager(_mActivity));
        rl_list.setAdapter(mAdapter);

        ZhihuViewModel.Factory factory = new ZhihuViewModel.Factory();
        viewModel = ViewModelProviders.of(this, factory).get(ZhihuViewModel.class);
        viewModel.fetchHotListInfo()
                .observe(this, new Observer<HotListBean>() {
                    @Override
                    public void onChanged(@Nullable HotListBean hotListBean) {
                        isLoading = false;
                        isLoading();
                        mAdapter.refreshData(hotListBean);
                    }
                });
    }


    private void isLoading() {
        if (isLoading) {
            tv_loading.setVisibility(View.VISIBLE);
            rl_list.setVisibility(View.GONE);
        } else {
            rl_list.setVisibility(View.VISIBLE);
            tv_loading.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return layout;
    }
}
