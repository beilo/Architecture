package com.example.leipe.architecture.ui.gank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leipe.architecture.R;
import com.example.leipe.architecture.model.bean.GankItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 被咯苏州 on 2017/7/31.
 */

public class GankTechAdapter extends RecyclerView.Adapter<GankTechAdapter.ViewHolder> {
    private final int item_layout = R.layout.item_tech;
    private List<GankItemBean> list;
    private Context context;

    public GankTechAdapter(List<GankItemBean> list, Context context) {
        if (list == null){
            this.list = new ArrayList<>();
        }else {
            this.list = list;
        }
        this.context = context;
    }

    public void setList(List<GankItemBean> list) {
        if (list == null){
            this.list = new ArrayList<>();
        }else {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(item_layout, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GankItemBean itemBean = list.get(position);
        holder.tvTitle.setText(itemBean.getDesc());
        holder.tvName.setText(itemBean.getWho());
        holder.tvTime.setText(itemBean.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
