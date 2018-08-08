package com.lmroom.zhihu.view.child;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lmroom.zhihu.R;
import com.lmroom.zhihu.bean.DailyListBean;

import java.util.List;

/** 日报 adapter
 * Created by leipe on 2017/9/18.
 */

public class DailyAdapter extends BaseQuickAdapter<DailyListBean.StoriesBean, BaseViewHolder> {

    public DailyAdapter(@LayoutRes int layoutResId, @Nullable List<DailyListBean.StoriesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyListBean.StoriesBean item) {
        helper
                .setText(R.id.tv_title, item.getTitle());
        Glide
                .with(mContext)
                .load(item.getImages().get(0))
                .into((ImageView) helper.getView(R.id.imageView));
    }
}

