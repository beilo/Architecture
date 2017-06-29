package com.example.leipe.architecture.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leipe.architecture.R;
import com.example.leipe.architecture.base.BaseFragment;
import com.example.leipe.architecture.base.NetWorkObserver;
import com.example.leipe.architecture.model.bean.WXHttpResponse;
import com.example.leipe.architecture.viewmodel.WXViewModel;

/** 微信热门前50条
 * Created by leipe on 2017/6/27.
 */

public class WXListFragment extends BaseFragment {
    final String TAG = this.getClass().getSimpleName();
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

        viewModel.getWxDataCall()
                .observe(this, new NetWorkObserver<WXHttpResponse>(_mActivity) {
                    @Override
                    public void onData(WXHttpResponse wxHttpResponse) {
                        // 这里就有了活跃的LiveData
                        if (wxHttpResponse != null) {
                            isLoading = false;
                            isLoading();
                            mAdapter.refreshData(wxHttpResponse.getNewslist());
                        }
                    }
                });
        // 异步,还没有活跃的LiveData
        Log.e(TAG + "==onLazyInitView", String.valueOf(viewModel.getWxDataCall().hasActiveObservers()));
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


    // 以下生命周期用于测试是否有活跃的LiveData
    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG + "==onStart", String.valueOf(viewModel.getWxDataCall().hasActiveObservers()));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG + "==onStop", String.valueOf(viewModel.getWxDataCall().hasActiveObservers()));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG + "==onResume", String.valueOf(viewModel.getWxDataCall().hasActiveObservers()));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG + "==onPause", String.valueOf(viewModel.getWxDataCall().hasActiveObservers()));
    }
}
