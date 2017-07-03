package com.example.leipe.rx.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.leipe.rx.R;
import com.example.leipe.rx.model.bean.HotListBean;

import java.util.ArrayList;

/**
 * Created by leipe on 2017/6/30.
 */

public class ZhihuAdapter extends RecyclerView.Adapter<ZhihuAdapter.ZhihuViewHolder> {
    private final int item_layout = R.layout.item_daily;

    private HotListBean listBean;
    private Context mContext;
    private OnItemClick onItemClick;

    public void setItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public ZhihuAdapter(Context mContext, HotListBean listBean) {
        if (listBean == null) {
            HotListBean bean = new HotListBean();
            bean.setRecent(new ArrayList<HotListBean.RecentBean>());
            this.listBean = bean;
        } else {
            this.listBean = listBean;
        }
        this.mContext = mContext;
    }

    /**
     * 刷新数据
     *
     * @param listBean
     */
    public void refreshData(HotListBean listBean) {
        if (listBean == null) {
            HotListBean bean = new HotListBean();
            bean.setRecent(new ArrayList<HotListBean.RecentBean>());
            this.listBean = bean;
        } else {
            this.listBean = listBean;
        }
        notifyDataSetChanged();
    }

    @Override
    public ZhihuAdapter.ZhihuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(item_layout, parent, false);
        return new ZhihuViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ZhihuAdapter.ZhihuViewHolder holder, final int position) {
        final HotListBean.RecentBean item = listBean.getRecent().get(position);
        Glide.with(mContext)
                .load(item.getThumbnail())
                .into(holder.img_item);
        holder.tv_title.setText(item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onClick(view,item,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBean.getRecent().size();
    }

    static class ZhihuViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item;
        TextView tv_title;

        ZhihuViewHolder(View itemView) {
            super(itemView);
            img_item = itemView.findViewById(R.id.iv_daily_item_image);
            tv_title = itemView.findViewById(R.id.tv_daily_item_title);
        }
    }


    public interface OnItemClick {
        void onClick(View view, HotListBean.RecentBean item,int position);
    }
}
