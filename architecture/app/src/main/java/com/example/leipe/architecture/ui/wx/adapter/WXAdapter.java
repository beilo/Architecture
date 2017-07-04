package com.example.leipe.architecture.ui.wx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leipe.architecture.R;
import com.example.leipe.architecture.model.bean.WXListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter
 * Created by leipe on 2017/6/27.
 */

public class WXAdapter extends RecyclerView.Adapter<WXAdapter.ProductViewHolder> {
    final int item_layout = R.layout.item_product;

    private Context mContext;
    private List<WXListBean> mProductList;

    public WXAdapter(Context mContext, List<WXListBean> mProductList) {
        if (mProductList == null) {
            this.mProductList = new ArrayList<>();
        } else {
            this.mProductList = mProductList;
        }
        this.mContext = mContext;
    }

    public void refreshData(List<WXListBean> mProductList) {
        if (mProductList == null) {
            this.mProductList = new ArrayList<>();
        } else {
            this.mProductList = mProductList;
        }
        notifyDataSetChanged();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(item_layout, parent, false);
        return new ProductViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        WXListBean product = mProductList.get(position);
        holder.tv_name.setText(product.getTitle());
        holder.tv_price.setText(product.getPicUrl());
        holder.tv_description.setText(product.getDescription());
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_price;
        TextView tv_description;

        public ProductViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.name);
            tv_price = itemView.findViewById(R.id.price);
            tv_description = itemView.findViewById(R.id.description);
        }
    }
}
