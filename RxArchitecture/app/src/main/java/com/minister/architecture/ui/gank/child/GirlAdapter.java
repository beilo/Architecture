package com.minister.architecture.ui.gank.child;

import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.minister.architecture.R;
import com.minister.architecture.app.MyApp;
import com.minister.architecture.model.bean.GankItemBean;

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
     * @param  position
     * @return int
     */
    @Override
    protected int getDefItemViewType(int position) {
        return Math.round((float) MyApp.SCREEN_WIDTH / (float) mData.get(position).getHeight() * 10f);
    }

    @Override
    protected void convert(final BaseViewHolder helper, GankItemBean item) {
        final ImageView imageView = helper.getView(R.id.imageView);
        if (mData.get(helper.getAdapterPosition()).getHeight() > 0) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.height = mData.get(helper.getAdapterPosition()).getHeight();
        }
        ViewCompat.setTransitionName(imageView, String.valueOf(helper.getAdapterPosition()) + "_image");
        Glide
                .with(mContext)
                .load(item.getUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>(MyApp.SCREEN_WIDTH / 2, MyApp.SCREEN_WIDTH / 2) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (helper.getAdapterPosition() != RecyclerView.NO_POSITION) {
                            if (mData.get(helper.getAdapterPosition()).getHeight() <= 0) {
                                int width = resource.getWidth();
                                int height = resource.getHeight();
                                int realHeight = (MyApp.SCREEN_WIDTH / 2) * height / width;
                                mData.get(helper.getAdapterPosition()).setHeight(realHeight);
                                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                                layoutParams.height = realHeight;
                            }
                            imageView.setImageBitmap(resource);
                        }
                    }
                });
    }
}
