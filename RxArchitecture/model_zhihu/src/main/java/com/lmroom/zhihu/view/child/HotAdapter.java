package com.lmroom.zhihu.view.child;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lmroom.zhihu.R;
import com.lmroom.zhihu.bean.HotListBean;

import java.util.List;

/** 热门 adapter
 * Created by leipe on 2017/9/19.
 */

public class HotAdapter extends BaseQuickAdapter<HotListBean.RecentBean,BaseViewHolder> {

    public HotAdapter(@LayoutRes int layoutResId, @Nullable List<HotListBean.RecentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotListBean.RecentBean item) {
        helper
                .setText(R.id.tv_title, item.getTitle());
        Glide
                .with(mContext)
                .load(item.getThumbnail())
                .into((ImageView) helper.getView(R.id.imageView));
    }
}
