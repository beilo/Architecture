package com.lmroom.journalism.view.child;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.journalism.R;
import com.lmroom.journalism.bean.JournalismBean;
import com.lmroom.journalism.util.JournalismRxHelp;
import com.lmroom.journalism.viewmodel.JournalismViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class JournalismListFragment extends BaseSupportFragment {

    private View mView;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mRefresh;

    private JournalismAdapter mAdapter;
    private JournalismViewModel mJournalismViewModel;

    public static JournalismListFragment newInstance() {
        Bundle args = new Bundle();
        JournalismListFragment fragment = new JournalismListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.journalism_list_fragment, container, false);
        mJournalismViewModel = ViewModelProviders.of(this).get(JournalismViewModel.class);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = mView.findViewById(R.id.products_list);
        mRefresh = mView.findViewById(R.id.refresh);

        mAdapter = new JournalismAdapter(R.layout.journalism_list_item_tech, new ArrayList<JournalismBean.ListBean>());
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
                Toast.makeText(_mActivity, "click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadData();
    }

    private void loadData() {
        mRefresh.setRefreshing(true);
        mJournalismViewModel.getJournalismList("头条", 40, 0)
                .compose(JournalismRxHelp.<JournalismBean>rxScheduler())
                .subscribe(new Consumer<JournalismBean>() {
                    @Override
                    public void accept(JournalismBean journalismBean) throws Exception {
                        mAdapter.replaceData(journalismBean.getList());
                        mRefresh.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        mRefresh.setRefreshing(false);
                    }
                });
    }

    static class JournalismAdapter extends BaseQuickAdapter<JournalismBean.ListBean, BaseViewHolder> {

        public JournalismAdapter(int layoutResId, @Nullable List<JournalismBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, JournalismBean.ListBean item) {
            helper.setText(R.id.tv_title, item.getTitle());
            Glide.with(mContext)
                    .load(item.getPic())
                    .error(R.drawable.error)
                    .into((ImageView) helper.getView(R.id.imageView));
        }
    }
}
