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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.leipe.architecture.R;
import com.example.leipe.architecture.app.Router;
import com.example.leipe.architecture.base.BaseFragment;
import com.example.leipe.architecture.model.bean.DailyListBean;
import com.example.leipe.architecture.ui.MainFragment;
import com.example.leipe.architecture.ui.zhihu.adapter.TopPagerAdapter;
import com.example.leipe.architecture.ui.zhihu.adapter.ZhihuDailyAdapter;
import com.example.leipe.architecture.viewmodel.GithubViewModelFactory;
import com.example.leipe.architecture.viewmodel.zhihu.ZhihuViewModel;

import butterknife.BindView;

/**
 * 知乎日报列表
 * Created by 被咯苏州 on 2017/7/17.
 */
@Route(path = Router.ZHIHU_DAILY)
public class ZhihuDailyFragment extends BaseFragment {
    private final int layout = R.layout.list_fragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_tv)
    TextView loadingTv;
    @BindView(R.id.products_list)
    RecyclerView list;

    ZhihuViewModel viewModel;
    private boolean isLoading = true;
    private ZhihuDailyAdapter mAdapter;
    MainFragment mainFragment;

    public static ZhihuDetailFragment newInstance() {
        ZhihuDetailFragment fragment = new ZhihuDetailFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar(toolbar,"知乎热门");
        GithubViewModelFactory factory = new GithubViewModelFactory(ZhihuViewModel.class, new ZhihuViewModel());
        viewModel = ViewModelProviders.of(this, factory).get(ZhihuViewModel.class);
        mainFragment = (MainFragment) getParentFragment();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        isLoading();
        list.setLayoutManager(new LinearLayoutManager(_mActivity));
        list.setHasFixedSize(true);
        mAdapter = new ZhihuDailyAdapter(null, null, _mActivity);
        list.setAdapter(mAdapter);
        mAdapter.setmClickListener(new ZhihuDailyAdapter.DailyItemClickListener() {
            @Override
            public void onItemClick(int id) {
                mainFragment.start(ZhihuDetailFragment.newInstance(id, false));
            }
        });
        mAdapter.setmTopClickListener(new TopPagerAdapter.TopItemClickListener() {
            @Override
            public void onItemClick(int id, boolean isNotTransition) {
                mainFragment.start(ZhihuDetailFragment.newInstance(id, isNotTransition));
            }
        });
        viewModel.getDailyList()
                .observe(this, new Observer<DailyListBean>() {
                    @Override
                    public void onChanged(@Nullable DailyListBean dailyListBean) {
                        mAdapter.setmList(dailyListBean);
                        isLoading = false;
                        isLoading();
                    }
                });
    }

    private void isLoading() {

        if (isLoading) {
            loadingTv.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        } else {
            loadingTv.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutId() {
        return layout;
    }
}
