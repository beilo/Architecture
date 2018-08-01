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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.journalism.R;
import com.lmroom.journalism.bean.JokeBean;
import com.lmroom.journalism.util.JournalismRxHelp;
import com.lmroom.journalism.viewmodel.JournalismViewModel;

import io.reactivex.functions.Consumer;

public class JokeListFragment extends BaseSupportFragment {

    public static JokeListFragment newInstance() {
        Bundle args = new Bundle();
        JokeListFragment fragment = new JokeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View mView;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mRefresh;
    private JournalismViewModel mJournalismViewModel;
    private JokeAdapter mAdapter;

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

        mAdapter = new JokeAdapter(R.layout.journalism_list_item_tech);
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

    private void loadData(){
        mRefresh.setRefreshing(true);
        mJournalismViewModel.getJokeList(20,1)
                .compose(JournalismRxHelp.<JokeBean>rxScheduler())
                .subscribe(new Consumer<JokeBean>() {
                    @Override
                    public void accept(JokeBean jokeBean) throws Exception {
                        mAdapter.replaceData(jokeBean.getData());
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

    static class JokeAdapter extends BaseQuickAdapter<JokeBean.DataBean, BaseViewHolder> {

        public JokeAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, JokeBean.DataBean item) {
            helper.setText(R.id.tv_title, item.getContent());
            helper.setText(R.id.tv_time,item.getUpdatetime());
        }
    }
}
