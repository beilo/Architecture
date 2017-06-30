package com.example.leipe.rx.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by leipe on 2017/6/30.
 */

public class ZhihuAdapter extends RecyclerView.Adapter<ZhihuAdapter.ZhihuViewHolder> {
    @Override
    public ZhihuAdapter.ZhihuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ZhihuAdapter.ZhihuViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ZhihuViewHolder extends RecyclerView.ViewHolder{

        public ZhihuViewHolder(View itemView) {
            super(itemView);
        }
    }
}
