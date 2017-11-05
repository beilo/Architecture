package com.minister.architecture.ui.gank.child;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.minister.architecture.R;
import com.minister.architecture.model.bean.GankItemBean;

import java.util.List;

/** android gank adapter
 * Created by leipe on 2017/9/13.
 */

public class TechAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {

    public TechAdapter(@LayoutRes int layoutResId, @Nullable List<GankItemBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GankItemBean item) {
        helper
                .setText(R.id.tv_title, item.getDesc())
                .setText(R.id.tv_name, item.getWho())
                .setText(R.id.tv_time, item.getCreatedAt());
    }
}
