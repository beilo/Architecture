package com.minister.architecture.ui.gank.child;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.minister.architecture.R;
import com.minister.architecture.app.MyApp;
import com.minister.architecture.model.bean.GankItemBean;
import com.minister.architecture.widget.ScaleImageView;

import java.util.List;

/**
 * Created by leipe on 2017/9/14.
 */

public class GirlAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {

    public static int LOAD_MORE_ITEM_TYPE = 1;

    public GirlAdapter(@LayoutRes int layoutResId, @Nullable List<GankItemBean> data) {
        super(layoutResId, data);
    }

    /**
     * 在StaggeredGridLayoutManager瀑布流中,当需要依据图片实际相对高度,不断动态设置ImageView的LayoutParams时,
     * 会导致快速滑动状态下产生重新排列,重写getItemViewType并设置StaggeredGridLayoutManager.GAP_HANDLING_NONE解决了这个问题，原因目前未知
     * https://github.com/oxoooo/mr-mantou-android/blob/master/app/src/main/java/ooo/oxo/mr/MainAdapter.java
     *
     * @param position
     * @return int
     */
    @Override
    protected int getDefItemViewType(int position) {
        return Math.round((float) MyApp.SCREEN_WIDTH / (float) mData.get(position).getHeight() * 10f);
    }

    @Override
    protected void convert(final BaseViewHolder helper, GankItemBean item) {
        ScaleImageView imageView = helper.getView(R.id.imageView);
        ViewCompat.setTransitionName(imageView, String.valueOf(helper.getAdapterPosition()) + "_image");
        imageView.setInitSize(MyApp.SCREEN_WIDTH / 2, item.getHeight());
        if (item.getHeight() > 0) {
            Glide
                    .with(mContext)
                    .load(item.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .into(imageView);
        }
    }
}
