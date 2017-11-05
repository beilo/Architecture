package com.example.leipe.architecture.ui.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.leipe.architecture.R;
import com.example.leipe.architecture.app.Router;
import com.example.leipe.architecture.base.BaseFragment;
import com.example.leipe.architecture.base.http.GankObserver;
import com.example.leipe.architecture.model.bean.GankItemBean;
import com.example.leipe.architecture.model.http.result.GankHttpResponse;
import com.example.leipe.architecture.ui.gank.adapter.GankTechAdapter;
import com.example.leipe.architecture.viewmodel.gank.GankViewModel;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 被咯苏州 on 2017/7/31.
 */
@Route(path = Router.Gank_TECH_LIST)
public class GankTechFragment extends BaseFragment {
    private final int layout = R.layout.list_fragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_tv)
    TextView loadingTv;
    @BindView(R.id.products_list)
    RecyclerView list;

    private boolean isLoading = true;
    GankViewModel viewModel;
    private GankTechAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar(toolbar, "Android技术前线");
        viewModel = (GankViewModel) getViewModel(GankViewModel.class, new GankViewModel());
        adapter = new GankTechAdapter(null, _mActivity);
        list.setLayoutManager(new LinearLayoutManager(_mActivity));
        list.setAdapter(adapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        isLoading();
        viewModel.getTechList(10, 1)
                .observe(this, new GankObserver<GankHttpResponse<List<GankItemBean>>, List<GankItemBean>>() {
                    @Override
                    public void onData(List<GankItemBean> gankItemBeen) {
                        isLoading = false;
                        isLoading();
                        adapter.setList(gankItemBeen);
                    }

                    @Override
                    public void onErrorCode(int code, String msg) {
                        Toast.makeText(_mActivity, msg, Toast.LENGTH_SHORT).show();
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
