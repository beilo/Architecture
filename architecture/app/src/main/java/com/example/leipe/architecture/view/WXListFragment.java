package com.example.leipe.architecture.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leipe.architecture.R;
import com.example.leipe.architecture.base.BaseFragment;
import com.example.leipe.architecture.model.bean.WXHttpResponse;
import com.example.leipe.architecture.viewmodel.WXViewModel;

/**
 * Created by leipe on 2017/6/27.
 */

public class WXListFragment extends BaseFragment {
    final int layout = R.layout.list_fragment;
    TextView tv_loading;
    RecyclerView rl_list;

    WXAdapter mAdapter;
    private WXViewModel viewModel;
    private boolean isLoading = true;

    public static WXListFragment newInstance() {
        WXListFragment fragment = new WXListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(_mActivity).inflate(layout, container, false);
        tv_loading = inflate.findViewById(R.id.loading_tv);
        rl_list = inflate.findViewById(R.id.products_list);
        isLoading();
        return inflate;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mAdapter = new WXAdapter(_mActivity, null);
        rl_list.setLayoutManager(new LinearLayoutManager(_mActivity));
        rl_list.setAdapter(mAdapter);

        viewModel = ViewModelProviders.of(this).get(WXViewModel.class);
        viewModel.getWxData()
                .observe(this, new Observer<WXHttpResponse>() {
                    @Override
                    public void onChanged(@Nullable WXHttpResponse wxHttpResponse) {
                        if (wxHttpResponse!=null) {
                            isLoading = false;
                            isLoading();
                            mAdapter.refreshData(wxHttpResponse.getNewslist());
                        }
                    }
                });
    }

    private void isLoading(){
        if (isLoading) {
            tv_loading.setVisibility(View.VISIBLE);
            rl_list.setVisibility(View.GONE);
        }else {
            rl_list.setVisibility(View.VISIBLE);
            tv_loading.setVisibility(View.GONE);
        }
    }
}
